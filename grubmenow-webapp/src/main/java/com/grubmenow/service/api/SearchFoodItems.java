package com.grubmenow.service.api;

import java.util.ArrayList;
import java.util.List;

import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.grubmenow.service.datamodel.FoodItemDAO;
import com.grubmenow.service.model.FoodItem;
import com.grubmenow.service.model.SearchQuery;
import com.grubmenow.service.persist.PersistenceFactory;

@RestController
public class SearchFoodItems  {
	private static ObjectMapper objectMapper = new ObjectMapper();

	@RequestMapping(value = "/searchFoodItems", method=RequestMethod.POST)
	@ResponseBody
	protected List<FoodItem> doPost(@RequestBody SearchQuery searchQuery) 
	{
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

	private List<FoodItemDAO> getAllFoodItemsInZipCodes(
			List<String> neighboringZipCodes) {
		return PersistenceFactory.getInstance().getAllAvailableFoodItemForZipCodes(neighboringZipCodes);
	}

	private List<String> getAllNeighboringZipCodes(String zipCode, int radius) {
		return PersistenceFactory.getInstance().getNeighbouringZipCodes(zipCode, radius);
	}

}
