package com.grubmenow.service.api;

import lombok.extern.apachecommons.CommonsLog;

import org.apache.commons.lang3.StringUtils;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.grubmenow.service.datamodel.SearchSuggestionFeedbackDAO;
import com.grubmenow.service.model.SubmitFoodItemSuggestionsRequest;
import com.grubmenow.service.model.SubmitFoodItemSuggestionsResponse;
import com.grubmenow.service.persist.PersistenceHandler;

@RestController
@CommonsLog
public class SubmitFoodItemSuggestionsService extends AbstractRemoteService 
{
    @Autowired
    PersistenceHandler persistenceHandler;
    private static final String EXPECTED_FOOD_ITEM_NAME_SEPARATOR = "," ;
	@RequestMapping(value = "/api/submitFoodItemSuggestions", method = RequestMethod.POST, produces = "application/json; charset=utf-8")
	@ResponseBody
	public SubmitFoodItemSuggestionsResponse executeService(@RequestBody SubmitFoodItemSuggestionsRequest request) {
	    validateRequest(request);
		log.debug("Feedback request submitted: " + request.getFoodItemSuggestions());

		String[] foodItemNames = StringUtils.split(request.getFoodItemSuggestions(), EXPECTED_FOOD_ITEM_NAME_SEPARATOR);
		for (String foodItemName: foodItemNames)
		{
		    if (StringUtils.isBlank(foodItemName))
		    {
		        continue;
		    }

		    SearchSuggestionFeedbackDAO dao = new SearchSuggestionFeedbackDAO();
		    dao.setFoodItemName(foodItemName);
		    dao.setFeedbackProvidedTime(new DateTime());
		    dao.setEmailId(request.getEmailId());
		    dao.setZipCode(request.getZipCode());
		    persistenceHandler.createSearchSuggestionFeedback(dao);
		}
		SubmitFoodItemSuggestionsResponse response = new SubmitFoodItemSuggestionsResponse();
		response.setFeedbackMessage("Thank you for your feedback.");
		return response;
	}

    private void validateRequest(SubmitFoodItemSuggestionsRequest request)
    {
        Validator.notBlank(request.getFoodItemSuggestions(), "food item suggestions cannot be empty");
    }
}
