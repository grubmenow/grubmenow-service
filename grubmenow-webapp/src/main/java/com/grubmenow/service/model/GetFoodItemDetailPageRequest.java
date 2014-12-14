package com.grubmenow.service.model;

import lombok.Data;

@Data
public class GetFoodItemDetailPageRequest {
	private String foodItemId;
	private AvailableDay availableDay;
	private int timezoneOffsetMins;
	private String zipCode;
	private int radius;
}
