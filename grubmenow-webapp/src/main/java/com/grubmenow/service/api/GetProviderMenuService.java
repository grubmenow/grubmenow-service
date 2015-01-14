package com.grubmenow.service.api;

import java.util.ArrayList;
import java.util.List;

import lombok.extern.apachecommons.CommonsLog;

import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
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
import com.grubmenow.service.persist.PersistenceHandler;

@RestController
@CommonsLog
public class GetProviderMenuService extends AbstractRemoteService
{

    @Autowired
    PersistenceHandler persistenceHandler;
	@RequestMapping(value = "/api/getProviderMenu", method = RequestMethod.POST, produces = "application/json; charset=utf-8")
	@ResponseBody
	protected GetProviderMenuResponse doPost(@RequestBody GetProviderMenuRequest request) throws ValidationException {
		
		validateInput(request);
		
		ProviderDAO providerDAO = persistenceHandler.getProviderById(request.getProviderId());
		validateProvider(request.getProviderId(), providerDAO);
		
		DateTime forDate = DateTime.now();
		if (request.getAvailableDay() == AvailableDay.Tomorrow) {
			forDate = forDate.plusDays(1);
		}
		forDate = forDate.minusMinutes(request.getTimezoneOffsetMins());
		
		List<FoodItemOfferDAO> allOffersFromProvider = persistenceHandler.getAllOffersByProvider(request.getProviderId(), forDate);

		GetProviderMenuResponse response = new GetProviderMenuResponse();
		
		response.setProvider(ObjectPopulator.toProvider(providerDAO));
		
		List<ProviderFoodItemOffer> providerFoodItemOffers = new ArrayList<>();
		
		response.setProviderFoodItemOffers(providerFoodItemOffers);
		if (allOffersFromProvider == null) {
			return response;
		}

		for (FoodItemOfferDAO foodItemOfferDAO : allOffersFromProvider) {
		    if (request.getExcludedFoodItem() != null && request.getExcludedFoodItem().equals(foodItemOfferDAO.getFoodItemId()))
		    {
		        log.debug("Food item matches the excluded food item: " + request.getExcludedFoodItem());
		        continue;
		    }
			FoodItemDAO foodItemDAO = persistenceHandler.getFoodItemById(foodItemOfferDAO.getFoodItemId());

			ProviderFoodItemOffer providerFoodItemOffer = new ProviderFoodItemOffer();
			providerFoodItemOffer.setFoodItemOffer(ObjectPopulator.toFoodItemOffer(foodItemOfferDAO, request.getAvailableDay()));
			providerFoodItemOffer.setFoodItem(ObjectPopulator.toFoodItem(foodItemDAO));
			
			providerFoodItemOffers.add(providerFoodItemOffer);
		}

		return response;
	}

	private void validateProvider(String providerId, ProviderDAO providerDAO) {
		Validator.notNull(providerDAO, "Provider not found for the id" + providerId);
		Validator.isTrue(providerDAO.getProviderState() == ProviderState.ACTIVE, "Provider state is not Active");
	}

	private void validateInput(GetProviderMenuRequest getProviderMenuRequest) {
		Validator.notBlank(getProviderMenuRequest.getProviderId(), "Provider id cannot be null or empty");
		Validator.notNull(getProviderMenuRequest.getAvailableDay(), "Available day cannot be null, the possible values are: " + AvailableDay.values());
	}
}
