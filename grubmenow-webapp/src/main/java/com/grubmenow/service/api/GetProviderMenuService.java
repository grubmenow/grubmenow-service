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
import com.grubmenow.service.datamodel.ProviderState;
import com.grubmenow.service.model.AvailableDay;
import com.grubmenow.service.model.GetProviderMenuRequest;
import com.grubmenow.service.model.GetProviderMenuResponse;
import com.grubmenow.service.model.ProviderFoodItemOffer;
import com.grubmenow.service.model.exception.ValidationException;
import com.grubmenow.service.persist.PersistenceFactory;

@RestController
public class GetProviderMenuService extends AbstractRemoteService {

	@RequestMapping(value = "/getProviderMenu", method = RequestMethod.POST, produces = "application/json; charset=utf-8")
	@ResponseBody
	protected GetProviderMenuResponse doPost(@RequestBody GetProviderMenuRequest request) throws ValidationException {
		
		validateInput(request);
		
		ProviderDAO providerDAO = PersistenceFactory.getInstance().getProviderById(request.getProviderId());
		validateProvider(request.getProviderId(), providerDAO);
		
		DateTime forDate = DateTime.now();
		if (request.getAvailableDay() == AvailableDay.TOMORROW) {
			forDate = forDate.plusDays(1);
		}
		
		List<FoodItemOfferDAO> allOffersFromProvider = PersistenceFactory.getInstance().getAllOffersByProvider(request.getProviderId(), forDate);

		GetProviderMenuResponse response = new GetProviderMenuResponse();
		
		response.setProvider(ObjectPopulator.toProvider(providerDAO));
		
		List<ProviderFoodItemOffer> providerFoodItemOffers = new ArrayList<>();
		
		response.setProviderFoodItemOffers(providerFoodItemOffers);
		if (allOffersFromProvider == null) {
			return response;
		}

		for (FoodItemOfferDAO foodItemOfferDAO : allOffersFromProvider) {
			FoodItemDAO foodItemDAO = PersistenceFactory.getInstance().getFoodItemById(foodItemOfferDAO.getFoodItemId());

			ProviderFoodItemOffer providerFoodItemOffer = new ProviderFoodItemOffer();
			providerFoodItemOffer.setFoodItemOffer(ObjectPopulator.toFoodItemOffer(foodItemOfferDAO));
			providerFoodItemOffer.setFoodItem(ObjectPopulator.toFoodItem(foodItemDAO));
			
			providerFoodItemOffers.add(providerFoodItemOffer);
		}

		return response;
	}

	private void validateProvider(String providerId, ProviderDAO providerDAO) {
		
		if (providerDAO == null) {
			throw new ValidationException("Provider not found for the id" + providerId);
		}
		if (providerDAO.getProviderState() == ProviderState.INACTIVE) {
			throw new ValidationException("Provider state is not Active");
		}
	}

	private void validateInput(GetProviderMenuRequest getProviderMenuRequest) {
		if (StringUtils.isEmpty(getProviderMenuRequest.getProviderId())) {
			throw new ValidationException("Provider id cannot be null or empty");
		}

		if (getProviderMenuRequest.getAvailableDay() == null) {
			throw new ValidationException("Available day cannot be null, the possible values are: " + AvailableDay.values());
		}
	}
}
