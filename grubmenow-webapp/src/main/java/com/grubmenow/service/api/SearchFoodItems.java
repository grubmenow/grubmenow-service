package com.grubmenow.service.api;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.joda.time.DateTime;
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

import com.grubmenow.service.datamodel.FoodItemDAO;
import com.grubmenow.service.model.FoodItem;
import com.grubmenow.service.model.SearchQuery;
import com.grubmenow.service.model.exception.ValidationException;
import com.grubmenow.service.persist.PersistenceFactory;

@RestController
public class SearchFoodItems  {

	@RequestMapping(value = "/api/searchFoodItems", method=RequestMethod.POST)
	@ResponseBody
	protected List<FoodItem> doPost(@RequestBody SearchQuery searchQuery)
	throws ValidationException
	{
		validateInput(searchQuery);
		// get all the zip codes in the given radius
		List<String> neighboringZipCodes = getAllNeighboringZipCodes(searchQuery.getZipCode(), searchQuery.getRadius());
		
		// get all the distinct food items in the related zip code
		List<FoodItemDAO> foodItemsAround = getAllFoodItemsInZipCodes(neighboringZipCodes);
		List<FoodItem> foodItems = new ArrayList<FoodItem>();
		if (foodItemsAround == null || foodItemsAround.isEmpty())
		{
			return foodItems;
		}
		
		for (FoodItemDAO dao: foodItemsAround)
		{
			FoodItem foodItem = new FoodItem();
			foodItem.setFoodItemId(dao.getFoodItemId());
			foodItem.setFoodItemName(dao.getFoodItemName());
			foodItem.setFoodItemDescription(dao.getFoodItemDescription());
			foodItem.setFoodItemImageUrl(dao.getFoodItemImageUrl());
			foodItems.add(foodItem);
		}
		return foodItems;
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
	
	private void validateInput(SearchQuery searchQuery) {
		if (StringUtils.isEmpty(searchQuery.getZipCode()))
		{
			throw new ValidationException("ZipCode is null or empty");
		}
		
		if (searchQuery.getRadius() <= 0)
		{
			throw new ValidationException("Radius should be > 0");
		}
		
		if (searchQuery.getAvailableDay() == null)
		{
			throw new ValidationException("AvailableDay should be present");
		}
		
	}

	private List<FoodItemDAO> getAllFoodItemsInZipCodes(
			List<String> neighboringZipCodes) {
		return PersistenceFactory.getInstance().getAllAvailableFoodItemForZipCodes(neighboringZipCodes, DateTime.now());
	}

	private List<String> getAllNeighboringZipCodes(String zipCode, int radius) {
		return PersistenceFactory.getInstance().getNeighbouringZipCodes(zipCode, radius);
	}

}
