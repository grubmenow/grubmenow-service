package com.grubmenow.service;

import java.util.List;

import com.grubmenow.service.datamodel.FoodItemDAO;
import com.grubmenow.service.persist.PersistenceFactory;


public class PersistTest {

	public static void main(String[] args) {

		List<String> zipCodes = PersistenceFactory.getInstance().getNeighbouringZipCodes("98007", 10);
		
		List<FoodItemDAO> foodItemDAOs = PersistenceFactory.getInstance().getAllAvailableFoodItemForZipCodes(zipCodes);
		
		System.out.println(zipCodes);
		
	}
	
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
