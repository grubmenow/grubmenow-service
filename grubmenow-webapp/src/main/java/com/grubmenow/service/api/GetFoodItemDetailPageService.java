package com.grubmenow.service.api;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
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
import com.grubmenow.service.model.GetFoodItemDetailPageRequest;
import com.grubmenow.service.model.GetFoodItemDetailPageResponse;
import com.grubmenow.service.model.ProviderFoodItemOffer;
import com.grubmenow.service.persist.PersistenceFactory;

@RestController
public class GetFoodItemDetailPageService extends AbstractRemoteService {

	@RequestMapping(value = "/getDetailPageResults", method = RequestMethod.POST, produces = "application/json; charset=utf-8")
	@ResponseBody
	public GetFoodItemDetailPageResponse executeService(@RequestBody GetFoodItemDetailPageRequest request) {

		if(StringUtils.isBlank(request.getZipCode()) || request.getRadius() == 0) {
			request.setZipCode("98007");
			request.setRadius(10000);
		}
		
		List<String> neighboringZipCodes = getAllNeighboringZipCodes(request.getZipCode(), request.getRadius());

		// food item
		GetFoodItemDetailPageResponse response = new GetFoodItemDetailPageResponse();
		
		response.setFoodItem(populateFoodItem(request.getFoodItemId()));
		
		response.setProviderFoodItemOffers(populateProviderFoodItemOffers(request.getFoodItemId(), neighboringZipCodes, request.getAvailableDay()));
		return response;
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

			ProviderDAO providerDAO = PersistenceFactory.getInstance().getProviderById(foodItemOfferDAO.getProviderId());
			
			ProviderFoodItemOffer providerFoodItemOffer = new ProviderFoodItemOffer();
			providerFoodItemOffer.setProvider(ObjectPopulator.toProvider(providerDAO));
			providerFoodItemOffer.setFoodItemOffer(ObjectPopulator.toFoodItemOffer(foodItemOfferDAO));

			providerFoodItemOffers.add(providerFoodItemOffer);
		}

		return providerFoodItemOffers;
	}

	private List<String> getAllNeighboringZipCodes(String zipCode, int radius) {
		return PersistenceFactory.getInstance().getNeighbouringZipCodes(zipCode, radius);
	}

}
