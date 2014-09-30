package com.grubmenow.service.model;

import java.util.List;

import lombok.Data;

@Data
public class GetProviderMenuResponse {
	
	private Provider provider;
	
	private List<ProviderFoodItemOffer> providerFoodItemOffers;
}
