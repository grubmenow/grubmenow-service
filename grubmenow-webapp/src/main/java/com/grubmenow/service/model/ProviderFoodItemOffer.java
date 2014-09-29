package com.grubmenow.service.model;

import java.util.List;

import lombok.Data;

@Data
public class ProviderFoodItemOffer {

	private Provider provider;
	
	private List<FoodItemOffer> foodItemOffers;
}
