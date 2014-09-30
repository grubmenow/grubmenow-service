package com.grubmenow.service.model;

import lombok.Data;

@Data
public class GetFoodItemDetailPageRequest {

	private String foodItemId;
	
	private AvailableDay availableDay;
	
	private String zipCode;
	
	private int radius;
}
