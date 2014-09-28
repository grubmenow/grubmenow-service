package com.grubmenow.service.model;

import lombok.Data;

@Data
public class ProviderFoodItemAvailability {
	int quantityAvailable;
	String availableDateDescription;
	String availableDay;
	String mealType;
}
