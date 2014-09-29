package com.grubmenow.service.model;

import lombok.Data;

@Data
public class FoodItemDetailPageRequest {

	private String foodItemId;
	
	private AvailableDay availableDay;
	
	private String[] zipCode;
}
