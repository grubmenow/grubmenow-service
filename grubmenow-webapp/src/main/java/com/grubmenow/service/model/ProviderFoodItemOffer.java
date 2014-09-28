package com.grubmenow.service.model;

import java.util.List;

import lombok.Data;

@Data
public class ProviderFoodItemOffer {
	String foodItemId;
	String providerItemDescription;
	Currency providerItemCost;
	float distance;
	List<ProviderFoodItemAvailability> providerItemAvailabilities;
}
