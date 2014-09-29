package com.grubmenow.service.api;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.HashMap;

import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.grubmenow.service.datamodel.FoodItemDAO;
import com.grubmenow.service.datamodel.FoodItemOfferDAO;
import com.grubmenow.service.model.FoodItem;
import com.grubmenow.service.model.GetProviderMenuRequest;
import com.grubmenow.service.model.GetProviderMenuResponse;
import com.grubmenow.service.model.ProviderFoodItemOffer;
import com.grubmenow.service.model.SearchQuery;
import com.grubmenow.service.persist.PersistenceFactory;
import com.grubmenow.service.persist.PersistenceHandlerImpl;

@RestController
public class GetProviderMenu  {

	@RequestMapping(value = "/getProviderMenu", method=RequestMethod.POST)
	@ResponseBody
	protected GetProviderMenuResponse doPost(@RequestBody GetProviderMenuRequest getProviderMenuRequest) 
	{
		List<FoodItemOfferDAO> allOffersFromProvider = PersistenceFactory.getInstance().getAllOffersByProvider(getProviderMenuRequest.getProviderId());
		GetProviderMenuResponse response = new GetProviderMenuResponse();
		List<ProviderFoodItemOffer> providerFoodItemOffers = new ArrayList<>();
		response.setProviderFoodItemMenuItems(providerFoodItemOffers);
		Map<String, ProviderFoodItemOffer> providerFoodItems = new HashMap<>();
		if (allOffersFromProvider == null || allOffersFromProvider.isEmpty())
		{
			return response;
		}
		
		for(FoodItemOfferDAO foodItemOfferDAO: allOffersFromProvider)
		{	
			ProviderFoodItemOffer foodItemOffer = providerFoodItems.get(foodItemOfferDAO.getProviderId());
			if (foodItemOffer == null)
			{
				foodItemOffer = new ProviderFoodItemOffer();
				providerFoodItems.put(foodItemOfferDAO.getProviderId(), foodItemOffer);
			}
			
//			foodItemOffer.
		}
		
		return response;
	}

}
