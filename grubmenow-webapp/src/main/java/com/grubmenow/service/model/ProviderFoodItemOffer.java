package com.grubmenow.service.model;

import lombok.Data;

@Data
public class ProviderFoodItemOffer 
{
	private Provider provider;
	private FoodItem foodItem;
	private FoodItemOffer foodItemOffer;
}
