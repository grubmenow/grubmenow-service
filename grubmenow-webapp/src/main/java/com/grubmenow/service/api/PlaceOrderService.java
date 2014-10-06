package com.grubmenow.service.api;

import java.util.ArrayList;
import java.util.List;

import lombok.extern.apachecommons.CommonsLog;

import org.hibernate.Session;
import org.hibernate.Transaction;
import org.joda.time.DateTime;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.grubmenow.service.datamodel.CustomerOrderDAO;
import com.grubmenow.service.datamodel.CustomerOrderItemDAO;
import com.grubmenow.service.datamodel.FoodItemOfferDAO;
import com.grubmenow.service.datamodel.IDGenerator;
import com.grubmenow.service.datamodel.ObjectPopulator;
import com.grubmenow.service.datamodel.OrderPaymentState;
import com.grubmenow.service.datamodel.OrderState;
import com.grubmenow.service.datamodel.ProviderDAO;
import com.grubmenow.service.datamodel.ProviderState;
import com.grubmenow.service.model.CustomerOrderItem;
import com.grubmenow.service.model.OrderItem;
import com.grubmenow.service.model.PaymentMethod;
import com.grubmenow.service.model.PlaceOrderRequest;
import com.grubmenow.service.model.PlaceOrderResponse;
import com.grubmenow.service.model.exception.ValidationException;
import com.grubmenow.service.persist.PersistenceFactory;

@RestController
@CommonsLog
public class PlaceOrderService {

	@RequestMapping(value = "/placeOrder", method = RequestMethod.POST, produces = "application/json; charset=utf-8")
	@ResponseBody
	public PlaceOrderResponse doPost(@RequestBody PlaceOrderRequest request) throws ValidationException {
		
		validateInput(request);

		// generate ids and other constant for this order
		String orderId = IDGenerator.generateOrderId();
		String customerId = getCustomerId(request);
		
		// order date
		DateTime orderDateTime = DateTime.now();

		// initialize order by adding order and order item id in it's initial state into database
		initializeOrder(request, orderId, customerId, orderDateTime);

		// process order
		processOrder(request, orderId);
		
		return generateResponse(orderId);
	}
	
	private String getCustomerId(PlaceOrderRequest request) {
		return "customerId";
	}
	 
	private void processOrder(PlaceOrderRequest request, String orderId) {
		try {
			reserveOrder(request);
		} catch (Exception e) {
			log.error("Unable to reserve order", e);
			processOfferReserveFailure(request, orderId, e.getMessage());
			return;
		}
		
		// Charge customer credit card
		// if successful credit card charge, then go ahead with order success, else, unreserve the availability
		try {
			processPayment(request, orderId);
		} catch (Exception e) {
			log.error("Unable to process payment", e);
			releaseReservedOrder(request);
			processCreditCardChargeFailure(request, orderId, e.getMessage());
			return;
		}

		// Update Order Status and send Emails
		processOrderSuccess(request, orderId, "Order Processed");
		
	}
	
	private void processPayment(PlaceOrderRequest request, String orderId) {
		if(request.getPaymentMethod() == PaymentMethod.CARD_ON_DELIVERY || request.getPaymentMethod() == PaymentMethod.CASH_ON_DELIVERY) {
			return;
		}
		
		CustomerOrderDAO customerOrderDAO = PersistenceFactory.getInstance().getCustomerOrderById(orderId);
		customerOrderDAO.setOrderPaymentState(OrderPaymentState.AUTHORIZATION_SUCCESS);
		PersistenceFactory.getInstance().updateCustomerOrder(customerOrderDAO);
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
		customerOrderDAO.setOrderState(OrderState.FAILED);
		PersistenceFactory.getInstance().updateCustomerOrder(customerOrderDAO);
	}

	
	
	private void processOrderSuccess(PlaceOrderRequest request, String orderId, String message) {
		CustomerOrderDAO customerOrderDAO = PersistenceFactory.getInstance().getCustomerOrderById(orderId);
		customerOrderDAO.setOrderState(OrderState.SUCCESS);
		customerOrderDAO.setOrderStateMessage(message);
		PersistenceFactory.getInstance().updateCustomerOrder(customerOrderDAO);
	}


	private void initializeOrder(PlaceOrderRequest request, String orderId, String customerId, DateTime orderDateTime) {
		createOrderItemId(request, orderId, orderDateTime, customerId);
		createOrder(request, orderId, orderDateTime, customerId);
	}
	

	private void createOrder(PlaceOrderRequest request, String orderId, DateTime orderDateTime, String customerId) {
		CustomerOrderDAO customerOrderDAO = new CustomerOrderDAO();
		customerOrderDAO.setCustomerId(customerId);
		customerOrderDAO.setOrderCreationDate(DateTime.now());
		customerOrderDAO.setOrderId(orderId);
		customerOrderDAO.setProviderId(request.getProviderId());
		customerOrderDAO.setDeliveryMethod(request.getDeliveryMethod());
		customerOrderDAO.setPaymentMethod(request.getPaymentMethod());
		customerOrderDAO.setOrderPaymentState(OrderPaymentState.PENDING);
		customerOrderDAO.setOrderState(OrderState.PENDING);
		customerOrderDAO.setOrderStateMessage("Creating Order");
		
		PersistenceFactory.getInstance().createCustomerOrder(customerOrderDAO);
	}
	
	
	private void createOrderItemId(PlaceOrderRequest request, String orderId, DateTime orderDateTime, String customerId) {
		List<CustomerOrderItemDAO> orderDAOs = new ArrayList<>(); 
		
		for (OrderItem orderItem : request.getOrderItems()) {
			String orderItemId = IDGenerator.generateOrderId();
			
			FoodItemOfferDAO foodItemOfferDAO = PersistenceFactory.getInstance().getFoodItemOfferById(orderItem.getFoodItemOfferId());
			
			CustomerOrderItemDAO orderDAO = new CustomerOrderItemDAO();
			orderDAO.setCustomerId(customerId);
			orderDAO.setOrderCreationDate(orderDateTime);
			orderDAO.setOrderId(orderId);
			orderDAO.setOrderItemId(orderItemId);
			orderDAO.setCustomerId(customerId);
			orderDAO.setProviderId(request.getProviderId());
			orderDAO.setFoodItemId(foodItemOfferDAO.getFoodItemId());
			orderDAO.setFoodItemOfferId(orderItem.getFoodItemOfferId());
			orderDAO.setQuantity(orderItem.getQuantity());
			
			if(foodItemOfferDAO.getAvailableQuantity() < orderItem.getQuantity()) {
				throw new ValidationException("Not enough quantity available for this Offer");
			}
			
			orderDAOs.add(orderDAO);
		}
		
		for(CustomerOrderItemDAO customerOrderItemDAO: orderDAOs) {
			PersistenceFactory.getInstance().createCustomerOrderItem(customerOrderItemDAO);
		}
	}


	private void validateProvider(String providerId, ProviderDAO providerDAO) {
		
		if (providerDAO == null) {
			throw new ValidationException("Provider not found for the id" + providerId);
		}
		if (providerDAO.getProviderState() == ProviderState.INACTIVE) {
			throw new ValidationException("Provider state is not Active");
		}
	}

	private void validateInput(PlaceOrderRequest request) {
	}
}
