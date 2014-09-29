package com.grubmenow.service.model;

import lombok.Data;

@Data
public class SearchQuery {

	private String zipCode;
	private int radius; // miles
	private AvailableDay availableDay;
}
