package com.grubmenow.service.model;

import java.util.List;

import lombok.Data;

@Data
public class GetProviderMenuResponse {
	List<ProviderFoodItemOffer> providerMenuItemOffers;
}
