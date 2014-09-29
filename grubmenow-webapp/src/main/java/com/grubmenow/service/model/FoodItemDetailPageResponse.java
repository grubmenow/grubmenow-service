package com.grubmenow.service.model;

import java.util.List;

import lombok.Data;

@Data
public class FoodItemDetailPageResponse {

	private FoodItem foodItem;
	
	private List<ProviderFoodItemOffer> providerFoodItemOffers;
	
}
