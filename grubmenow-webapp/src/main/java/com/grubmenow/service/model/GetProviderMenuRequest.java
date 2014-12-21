package com.grubmenow.service.model;

import lombok.Data;

@Data
public class GetProviderMenuRequest {
	private String providerId;
	private AvailableDay availableDay;
	private int timezoneOffsetMins;
	/**
	 * Optional: Returns the food item excluding this item (if matches any of the offers) 
	 */
	private String excludedFoodItem;
}
