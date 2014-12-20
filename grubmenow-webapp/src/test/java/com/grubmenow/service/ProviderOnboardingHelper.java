package com.grubmenow.service;

import org.apache.commons.lang3.RandomStringUtils;
import org.joda.time.DateTime;

import com.grubmenow.service.datamodel.FoodItemDAO;
import com.grubmenow.service.datamodel.FoodItemOfferDAO;
import com.grubmenow.service.datamodel.FoodItemState;
import com.grubmenow.service.datamodel.IDGenerator;
import com.grubmenow.service.datamodel.OfferMealType;
import com.grubmenow.service.datamodel.OfferState;
import com.grubmenow.service.datamodel.ProviderDAO;
import com.grubmenow.service.datamodel.ProviderState;
import com.grubmenow.service.model.Currency;
import com.grubmenow.service.persist.PersistenceFactory;


public class ProviderOnboardingHelper {

	public static void main(String[] args) {
//		setUpItem();
//		onboardProvider();
		setUpOffer();
	}
	
	private static void onboardProvider() {

		String providerName = "Kavya's Kitchen";
		String providerEmailId = "kavyashreekp@gmail.com";
		String providerStreetNumber = "4275";
		String providerAddressStreet = "148th AVE NE";
		String providerApartmentNumber = "F 102";
		String providerZipCode = "98007";
		String providerState = "WA";
		String providerCity = "Bellevue";
		String providerPhoneNumber = "2068836875";
		
		ProviderDAO provider = new ProviderDAO();
		provider.setProviderId(RandomStringUtils.randomAlphanumeric(8));
		provider.setProviderName(providerName);
		provider.setProviderEmailId(providerEmailId);
		provider.setProviderAddressStreetNumber(providerStreetNumber);
		provider.setProviderAddressStreet(providerAddressStreet);
		provider.setProviderAddressApartmentNumber(providerApartmentNumber);
		provider.setProviderAddressZipCode(providerZipCode);
		provider.setProviderAddressState(providerState);
		provider.setProviderAddressCity(providerCity);
		provider.setProviderPhoneNumber(providerPhoneNumber);
		provider.setTotalRatingPoints(0);
		provider.setNumberOfRatings(0);
		provider.setProviderState(ProviderState.ACTIVE);
		provider.setIsCardOnDeliverPaymentAccepted(false);
		provider.setIsCashOnDeliverPaymentAccepted(true);
		provider.setIsOnlinePaymentAccepted(true);
		
		PersistenceFactory.getInstance().createProvider(provider);
	}
	
	public static void setUpItem() {
		
		// following inputs will be required
		String foodItemName = "Akki Rotti";
		String imageUrl = "http://grubmenow.com/img/akki-rotti.jpg";
		
		FoodItemDAO foodItem = new FoodItemDAO();
		foodItem.setFoodItemId(RandomStringUtils.randomAlphanumeric(8));
		foodItem.setFoodItemName(foodItemName);
		foodItem.setFoodItemDescription("");
		foodItem.setFoodItemImageUrl(imageUrl);
		foodItem.setFoodItemState(FoodItemState.ACTIVE);
		PersistenceFactory.getInstance().createFoodItem(foodItem);
	}

	
	public static void setUpOffer() {

		String foodItemId = "225636";
		String providerId = "zvpZa8ub";
		String offerDescription = "Grilled cottage cheese cubes in tomato sauce. (1 bowl)";
		int offerUnitPrice = 899;
		int offerQuantity = 15;
		String offerTags = "Spicy,North Indian,Gravy";
		DateTime dateTime = DateTime.now();
		dateTime = dateTime.withMonthOfYear(12).withDayOfMonth(20)
		        .withTimeAtStartOfDay();
		
		FoodItemOfferDAO foodItemOfferDAO = new FoodItemOfferDAO();
		foodItemOfferDAO.setFoodItemOfferId(IDGenerator.generateFoodItemOfferId());
		
		foodItemOfferDAO.setFoodItemId(foodItemId);
		foodItemOfferDAO.setProviderId(providerId); // id from database
		foodItemOfferDAO.setOfferDescription(offerDescription);
		foodItemOfferDAO.setOfferUnitPrice(offerUnitPrice);
		foodItemOfferDAO.setOfferCurrency(Currency.USD);
		foodItemOfferDAO.setOfferQuantity(offerQuantity);
		foodItemOfferDAO.setAvailableQuantity(offerQuantity);
		foodItemOfferDAO.setOfferDay(dateTime);
		foodItemOfferDAO.setOfferMealType(OfferMealType.DINNER);
		foodItemOfferDAO.setIsFoodDeliveryOptionAvailable(false);
		foodItemOfferDAO.setIsFoodPickUpOptionAvailable(true);
		foodItemOfferDAO.setOfferState(OfferState.ACTIVE);
		foodItemOfferDAO.setOfferDescriptionTags(offerTags);

		PersistenceFactory.getInstance().createFoodItemOffer(
				foodItemOfferDAO);
	}
	
}
