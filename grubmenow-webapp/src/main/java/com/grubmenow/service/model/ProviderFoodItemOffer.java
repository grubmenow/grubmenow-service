package com.grubmenow.service.model;

import lombok.Data;

@Data
public class ProviderFoodItemOffer {
	// TODO: vjain: Is this required?
	private Provider provider;
	private FoodItemOffer foodItemOffer;
	private String foodItemId;
	private String providerItemDescription;
	private Amount providerItemCost;
	private float distance; // distanceInMiles
	private ProviderFoodItemAvailability providerFoodItemAvailability;
}
