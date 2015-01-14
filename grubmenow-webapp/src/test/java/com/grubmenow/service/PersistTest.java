package com.grubmenow.service;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;

import org.joda.time.DateTime;

import com.grubmenow.service.api.PlaceOrderService;
import com.grubmenow.service.api.SubmitOrderFeedbackService;
import com.grubmenow.service.datamodel.FoodItemDAO;
import com.grubmenow.service.datamodel.FoodItemOfferDAO;
import com.grubmenow.service.datamodel.IDGenerator;
import com.grubmenow.service.datamodel.OfferMealType;
import com.grubmenow.service.datamodel.OfferState;
import com.grubmenow.service.model.Currency;
import com.grubmenow.service.model.DeliveryMethod;
import com.grubmenow.service.model.OrderItem;
import com.grubmenow.service.model.PaymentMethod;
import com.grubmenow.service.model.PlaceOrderRequest;
import com.grubmenow.service.model.SubmitOrderFeedbackRequest;
import com.grubmenow.service.persist.PersistenceHandler;
import com.grubmenow.service.persist.PersistenceHandlerImpl;


public class PersistTest {

	public static void main(String[] args) {

//		List<String> zipCodes = persistenceHandler.getNeighbouringZipCodes("98007", 10);
//		
//		List<FoodItemDAO> foodItemDAOs = persistenceHandler.getAllAvailableFoodItemForZipCodes(zipCodes, new DateTime());
//		
//		System.out.println(foodItemDAOs);
//		
//		FoodItemOfferDAO foodItemOfferDAO = persistenceHandler.getFoodItemOfferById("kyqkfbtdgp");
//		System.out.println(foodItemOfferDAO);
//		
//		System.out.println(persistenceHandler.getAllFoodItemOffer());
//		
//		populateTestFoodItemOffer();
		
//		testPlaceOrder();
		
//		testSubmitOrderFeedback();
		
//		populateTestFoodItemOffer();
	}
	
	public static void testSubmitOrderFeedback() {
		SubmitOrderFeedbackService service = new SubmitOrderFeedbackService();
		SubmitOrderFeedbackRequest request = new SubmitOrderFeedbackRequest();
		
		request.setOrderId("4a05f5f5-10da-4546-b0c2-06d332064795");
		request.setFeedback("Good food");
		request.setRating(5);
		request.setWebsiteAuthenticationToken("10152843609422975");
		
		service.executeService(request);

	}
	
	public static void testPlaceOrder() {
		
		PlaceOrderRequest placeOrderRequest = new PlaceOrderRequest();
		placeOrderRequest.setOnlinePaymentToken("token");
		placeOrderRequest.setPaymentMethod(PaymentMethod.ONLINE_PAYMENT);
		placeOrderRequest.setDeliveryMethod(DeliveryMethod.CUSTOMER_PICKUP);
		placeOrderRequest.setProviderId("ProviderId3");
		placeOrderRequest.setWebsiteAuthenticationToken("sometoken");
		
		OrderItem orderItem = new OrderItem();
		orderItem.setQuantity(10);
		orderItem.setFoodItemOfferId("mowqweoedi");
		placeOrderRequest.setOrderItems(Arrays.asList(orderItem));
		
		PlaceOrderService placeOrderService = new PlaceOrderService();
		placeOrderService.executeService(placeOrderRequest);
	}
	
	public static void populateTestFoodItemOffer() {
		
		DateTime dateTime = DateTime.now();

		for (int i = 0; i < 20; i++) {
			DateTime forDate = dateTime.plusDays(i);
			
			FoodItemOfferDAO foodItemOfferDAO1 = new FoodItemOfferDAO();
			foodItemOfferDAO1.setFoodItemOfferId(IDGenerator
					.generateFoodItemOfferId());
			foodItemOfferDAO1.setFoodItemId("245633");
			foodItemOfferDAO1.setProviderId("ProviderId1");
			foodItemOfferDAO1.setOfferDescription("Mast Offer");
			foodItemOfferDAO1.setOfferUnitPrice(699);
			foodItemOfferDAO1.setOfferCurrency(Currency.USD);
			foodItemOfferDAO1.setOfferQuantity(new Integer("10"));
			foodItemOfferDAO1.setAvailableQuantity(new Integer("10"));
			foodItemOfferDAO1.setOfferDay(forDate);
			foodItemOfferDAO1.setOfferMealType(OfferMealType.LUNCH);
			foodItemOfferDAO1.setIsFoodDeliveryOptionAvailable(true);
			foodItemOfferDAO1.setIsFoodPickUpOptionAvailable(true);
			foodItemOfferDAO1.setOfferState(OfferState.ACTIVE);

			PersistenceHandler persistenceHandler = new PersistenceHandlerImpl("", "", "");
			persistenceHandler.createFoodItemOffer(
					foodItemOfferDAO1);
			
			FoodItemOfferDAO foodItemOfferDAO2 = new FoodItemOfferDAO();
			foodItemOfferDAO2.setFoodItemOfferId(IDGenerator
					.generateFoodItemOfferId());
			foodItemOfferDAO2.setFoodItemId("225636");
			foodItemOfferDAO2.setProviderId("ProviderId2");
			foodItemOfferDAO2.setOfferDescription("Mast Offer2");
			foodItemOfferDAO2.setOfferUnitPrice(599);
			foodItemOfferDAO2.setOfferCurrency(Currency.USD);
			foodItemOfferDAO2.setOfferQuantity(new Integer("10"));
			foodItemOfferDAO2.setAvailableQuantity(new Integer("10"));
			foodItemOfferDAO2.setOfferDay(forDate);
			foodItemOfferDAO2.setOfferMealType(OfferMealType.LUNCH);
			foodItemOfferDAO2.setIsFoodDeliveryOptionAvailable(true);
			foodItemOfferDAO2.setIsFoodPickUpOptionAvailable(true);
			foodItemOfferDAO2.setOfferState(OfferState.ACTIVE);

			persistenceHandler.createFoodItemOffer(
					foodItemOfferDAO2);

			
			FoodItemOfferDAO foodItemOfferDAO3 = new FoodItemOfferDAO();
			foodItemOfferDAO3.setFoodItemOfferId(IDGenerator
					.generateFoodItemOfferId());
			foodItemOfferDAO3.setFoodItemId("475630");
			foodItemOfferDAO3.setProviderId("ProviderId3");
			foodItemOfferDAO3.setOfferDescription("Mast Offer3");
			foodItemOfferDAO3.setOfferUnitPrice(699);
			foodItemOfferDAO3.setOfferCurrency(Currency.USD);
			foodItemOfferDAO3.setOfferQuantity(new Integer("10"));
			foodItemOfferDAO3.setAvailableQuantity(new Integer("10"));
			foodItemOfferDAO3.setOfferDay(forDate);
			foodItemOfferDAO3.setOfferMealType(OfferMealType.DINNER);
			foodItemOfferDAO3.setIsFoodDeliveryOptionAvailable(true);
			foodItemOfferDAO3.setIsFoodPickUpOptionAvailable(true);
			foodItemOfferDAO3.setOfferState(OfferState.ACTIVE);

			persistenceHandler.createFoodItemOffer(
					foodItemOfferDAO3);

			
			
			FoodItemOfferDAO foodItemOfferDAO4 = new FoodItemOfferDAO();
			foodItemOfferDAO4.setFoodItemOfferId(IDGenerator
					.generateFoodItemOfferId());
			foodItemOfferDAO4.setFoodItemId("885637");
			foodItemOfferDAO4.setProviderId("ProviderId4");
			foodItemOfferDAO4.setOfferDescription("Mast Offer 4");
			foodItemOfferDAO4.setOfferUnitPrice(299);
			foodItemOfferDAO4.setOfferCurrency(Currency.USD);
			foodItemOfferDAO4.setOfferQuantity(new Integer("10"));
			foodItemOfferDAO4.setAvailableQuantity(new Integer("10"));
			foodItemOfferDAO4.setOfferDay(forDate);
			foodItemOfferDAO4.setOfferMealType(OfferMealType.DINNER);
			foodItemOfferDAO4.setIsFoodDeliveryOptionAvailable(true);
			foodItemOfferDAO4.setIsFoodPickUpOptionAvailable(true);
			foodItemOfferDAO4.setOfferState(OfferState.ACTIVE);

			persistenceHandler.createFoodItemOffer(
					foodItemOfferDAO4);

		}
	}
	
//	public static void testAddFoodItemOffer() {
//		
//		FoodItemOfferDAO foodItemOfferDAO = new FoodItemOfferDAO();
//		foodItemOfferDAO.setFoodItemOfferId(IDGenerator.generateFoodItemOfferId());
//		foodItemOfferDAO.setFoodItemId("lklpnwax");
//		foodItemOfferDAO.setProviderId("sjycxqnc");
//		foodItemOfferDAO.setOfferDescription("Tasty Samosa");
//		foodItemOfferDAO.setOfferDescriptionTags("Spicy");
//		foodItemOfferDAO.setOfferDescriptionTags("Spicy");
//		foodItemOfferDAO.setOfferUnitPrice(new BigDecimal("6.5"));
//		foodItemOfferDAO.setOfferCurrency("USD");
//		foodItemOfferDAO.setOfferQuantity(new Integer("100"));
//		foodItemOfferDAO.setAvailableQuantity(new Integer("100"));
//		foodItemOfferDAO.setOfferDay(DateTime.now());
//		foodItemOfferDAO.setOfferMealType(OfferMealType.LUNCH);
//		foodItemOfferDAO.setIsFoodDeliveryOptionAvailable(true);
//		foodItemOfferDAO.setIsFoodPickUpOptionAvailable(true);
//		foodItemOfferDAO.setOfferState(OfferState.ACTIVE);
//		
//		persistenceHandler.createFoodItemOffer(foodItemOfferDAO);
//	}
//	
//	private static void testFoodItem(PersistenceHandlerInterface persistenceHandlerInterface) {
//		FoodItemDAO foodItem = new FoodItemDAO();
//		foodItem.setFoodItemId(RandomStringUtils.randomAlphanumeric(8));
//		foodItem.setFoodItemDescription("Rice Idly");
//		foodItem.setFoodItemImageUrl("http://abc.com/img.url");
//		foodItem.setFoodItemName("Idly");
//		
//		persistenceHandlerInterface.createFoodItem(foodItem);
//	}
//	
//	private static void testProvider(PersistenceHandlerInterface persistenceHandlerInterface) {
//		ProviderDAO provider = new ProviderDAO();
//		provider.setProviderId(RandomStringUtils.randomAlphanumeric(8));
//		provider.setProviderName("First Provider");
//		provider.setProviderAddressStreetNumber("4723");
//		provider.setProviderAddressStreet("148TH AVE NE");
//		provider.setProviderAddressApartmentNumber("XX 101");
//		provider.setProviderAddressZipCode("98007");
//		provider.setProviderAddressState("WA");
//		provider.setProviderAddressCity("Bellevue");
//		provider.setProviderImageURL("http://abc.com/img.url");
//		provider.setAcceptedPaymentMethods(new HashSet<String>(Arrays.asList("credit_card", "on_delivery")));
//		
//		persistenceHandlerInterface.createProvider(provider);
//	}
//	
//	private static void testFoodItemOffer(PersistenceHandlerInterface persistenceHandlerInterface) {
//		FoodItemOfferDAO foodItemOffer = new FoodItemOfferDAO();
//		foodItemOffer.setFoodItemOfferId(RandomStringUtils.randomAlphanumeric(8));
//		foodItemOffer.setProviderId(RandomStringUtils.randomAlphanumeric(8));
//		foodItemOffer.setFoodItemId(RandomStringUtils.randomAlphanumeric(8));
//		foodItemOffer.setMealType("dinner");
//		foodItemOffer.setOfferDay(Calendar.getInstance());
//		foodItemOffer.setProviderFoodItemOfferDescription("Special Idly");
//		foodItemOffer.setProviderFoodItemOfferCurrency("USD");
//		foodItemOffer.setProviderFoodItemOfferUnitPrice(new BigDecimal(10));
//		foodItemOffer.setProviderFoodItemOfferUnitQuantity(6);
//
//		persistenceHandlerInterface.createFoodItemOffer(foodItemOffer);
//	}


}
