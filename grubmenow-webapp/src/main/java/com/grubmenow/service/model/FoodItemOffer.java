package com.grubmenow.service.model;

import lombok.Data;

@Data
public class FoodItemOffer {
	
	private String foodItemOfferId;
	
	private String foodItemId;
	
	private String providerId;
	
	private String offerDescription;
	
	private int quantityAvailable;
	
	private String offerDay;
	
	private AvailableDay availableDay;
	
	private MealType mealType;
}
