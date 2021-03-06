package com.grubmenow.service.api;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
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
import com.grubmenow.service.model.AvailableDay;
import com.grubmenow.service.model.FoodItem;
import com.grubmenow.service.model.GetFoodItemDetailPageRequest;
import com.grubmenow.service.model.GetFoodItemDetailPageResponse;
import com.grubmenow.service.model.ProviderFoodItemOffer;
import com.grubmenow.service.persist.PersistenceHandler;

@RestController
@CommonsLog
public class GetFoodItemDetailPageService extends AbstractRemoteService
{
    @Autowired
    PersistenceHandler persistenceHandler;

	@RequestMapping(value = "/api/getDetailPageResults", method = RequestMethod.POST, produces = "application/json; charset=utf-8")
	@ResponseBody
	public GetFoodItemDetailPageResponse executeService(@RequestBody GetFoodItemDetailPageRequest request)
	{
		validateInput(request);
		// food item
		GetFoodItemDetailPageResponse response = new GetFoodItemDetailPageResponse();
		DateTime offerDay = getDateTimeForOfferDay(request.getAvailableDay(), request.getTimezoneOffsetMins());
        response.setFormattedOfferDay(ObjectPopulator.readableDay(offerDay));
        response.setFoodItem(populateFoodItem(request.getFoodItemId()));

        // check if the request is after the order cut off time of that day, if
        // so: return empty results
        DateTime orderCutoffTimeWrtClient = TimezoneUtils.calculateOrderCutOffTimeWrtClient(request.getAvailableDay(), request.getTimezoneOffsetMins());
        DateTime currentTimeWrtClient = TimezoneUtils.getCurrentTimeWrtClient(request.getTimezoneOffsetMins());
        if (currentTimeWrtClient.isAfter(orderCutoffTimeWrtClient))
        {
            log.info("Current Time on client [" + currentTimeWrtClient + "] exceeds the order cut off time [ " + orderCutoffTimeWrtClient
                    + " ], returning empty results");
            response.setProviderFoodItemOffers(new ArrayList<ProviderFoodItemOffer>());
            return response;
        }
		response.setProviderFoodItemOffers(populateProviderFoodItemOffers(
		        offerDay,
		        request.getZipCode(), 
		        request.getFoodItemId(),
		        request.getAvailableDay(), 
		        request.getTimezoneOffsetMins()));
		return response;
	}

	private void validateInput(GetFoodItemDetailPageRequest request)
	{
		Validator.notNull(request.getAvailableDay(), "AvailableDay should be present");
		Validator.notBlank(request.getFoodItemId(), "Food item id should be present");
	}

	private FoodItem populateFoodItem(String foodItemId)
	{
		FoodItemDAO foodItemDAO = persistenceHandler.getFoodItemById(foodItemId);
		return ObjectPopulator.toFoodItem(foodItemDAO);
	}

	private List<ProviderFoodItemOffer> populateProviderFoodItemOffers(
	        DateTime offerDay,
	        String zipCode,
	        String foodItemId,
	        AvailableDay availableDay, 
	        int requestTimezoneOffsetMins) 
	{
		List<FoodItemOfferDAO> foodItemOfferDAOs = persistenceHandler.getCurrentProviderOffering(foodItemId, offerDay);

		List<ProviderFoodItemOffer> providerFoodItemOffers = new ArrayList<>();

		for (FoodItemOfferDAO foodItemOfferDAO : foodItemOfferDAOs) {

			ProviderDAO providerDAO = persistenceHandler.getProviderById(foodItemOfferDAO.getProviderId());
			
			ProviderFoodItemOffer providerFoodItemOffer = new ProviderFoodItemOffer();
			providerFoodItemOffer.setProvider(ObjectPopulator.toProvider(providerDAO));
			providerFoodItemOffer.setFoodItemOffer(ObjectPopulator.toFoodItemOffer(foodItemOfferDAO, availableDay));
			providerFoodItemOffer.setDistanceInMiles(persistenceHandler.getDistanceInMilesBetweenZipCodes(zipCode, providerDAO.getProviderAddressZipCode())); 

			providerFoodItemOffers.add(providerFoodItemOffer);
		}
		
		// sort the providers based on distance and the rating
		Collections.sort(providerFoodItemOffers, new Comparator<ProviderFoodItemOffer>() {

			@Override
			public int compare(ProviderFoodItemOffer o1, ProviderFoodItemOffer o2) {
				double double1 = Double.parseDouble(o1.getDistanceInMiles());
				double double2 = Double.parseDouble(o2.getDistanceInMiles());
				
				if (double1 == double2) { // if the distance are equal, then
											// sort is based on rating
					if (o1.getProvider().getRating().compareTo(o2.getProvider().getRating()) > 0) {
						return 1;
					} else if (o1.getProvider().getRating().compareTo(o2.getProvider().getRating()) < 0) {
						return -1;
					} else {
						return 0;
					}
					
				} else if (double1 > double2) {
					return 1;
				} else {
					return -1;
				}
			}
		});
		return providerFoodItemOffers;
	}
	
	private DateTime getDateTimeForOfferDay(AvailableDay availableDay, int requestTimezoneOffsetMins) 
	{
		DateTime forDate = DateTime.now();
        if (availableDay == AvailableDay.Tomorrow) {
            forDate = forDate.plusDays(1);
        }
        forDate = forDate.minusMinutes(requestTimezoneOffsetMins);
        return forDate;
	}
}
