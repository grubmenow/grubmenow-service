package com.grubmenow.service.api;


import java.util.ArrayList;
import java.util.List;

import lombok.extern.apachecommons.CommonsLog;

import org.apache.commons.lang3.StringUtils;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.joda.time.DateTime;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.grubmenow.service.auth.FacebookCustomerInfo;
import com.grubmenow.service.auth.ServiceHandler;
import com.grubmenow.service.datamodel.CustomerDAO;
import com.grubmenow.service.datamodel.CustomerOrderDAO;
import com.grubmenow.service.datamodel.CustomerOrderItemDAO;
import com.grubmenow.service.datamodel.CustomerState;
import com.grubmenow.service.datamodel.FoodItemDAO;
import com.grubmenow.service.datamodel.FoodItemOfferDAO;
import com.grubmenow.service.datamodel.IDGenerator;
import com.grubmenow.service.datamodel.ObjectPopulator;
import com.grubmenow.service.datamodel.OfferMealType;
import com.grubmenow.service.datamodel.OfferState;
import com.grubmenow.service.datamodel.OrderPaymentState;
import com.grubmenow.service.datamodel.OrderState;
import com.grubmenow.service.datamodel.ProviderDAO;
import com.grubmenow.service.datamodel.ProviderState;
import com.grubmenow.service.model.Amount;
import com.grubmenow.service.model.AvailableDay;
import com.grubmenow.service.model.CustomerOrderItem;
import com.grubmenow.service.model.OrderItem;
import com.grubmenow.service.model.PaymentMethod;
import com.grubmenow.service.model.PlaceOrderRequest;
import com.grubmenow.service.model.PlaceOrderResponse;
import com.grubmenow.service.model.exception.ValidationException;
import com.grubmenow.service.notif.email.OrderSuccessEmailRequest;
import com.grubmenow.service.notif.email.EmailSendException;
import com.grubmenow.service.notif.email.EmailableOrderItemDetail;
import com.grubmenow.service.pay.PaymentTransaction;
import com.grubmenow.service.persist.PersistenceFactory;

@RestController
@CommonsLog
public class PlaceOrderService  extends AbstractRemoteService {

	private static float TAX_PERCENTAGE = 0.095f;
	
	private static int ORDER_CUT_OFF_HOUR = 16;
	
	@RequestMapping(value = "/api/placeOrder", method = RequestMethod.POST, produces = "application/json; charset=utf-8")
	@ResponseBody
	public PlaceOrderResponse executeService(@RequestBody PlaceOrderRequest request) throws ValidationException {
		
		validateInput(request);

		// generate ids and other constant for this order
		String orderId = IDGenerator.generateOrderId();
		CustomerDAO customerDAO = saveAndFetchCustomerId(request);
		
		// order date
		DateTime orderDateTime = DateTime.now();

		// initialize order by adding order and order item id in it's initial state into database
		// Also verifies if the order amount in request is as per back-end logic
		Order order = initializeOrder(request, orderId, customerDAO.getCustomerId(), orderDateTime);

		// process order
		try {
			processOrder(request, customerDAO, order);
		} catch (EmailSendException ex) {
			log.error("Not able to send email", ex);
		}
		
		return generateResponse(orderId);
	}
	
	private CustomerDAO saveAndFetchCustomerId(PlaceOrderRequest request) {
		FacebookCustomerInfo fbCustomerInfo = null;
		try {
			fbCustomerInfo = ServiceHandler.getInstance().getFacebookAuthentication().validateTokenAndFetchCustomerInfo(request.getWebsiteAuthenticationToken());
		} catch (Exception e) {
			throw new ValidationException("Invalid fb authentication token");
		}
			
		// create a customer DAO
		CustomerDAO customerDAO = new CustomerDAO();
		customerDAO.setCustomerId(fbCustomerInfo.getFacebookUserId());
		String[] splitCustomerName = StringUtils.split(request.getCustomerName(), " ");
		String firstName = splitCustomerName[0];
		String lastName = null;
		if (splitCustomerName.length > 1)
		{
		    lastName = splitCustomerName[1];
		}

		customerDAO.setCustomerFirstName(firstName);
		customerDAO.setCustomerLastName(lastName);
		customerDAO.setCustomerEmailId(request.getCustomerEmailId());
		customerDAO.setCustomerPhoneNumber(request.getCustomerPhoneNumber());
		customerDAO.setCustomerState(CustomerState.ACTIVE);

		CustomerDAO prevCustomerDAO = null;
		try
		{
		    prevCustomerDAO = PersistenceFactory.getInstance().getCustomerById(fbCustomerInfo.getFacebookUserId());
		}
		catch (Exception e)
		{
		    log.info("Customer does not exist, valid for a new customer placing order first time. fb user id: " + fbCustomerInfo.getFacebookUserId() 
		        + ". " + e.getMessage());
		}

		try
		{
		    if (prevCustomerDAO == null)
		    {
		        PersistenceFactory.getInstance().createCustomer(customerDAO);
		    }
		    else
		    {
		        Validator.isTrue(prevCustomerDAO.getCustomerState() == CustomerState.ACTIVE, "Invalid Customer State");
		        CustomerDAO newCustomerDAO = mergePrevAndNewCustomerDAO(prevCustomerDAO, customerDAO);
		        PersistenceFactory.getInstance().updateCustomer(newCustomerDAO);
		    }
		}
		catch (Exception e) {
            throw new ValidationException("Customer could not be created or updated", e.getMessage());
        }

		return customerDAO;
	}
	 
	private CustomerDAO mergePrevAndNewCustomerDAO(CustomerDAO prevCustomerDAO, CustomerDAO newCustomerDAO)
    {
        if (prevCustomerDAO == null)
        {
            return newCustomerDAO;
        }

        // Use the prev phone number if the prev was present but the new one is not present. 
        // this is done so that we don't override a previous phone number and have a way to reach to the customer
        // if needed
        if (prevCustomerDAO.getCustomerPhoneNumber() != null && newCustomerDAO.getCustomerPhoneNumber() == null)
        {
            newCustomerDAO.setCustomerPhoneNumber(prevCustomerDAO.getCustomerPhoneNumber());
        }
        return newCustomerDAO;
    }

	private void processOrder(PlaceOrderRequest request, CustomerDAO customerDAO, Order order) throws EmailSendException {
		String orderId = order.customerOrderDAO.getOrderId();
		try {
			reserveOrder(request);
		} catch (Exception e) {
			log.error("Unable to reserve order", e);
			processOfferReserveFailure(request, orderId, e.getMessage());
			throw e;
		}
		
		// Charge customer credit card
		// if successful credit card charge, then go ahead with order success, else, unreserve the availability
		try {
			processPayment(request, customerDAO, orderId);
		} catch (Exception e) {
			log.error("Unable to process payment", e);
			releaseReservedOrder(request);
			processCreditCardChargeFailure(request, orderId, e.getMessage());
			throw e;
		}

		// Update Order Status
		CustomerOrderDAO customerOrderDAO = processOrderSuccess(request, orderId, "Order Processed");
		
		// send success emails
		if (order.foodItemOfferDAOs == null || order.foodItemOfferDAOs.isEmpty())
		{
			throw new IllegalStateException("FoodItemOfferDAOs should not be null at this point");
		}
		sendSuccessEmail(customerDAO, customerOrderDAO, order.customerOrderItemDAOs, order.foodItemOfferDAOs.get(0).getOfferDay());
	}
	
	private void processPayment(PlaceOrderRequest request, CustomerDAO customerDAO, String orderId) {
		if(request.getPaymentMethod() == PaymentMethod.CARD_ON_DELIVERY || request.getPaymentMethod() == PaymentMethod.CASH_ON_DELIVERY) {
			return;
		}

		Amount orderAmount = request.getOrderAmount();
		int amountInCents = orderAmount.getValue();
		
		try {
			PaymentTransaction paymentTransaction = ServiceHandler.getInstance().getStripeProcessor().charge(request.getOnlinePaymentToken(), amountInCents, orderAmount.getCurrency(), orderId, orderId);
			Validator.notNull(paymentTransaction, "Unable to authorize payment instrument");

			CustomerOrderDAO customerOrderDAO = PersistenceFactory.getInstance().getCustomerOrderById(orderId);
			customerOrderDAO.setOrderChargeTransactionId(paymentTransaction.getTransactionId());
			customerOrderDAO.setOrderPaymentState(OrderPaymentState.AUTHORIZATION_SUCCESS);
			
			PersistenceFactory.getInstance().updateCustomerOrder(customerOrderDAO);
		} catch (Exception e) {
			log.error("Unable to authorize payment instrument", e);
			throw new ValidationException("Unable to authorize payment instrument", e.getMessage());
		}
	}
	
	private PlaceOrderResponse generateResponse(String orderId) {
		PlaceOrderResponse response = new PlaceOrderResponse();

		CustomerOrderDAO customerOrderDAO = PersistenceFactory.getInstance().getCustomerOrderById(orderId);
		response.setCustomerOrder(ObjectPopulator.toCustomerOrder(customerOrderDAO));
		
		List<CustomerOrderItemDAO> customerOrderItemDAOs = PersistenceFactory.getInstance().getCustomerOrderItemByOrderId(orderId);
		
		List<CustomerOrderItem> customerOrderItems = new ArrayList<>();
		for(CustomerOrderItemDAO customerOrderItemDAO: customerOrderItemDAOs) {
			customerOrderItems.add(ObjectPopulator.toCustomerOrderItem(customerOrderItemDAO));
		}
		
		response.setCustomerOrderItems(customerOrderItems);
		
		return response;
	}
	
	private void reserveOrder(PlaceOrderRequest request) {
		
		Session session = PersistenceFactory.getInstance().getSession();
		Transaction transaction = session.beginTransaction();
		try {
			for (OrderItem orderItem : request.getOrderItems()) {
				String hql = String.format("UPDATE FOOD_ITEM_OFFER set AVAILABLE_QUANTITY = AVAILABLE_QUANTITY - :quantity where AVAILABLE_QUANTITY >= :quantity and FOOD_ITEM_OFFER_ID = :offerId");
				
				int updatedOfferCount = session.createSQLQuery(hql)
				        .setInteger("quantity", orderItem.getQuantity())
				        .setString("offerId", orderItem.getFoodItemOfferId())
				        .executeUpdate();
				
				if(updatedOfferCount == 0) { // that means, enough quantities are not available
					throw new IllegalStateException("Unable to reserve offer: " + orderItem.getFoodItemOfferId());	
				}
			}
			
			transaction.commit();
		} catch (Exception e) {
			transaction.rollback();
			throw e;
		} finally {
			session.close();
		}
	}
	
	private void releaseReservedOrder(PlaceOrderRequest request) {
		
		Session session = PersistenceFactory.getInstance().getSession();
		Transaction transaction = session.beginTransaction();
		try {
			for (OrderItem orderItem : request.getOrderItems()) {
				String hql = String.format("UPDATE FOOD_ITEM_OFFER set AVAILABLE_QUANTITY = AVAILABLE_QUANTITY + :quantity and FOOD_ITEM_OFFER_ID = :offerId");
				
				int updatedOfferCount = session.createQuery(hql)
				        .setInteger("quantity", orderItem.getQuantity())
				        .setString("offerId", orderItem.getFoodItemOfferId())
				        .executeUpdate();
				
				if(updatedOfferCount == 0) { // that means, enough quantities are not available
					throw new IllegalStateException("Unable to update Offer Count");	
				}
			}
			
			transaction.commit();
		} catch (Exception e) {
			transaction.rollback();
		} finally {
			session.close();
		}
	}
	
	private void processOfferReserveFailure(PlaceOrderRequest request, String orderId, String message) {
		CustomerOrderDAO customerOrderDAO = PersistenceFactory.getInstance().getCustomerOrderById(orderId);
		customerOrderDAO.setOrderStateMessage(message);
		customerOrderDAO.setOrderState(OrderState.FAILED);
		PersistenceFactory.getInstance().updateCustomerOrder(customerOrderDAO);
	}
	
	private void processCreditCardChargeFailure(PlaceOrderRequest request, String orderId, String message) {
		CustomerOrderDAO customerOrderDAO = PersistenceFactory.getInstance().getCustomerOrderById(orderId);
		customerOrderDAO.setOrderStateMessage(message);
		customerOrderDAO.setOrderPaymentState(OrderPaymentState.AUTHORIZATION_FAILURE);
		customerOrderDAO.setOrderState(OrderState.FAILED);
		PersistenceFactory.getInstance().updateCustomerOrder(customerOrderDAO);
	}

	
	
	private CustomerOrderDAO processOrderSuccess(PlaceOrderRequest request, String orderId, String message) {
		CustomerOrderDAO customerOrderDAO = PersistenceFactory.getInstance().getCustomerOrderById(orderId);
		customerOrderDAO.setOrderState(OrderState.SUCCESS);
		customerOrderDAO.setOrderStateMessage(message);
		PersistenceFactory.getInstance().updateCustomerOrder(customerOrderDAO);
		return customerOrderDAO;
	}

	private void sendSuccessEmail(CustomerDAO customerDAO, 
			CustomerOrderDAO customerOrderDAO, 
			List<CustomerOrderItemDAO> customerOrderItemDAOs, 
			DateTime orderFulfillmentDate) throws EmailSendException {
		String providerId = customerOrderDAO.getProviderId();
		ProviderDAO providerDAO = PersistenceFactory.getInstance().getProviderById(providerId);
		List<EmailableOrderItemDetail> orderItemDetails = new ArrayList<>();
		for(CustomerOrderItemDAO customerOrderItemDAO: customerOrderItemDAOs) {
			EmailableOrderItemDetail emailOrderItemDetail = new EmailableOrderItemDetail();
			
			// fill food item name
			String foodItemId = customerOrderItemDAO.getFoodItemId();
			FoodItemDAO foodItemDAO = PersistenceFactory.getInstance().getFoodItemById(foodItemId);
			emailOrderItemDetail.setFoodItemName(foodItemDAO.getFoodItemName());
			
			// fill food item description
			String foodItemOfferId = customerOrderItemDAO.getFoodItemOfferId();
			FoodItemOfferDAO foodItemOfferDAO = PersistenceFactory.getInstance().getFoodItemOfferById(foodItemOfferId);
			emailOrderItemDetail.setFoodItemDescription(foodItemOfferDAO.getOfferDescription());
			
			// fill quantity
			emailOrderItemDetail.setFoodItemQuantity(customerOrderItemDAO.getQuantity());
			
			// fill food item amount
			Amount foodItemAmount = new Amount();
			foodItemAmount.setCurrency(customerOrderItemDAO.getOrderCurrency());
			foodItemAmount.setValue(customerOrderItemDAO.getOrderItemAmount());
			emailOrderItemDetail.setFoodItemTotalPrice(foodItemAmount);
			
			orderItemDetails.add(emailOrderItemDetail);
		}
				
		
		// send email
		OrderSuccessEmailRequest emailRequest = OrderSuccessEmailRequest
					.builder()
					.consumer(customerDAO)
					.provider(providerDAO)
					.customerOrder(customerOrderDAO)
					.orderItems(orderItemDetails)
					.orderPickupEndTime("9 PM")
					.orderPickupStartTime("7 PM")
					.orderFulfillmentDate(orderFulfillmentDate)
					.build();
					
		// send email to consumer
		try
		{
			ServiceHandler.getInstance().getEmailSender().sendConsumerOrderSuccessEmail(emailRequest);
			ServiceHandler.getInstance().getEmailSender().sendProviderOrderSuccessEmail(emailRequest);
		}
		catch (Exception ex)
		{
			log.error("Cannot send email, gulping the exception", ex);
		}
	}

	private Order initializeOrder(PlaceOrderRequest request, String orderId, String customerId, DateTime orderDateTime) {
		// TODO: kapila(11th Oct 2014) All these calls are not happening in db transaction. is that alright?
		
		List<CustomerOrderItemDAO> orderDAOs = new ArrayList<>(); 

		Amount orderAmount = request.getOrderAmount();
		
		int totalOrderAmountInCents = 0;
		List<FoodItemOfferDAO> foodItemOfferDAOs = new ArrayList<>();
		for (OrderItem orderItem : request.getOrderItems()) {
			String orderItemId = IDGenerator.generateOrderId();
			
			FoodItemOfferDAO foodItemOfferDAO = PersistenceFactory.getInstance().getFoodItemOfferById(orderItem.getFoodItemOfferId());
			
			validateFoodOffer(foodItemOfferDAO);
			
			CustomerOrderItemDAO orderDAO = new CustomerOrderItemDAO();
			orderDAO.setOrderCreationDate(orderDateTime);
			orderDAO.setOrderId(orderId);
			orderDAO.setOrderItemId(orderItemId);
			orderDAO.setCustomerId(customerId);
			orderDAO.setProviderId(request.getProviderId());
			orderDAO.setFoodItemId(foodItemOfferDAO.getFoodItemId());
			orderDAO.setFoodItemOfferId(orderItem.getFoodItemOfferId());
			orderDAO.setQuantity(orderItem.getQuantity());
			
			int orderItemAmountInCents = foodItemOfferDAO.getOfferUnitPrice() * orderItem.getQuantity();
			orderDAO.setOrderItemAmount(orderItemAmountInCents);
			orderDAO.setOrderCurrency(request.getOrderAmount().getCurrency());
			
			Validator.isTrue(foodItemOfferDAO.getAvailableQuantity() >= orderItem.getQuantity(), "Not enough quantity available for this Offer");
			
			totalOrderAmountInCents = totalOrderAmountInCents + orderItemAmountInCents;
			orderDAOs.add(orderDAO);
			foodItemOfferDAOs.add(foodItemOfferDAO);
		}
		
		// calculate tax
		int taxAmountInCents = (int)(totalOrderAmountInCents * TAX_PERCENTAGE);
		
		// total order amount
		totalOrderAmountInCents = totalOrderAmountInCents + taxAmountInCents;
		
		Validator.isTrue(totalOrderAmountInCents == orderAmount.getValue(), 
		        String.format("Calculated amount is %s, where as request amount is %s - in cents", totalOrderAmountInCents, orderAmount));
		
		for(CustomerOrderItemDAO customerOrderItemDAO: orderDAOs) {
			PersistenceFactory.getInstance().createCustomerOrderItem(customerOrderItemDAO);
		}
		
		
		CustomerOrderDAO customerOrderDAO = new CustomerOrderDAO();
		customerOrderDAO.setCustomerId(customerId);
		customerOrderDAO.setOrderCreationDate(DateTime.now());
		customerOrderDAO.setOrderId(orderId);
		customerOrderDAO.setProviderId(request.getProviderId());
		customerOrderDAO.setDeliveryMethod(request.getDeliveryMethod());
		customerOrderDAO.setPaymentMethod(request.getPaymentMethod());
		customerOrderDAO.setOrderAmount(totalOrderAmountInCents);
		customerOrderDAO.setTaxAmount(taxAmountInCents);
		customerOrderDAO.setOrderCurrency(request.getOrderAmount().getCurrency());
		customerOrderDAO.setOrderPaymentState(OrderPaymentState.PENDING);
		customerOrderDAO.setOrderState(OrderState.PENDING);
		customerOrderDAO.setOrderStateMessage("Creating Order");
		
		PersistenceFactory.getInstance().createCustomerOrder(customerOrderDAO);
		
		Order order = new Order();
		order.customerOrderDAO = customerOrderDAO;
		order.customerOrderItemDAOs = orderDAOs;
		order.foodItemOfferDAOs = foodItemOfferDAOs;
		return order;
	}
	
	private void validateProvider(ProviderDAO providerDAO) {
		Validator.isTrue(providerDAO.getProviderState() == ProviderState.ACTIVE, "Provider state is not Active");
	}
	
	private void validateFoodOffer (FoodItemOfferDAO foodItemOfferDAO) {
		// check if offer is a valid offer
		Validator.isTrue(foodItemOfferDAO.getOfferState() == OfferState.ACTIVE, "Offer state is not Active");

		// if meal type is dinner and it is for today
		if(foodItemOfferDAO.getOfferMealType() == OfferMealType.DINNER && foodItemOfferDAO.getOfferDay().getDayOfYear() == DateTime.now().getDayOfYear() && foodItemOfferDAO.getOfferDay().getDayOfYear() == DateTime.now().getDayOfYear()) {
			Validator.validateNowBeforeCutOff(ORDER_CUT_OFF_HOUR, "Order cannot be placed as it is after cut-off time.");
		}
	}

	private void validateInput(PlaceOrderRequest request) {
		Validator.notBlank(request.getProviderId(), "Invalid Provider Id");
		Validator.notPositiveAmount(request.getOrderAmount(), "Invalid Order Amount");
		Validator.notNull(request.getPaymentMethod(), "Invalid Payment Method");
		Validator.notNull(request.getDeliveryMethod(), "Invalid Delivery Method");
		Validator.notNull(request.getWebsiteAuthenticationToken(), "Invalid Website Authentication Token");
		Validator.notNull(request.getOrderAmount(), "Invalid Order Amount");
		Validator.notNull(request.getOrderAmount().getValue(), "Invalid Order Amount.Value");
		Validator.notNull(request.getOrderAmount().getCurrency(), "Invalid Order Amount.Currency");
		Validator.notBlank(request.getCustomerName(), "Customer name cannot be empty");
		Validator.notBlank(request.getCustomerEmailId(), "Customer email id cannot be empty");
		
		if(request.getPaymentMethod() == PaymentMethod.ONLINE_PAYMENT) {
			Validator.notNull(request.getOnlinePaymentToken(), "Invalid Website Authentication Token");	
		}
		
		ProviderDAO providerDAO = PersistenceFactory.getInstance().getProviderById(request.getProviderId());
		validateProvider(providerDAO);
	}
	
	/**
	 * An object that carries the order and related state and objects from one method to another. 
	 * This is also used to avoid multiple redundant calls to db. 
	 * 
	 */
	private static class Order 
	{
		List<CustomerOrderItemDAO> customerOrderItemDAOs;
		CustomerOrderDAO customerOrderDAO;
		List<FoodItemOfferDAO> foodItemOfferDAOs;
	}
}
