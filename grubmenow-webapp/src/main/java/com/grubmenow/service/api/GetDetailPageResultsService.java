package com.grubmenow.service.api;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.grubmenow.service.datamodel.FoodItemDAO;
import com.grubmenow.service.datamodel.FoodItemOfferDAO;
import com.grubmenow.service.datamodel.ObjectPopulator;
import com.grubmenow.service.datamodel.ProviderDAO;
import com.grubmenow.service.model.FoodItem;
import com.grubmenow.service.model.FoodItemDetailPageResponse;
import com.grubmenow.service.model.FoodItemDetailPageRequest;
import com.grubmenow.service.model.FoodItemOffer;
import com.grubmenow.service.model.ProviderFoodItemOffer;
import com.grubmenow.service.persist.PersistenceFactory;

public class GetDetailPageResultsService implements IService<FoodItemDetailPageRequest, FoodItemDetailPageResponse> {

	@Override
	public FoodItemDetailPageResponse executeService(FoodItemDetailPageRequest request) {

		// food item
		FoodItemDetailPageResponse response = new FoodItemDetailPageResponse();

		response.setFoodItem(populateFoodItem(request.getFoodItemId()));
		response.setProviderFoodItemOffers(populateProviderFoodItemOffers(request.getFoodItemId(), Arrays.asList(request.getZipCode())));
		return response;
	}

	@Override
	public Class<FoodItemDetailPageRequest> getRequestClass() {
		return FoodItemDetailPageRequest.class;
	}

	private FoodItem populateFoodItem(String foodItemId) {
		FoodItemDAO foodItemDAO = PersistenceFactory.getInstance().getFoodItemById(foodItemId);

		return ObjectPopulator.toFoodItem(foodItemDAO);
	}

	private List<ProviderFoodItemOffer> populateProviderFoodItemOffers(String foodItemId, List<String> zipCodes) {
		List<FoodItemOfferDAO> foodItemOfferDAOs = PersistenceFactory.getInstance().getCurrentProviderOfferingWithinZipCodes(foodItemId, zipCodes);

		List<ProviderFoodItemOffer> providerFoodItemOffers = new ArrayList<>();

		Map<String, ProviderFoodItemOffer> providerToFoodItemOfferMap = new HashMap<>();

		for (FoodItemOfferDAO foodItemOfferDAO : foodItemOfferDAOs) {

			ProviderFoodItemOffer providerFoodItemOffer;

			// find the provider food item offer
			if (providerToFoodItemOfferMap.containsKey(foodItemOfferDAO.getProviderId())) {
				providerFoodItemOffer = providerToFoodItemOfferMap.get(foodItemOfferDAO.getProviderId());
			} else {
				providerFoodItemOffer = new ProviderFoodItemOffer();

				ProviderDAO providerDAO = PersistenceFactory.getInstance().getProviderById(foodItemOfferDAO.getProviderId());
				providerFoodItemOffer.setProvider(ObjectPopulator.toProvider(providerDAO));

				providerToFoodItemOfferMap.put(foodItemOfferDAO.getProviderId(), providerFoodItemOffer);
			}

			if (providerFoodItemOffer.getFoodItemOffers() == null) {
				providerFoodItemOffer.setFoodItemOffers(new ArrayList<FoodItemOffer>());
			}

			FoodItemOffer foodItemOffer = ObjectPopulator.toFoodItemOffer(foodItemOfferDAO);

			providerFoodItemOffer.getFoodItemOffers().add(foodItemOffer);
		}

		for(ProviderFoodItemOffer providerFoodItemOffer: providerToFoodItemOfferMap.values()) {
			providerFoodItemOffers.add(providerFoodItemOffer);
		}
		
		return providerFoodItemOffers;
	}

}
