package com.grubmenow.service;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Calendar;
import java.util.HashSet;

import org.apache.commons.lang3.RandomStringUtils;

import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.regions.Region;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClient;
import com.grubmenow.service.datamodel.FoodItemDAO;
import com.grubmenow.service.datamodel.FoodItemOfferDAO;
import com.grubmenow.service.datamodel.ProviderDAO;
import com.grubmenow.service.persist.PersistenceHandlerImpl;
import com.grubmenow.service.persist.PersistenceHandlerInterface;

public class PersistTest {

	public static void main(String[] args) {
		AWSCredentials awsCredentials = new BasicAWSCredentials("", "");
		AmazonDynamoDBClient awsDynamoDBClient = new AmazonDynamoDBClient(awsCredentials);
		awsDynamoDBClient.setRegion(Region.getRegion(Regions.US_WEST_2));
		PersistenceHandlerInterface persistenceHandlerInterface = new PersistenceHandlerImpl(awsDynamoDBClient);
		
//		testFoodItem(persistenceHandlerInterface);
//		testProvider(persistenceHandlerInterface);
		testFoodItemOffer(persistenceHandlerInterface);

	}
	
	private static void testFoodItem(PersistenceHandlerInterface persistenceHandlerInterface) {
		FoodItemDAO foodItem = new FoodItemDAO();
		foodItem.setFoodItemId(RandomStringUtils.randomAlphanumeric(8));
		foodItem.setFoodItemDescription("Rice Idly");
		foodItem.setFoodItemImageUrl("http://abc.com/img.url");
		foodItem.setFoodItemName("Idly");
		
		persistenceHandlerInterface.createFoodItem(foodItem);
	}
	
	private static void testProvider(PersistenceHandlerInterface persistenceHandlerInterface) {
		ProviderDAO provider = new ProviderDAO();
		provider.setProviderId(RandomStringUtils.randomAlphanumeric(8));
		provider.setProviderName("First Provider");
		provider.setProviderAddressStreetNumber("4723");
		provider.setProviderAddressStreet("148TH AVE NE");
		provider.setProviderAddressApartmentNumber("XX 101");
		provider.setProviderAddressZipCode("98007");
		provider.setProviderAddressState("WA");
		provider.setProviderAddressCity("Bellevue");
		provider.setProviderImageURL("http://abc.com/img.url");
		provider.setAcceptedPaymentMethods(new HashSet<String>(Arrays.asList("credit_card", "on_delivery")));
		
		persistenceHandlerInterface.createProvider(provider);
	}
	
	private static void testFoodItemOffer(PersistenceHandlerInterface persistenceHandlerInterface) {
		FoodItemOfferDAO foodItemOffer = new FoodItemOfferDAO();
		foodItemOffer.setFoodItemOfferId(RandomStringUtils.randomAlphanumeric(8));
		foodItemOffer.setProviderId(RandomStringUtils.randomAlphanumeric(8));
		foodItemOffer.setFoodItemId(RandomStringUtils.randomAlphanumeric(8));
		foodItemOffer.setMealType("dinner");
		foodItemOffer.setOfferDay(Calendar.getInstance());
		foodItemOffer.setProviderFoodItemOfferDescription("Special Idly");
		foodItemOffer.setProviderFoodItemOfferCurrency("USD");
		foodItemOffer.setProviderFoodItemOfferUnitPrice(new BigDecimal(10));
		foodItemOffer.setProviderFoodItemOfferUnitQuantity(6);

		persistenceHandlerInterface.createFoodItemOffer(foodItemOffer);
	}


}
