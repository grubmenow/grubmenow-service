package com.grubmenow.service.api.model;

import lombok.Data;

@Data
public class SearchQuery {
	String zipCode;
	int radius; // miles
	String availableDay;
}
