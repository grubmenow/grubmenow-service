package com.grubmenow.service.model;

import lombok.Data;

@Data
public class SearchFoodItemRequest {

	private String zipCode;
	
	private int radius; // miles
	
	private AvailableDay availableDay;
}
