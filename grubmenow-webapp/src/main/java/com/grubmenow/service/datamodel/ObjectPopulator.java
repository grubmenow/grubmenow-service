package com.grubmenow.service.datamodel;

import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

import com.grubmenow.service.model.Amount;
import com.grubmenow.service.model.AvailableDay;
import com.grubmenow.service.model.Currency;
import com.grubmenow.service.model.CustomerOrder;
import com.grubmenow.service.model.CustomerOrderItem;
import com.grubmenow.service.model.FoodItem;
import com.grubmenow.service.model.FoodItemOffer;
import com.grubmenow.service.model.MealType;
import com.grubmenow.service.model.Provider;

public class ObjectPopulator {
	
	private static DateTimeFormatter printableDateTimeFormatter = DateTimeFormat.forPattern("dd MMM, yy");
	
	public static FoodItem toFoodItem(FoodItemDAO foodItemDAO) {
		
		FoodItem foodItem = new FoodItem();
		foodItem.setFoodItemId(foodItemDAO.getFoodItemId());
		foodItem.setFoodItemName(foodItemDAO.getFoodItemName());
		foodItem.setFoodItemDescription(foodItemDAO.getFoodItemDescription());
		foodItem.setFoodItemImageUrl(foodItemDAO.getFoodItemImageUrl());

		return foodItem;
	}
	
	public static Provider toProvider(ProviderDAO providerDAO) {
		Provider provider = new Provider();
		provider.setProviderId(providerDAO.getProviderId());
		provider.setProviderName(providerDAO.getProviderName());
		
		String address = String.format("%s, %s, %s",
				providerDAO.getProviderAddressStreetNumber(),
				providerDAO.getProviderAddressStreet(),
				providerDAO.getProviderAddressCity());
		// TODO: city, state, zipcode
		provider.setProviderAddress(address);
		
		provider.setProviderImageURL(providerDAO.getProviderImageURL());
		provider.setOnlinePaymentAccepted(providerDAO.getIsOnlinePaymentAccepted());
		provider.setCashOnDeliverPaymentAccepted(providerDAO.getIsCashOnDeliverPaymentAccepted());
		provider.setCardOnDeliverPaymentAccepted(providerDAO.getIsCardOnDeliverPaymentAccepted());
		// TODO: phone number
		
		return provider;
	}
	
	public static FoodItemOffer toFoodItemOffer(FoodItemOfferDAO foodItemOfferDAO) {
		FoodItemOffer foodItemOffer = new FoodItemOffer();
		foodItemOffer.setFoodItemOfferId(foodItemOfferDAO.getFoodItemOfferId());
		foodItemOffer.setFoodItemId(foodItemOfferDAO.getFoodItemId());
		foodItemOffer.setProviderId(foodItemOfferDAO.getProviderId());
		foodItemOffer.setOfferDescription(foodItemOfferDAO.getOfferDescription());
		foodItemOffer.setAvailableQuantity(foodItemOfferDAO.getAvailableQuantity());
		foodItemOffer.setPrice(new Amount(foodItemOfferDAO.getOfferUnitPrice(), Currency.valueOf(foodItemOfferDAO.getOfferCurrency())));
		foodItemOffer.setOfferDay(foodItemOfferDAO.getOfferDay().toString(printableDateTimeFormatter));
		
		DateTime today = DateTime.now();
		DateTime tomorrow = today.plusDays(1);
		
		AvailableDay availableDay = null;
		
		if(foodItemOfferDAO.getOfferDay().getDayOfYear() == today.getDayOfYear() && foodItemOfferDAO.getOfferDay().getDayOfYear() == today.getDayOfYear()) {
			availableDay = AvailableDay.TODAY;
		} else if(foodItemOfferDAO.getOfferDay().getDayOfYear() == tomorrow.getDayOfYear() && foodItemOfferDAO.getOfferDay().getDayOfYear() == tomorrow.getDayOfYear()) {
			availableDay = AvailableDay.TOMORROW;
		} else {
			availableDay = AvailableDay.LATER;
		}
		
		foodItemOffer.setAvailableDay(availableDay);
		foodItemOffer.setMealType(MealType.valueOf(foodItemOfferDAO.getOfferMealType().name()));
		
		return foodItemOffer;
	}
	
	public static CustomerOrder toCustomerOrder(CustomerOrderDAO customerOrderDAO) {

		CustomerOrder customerOrder = new CustomerOrder();
		customerOrder.setOrderId(customerOrderDAO.getOrderId());
		customerOrder.setOrderState(customerOrderDAO.getOrderState());
		customerOrder.setOrderCreationDate(customerOrderDAO.getOrderCreationDate().toString(printableDateTimeFormatter));
		return customerOrder;
	}

	public static CustomerOrderItem toCustomerOrderItem(CustomerOrderItemDAO customerOrderItemDAO) {
		CustomerOrderItem customerOrderItem = new CustomerOrderItem();
		customerOrderItem.setOrderItemId(customerOrderItemDAO.getOrderItemId());
		return customerOrderItem;
	}
}
