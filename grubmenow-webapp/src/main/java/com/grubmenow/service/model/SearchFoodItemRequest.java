package com.grubmenow.service.model;

import lombok.Data;

@Data
public class SearchFoodItemRequest {
	private String zipCode;
	private int radius; // miles
	private AvailableDay availableDay;
	private int timezoneOffsetMins; // client's local timezone offset from UTC, optional. Default 0 
}
