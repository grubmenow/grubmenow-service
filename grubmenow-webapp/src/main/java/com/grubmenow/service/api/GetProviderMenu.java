package com.grubmenow.service.api;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.grubmenow.service.datamodel.AvailableDayDAO;
import com.grubmenow.service.datamodel.FoodItemOfferDAO;
import com.grubmenow.service.datamodel.ProviderDAO;
import com.grubmenow.service.datamodel.ProviderState;
import com.grubmenow.service.model.Amount;
import com.grubmenow.service.model.AvailableDay;
import com.grubmenow.service.model.Currency;
import com.grubmenow.service.model.GetProviderMenuRequest;
import com.grubmenow.service.model.GetProviderMenuResponse;
import com.grubmenow.service.model.MealType;
import com.grubmenow.service.model.ProviderFoodItemAvailability;
import com.grubmenow.service.model.ProviderFoodItemOffer;
import com.grubmenow.service.model.exception.ValidationException;
import com.grubmenow.service.persist.PersistenceFactory;

@RestController
public class GetProviderMenu  {

	@RequestMapping(value = "/getProviderMenu", method=RequestMethod.POST)
	@ResponseBody
	protected GetProviderMenuResponse doPost(@RequestBody GetProviderMenuRequest getProviderMenuRequest) 
	throws ValidationException
	{
		validateInput(getProviderMenuRequest);
		
		List<FoodItemOfferDAO> allOffersFromProvider = PersistenceFactory.getInstance().getAllOffersByProvider(getProviderMenuRequest.getProviderId(), 
				AvailableDayDAO.valueOf(getProviderMenuRequest.getAvailableDay().name()));
		
		GetProviderMenuResponse response = new GetProviderMenuResponse();
		List<ProviderFoodItemOffer> providerFoodItemOffers = new ArrayList<>();
		response.setProviderMenuItemOffers(providerFoodItemOffers);
		if (allOffersFromProvider == null || allOffersFromProvider.isEmpty())
		{
			return response;
		}
		
		for(FoodItemOfferDAO foodItemOfferDAO: allOffersFromProvider)
		{	
			ProviderFoodItemOffer providerFoodItemOffer = new ProviderFoodItemOffer();
			providerFoodItemOffers.add(providerFoodItemOffer);
			providerFoodItemOffer.setDistance(0);
			providerFoodItemOffer.setFoodItemId(foodItemOfferDAO.getFoodItemId());
			providerFoodItemOffer.setProviderItemDescription(foodItemOfferDAO.getOfferDescription());
			
			Amount amount = new Amount();
			amount.setCurrency(Currency.valueOf(foodItemOfferDAO.getOfferCurrency()));
			amount.setValue(foodItemOfferDAO.getOfferUnitPrice().floatValue());
			providerFoodItemOffer.setProviderItemCost(amount);
			
			ProviderFoodItemAvailability providerFoodItemAvailability = new ProviderFoodItemAvailability();
			providerFoodItemAvailability.setAvailableDay(getProviderMenuRequest.getAvailableDay());
			providerFoodItemAvailability.setMealType(MealType.valueOf(foodItemOfferDAO.getOfferMealType().name()));
			providerFoodItemAvailability.setQuantityAvailable(foodItemOfferDAO.getAvailableQuantity());
			providerFoodItemOffer.setProviderFoodItemAvailability(providerFoodItemAvailability);
		}
		
		return response;
	}
	
	@ExceptionHandler(ValidationException.class)
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	  public @ResponseBody
	   Map<String,Object> handleValidationException(ValidationException ex,
	                                           HttpServletRequest request, HttpServletResponse resp) {
	     HashMap<String, Object> result = new HashMap<>();
	     result.put("error", true);
	     result.put("error_message", ex.getMessage());
	     return result;
	  }

	private void validateProvider(String providerId) {
		ProviderDAO providerDAO = PersistenceFactory.getInstance().getProviderById(providerId);
		if (providerDAO == null)
		{
			throw new ValidationException ("Provider not found for the id: " + providerId);
		}
		if (providerDAO.getProviderState() == ProviderState.INACTIVE)
		{
			throw new ValidationException("Provider state is not Active");
		}
	}

	private void validateInput(GetProviderMenuRequest getProviderMenuRequest) {
		if (StringUtils.isEmpty(getProviderMenuRequest.getProviderId()))
		{
			throw new ValidationException("Provider id cannot be null or empty");
		}
		
		if (getProviderMenuRequest.getAvailableDay() == null)
		{
			throw new ValidationException("Available day cannot be null, the possible values are: " + AvailableDay.values());
		}
		
		validateProvider(getProviderMenuRequest.getProviderId());
	}
}
