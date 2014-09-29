package com.grubmenow.service.model;

import lombok.Data;

@Data
public class ProviderFoodItemAvailability {
	int quantityAvailable;
	AvailableDay availableDay;
	String availableDate; // actual date 2014-09-28
	MealType mealType;
}
