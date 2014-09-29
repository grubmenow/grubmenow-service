package com.grubmenow.service.api;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.joda.time.DateTime;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.grubmenow.service.datamodel.FoodItemDAO;
import com.grubmenow.service.datamodel.FoodItemOfferDAO;
import com.grubmenow.service.datamodel.ObjectPopulator;
import com.grubmenow.service.datamodel.ProviderDAO;
import com.grubmenow.service.model.AvailableDay;
import com.grubmenow.service.model.FoodItem;
import com.grubmenow.service.model.FoodItemDetailPageRequest;
import com.grubmenow.service.model.FoodItemDetailPageResponse;
import com.grubmenow.service.model.ProviderFoodItemOffer;
import com.grubmenow.service.persist.PersistenceFactory;

@RestController
public class GetDetailPageResultsService implements IService<FoodItemDetailPageRequest, FoodItemDetailPageResponse> {

	@Override
	@RequestMapping(value = "/api/getDetailPageResults", method = RequestMethod.POST, produces = "application/json; charset=utf-8")
	@ResponseBody
	public FoodItemDetailPageResponse executeService(@RequestBody FoodItemDetailPageRequest request) {

		// food item
		FoodItemDetailPageResponse response = new FoodItemDetailPageResponse();

		response.setFoodItem(populateFoodItem(request.getFoodItemId()));
		response.setProviderFoodItemOffers(populateProviderFoodItemOffers(request.getFoodItemId(), Arrays.asList(request.getZipCode()),
				request.getAvailableDay()));
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

	private List<ProviderFoodItemOffer> populateProviderFoodItemOffers(String foodItemId, List<String> zipCodes, AvailableDay availableDay) {

		DateTime forDate = DateTime.now();
		if (availableDay == AvailableDay.TOMORROW) {
			forDate = forDate.plusDays(1);
		}

		List<FoodItemOfferDAO> foodItemOfferDAOs = PersistenceFactory.getInstance().getCurrentProviderOfferingWithinZipCodes(foodItemId, zipCodes, forDate);

		List<ProviderFoodItemOffer> providerFoodItemOffers = new ArrayList<>();

		for (FoodItemOfferDAO foodItemOfferDAO : foodItemOfferDAOs) {

			ProviderFoodItemOffer providerFoodItemOffer = new ProviderFoodItemOffer();

			ProviderDAO providerDAO = PersistenceFactory.getInstance().getProviderById(foodItemOfferDAO.getProviderId());
			providerFoodItemOffer.setProvider(ObjectPopulator.toProvider(providerDAO));

			providerFoodItemOffer.setFoodItemOffer(ObjectPopulator.toFoodItemOffer(foodItemOfferDAO));
			
			providerFoodItemOffers.add(providerFoodItemOffer);
		}

		return providerFoodItemOffers;
	}

}
