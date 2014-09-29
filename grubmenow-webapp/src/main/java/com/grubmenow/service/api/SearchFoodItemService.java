package com.grubmenow.service.api;

import java.util.ArrayList;
import java.util.List;

import org.joda.time.DateTime;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.grubmenow.service.datamodel.FoodItemDAO;
import com.grubmenow.service.model.AvailableDay;
import com.grubmenow.service.model.FoodItem;
import com.grubmenow.service.model.SearchQuery;
import com.grubmenow.service.persist.PersistenceFactory;

@RestController
public class SearchFoodItemService implements
		IService<SearchQuery, List<FoodItem>> {

	@RequestMapping(value = "/api/searchFoodItems", method=RequestMethod.POST, produces = "application/json; charset=utf-8")
	@ResponseBody
	@Override
	public List<FoodItem> executeService(@RequestBody SearchQuery request) {

		List<String> neighboringZipCodes = getAllNeighboringZipCodes(request.getZipCode(), request.getRadius());

		// get all the distinct food items in the related zip code
		List<FoodItemDAO> foodItemsAround = getAllFoodItemsInZipCodes(neighboringZipCodes, request.getAvailableDay());
		List<FoodItem> foodItems = new ArrayList<FoodItem>();

		if (foodItemsAround != null) {
			for (FoodItemDAO dao : foodItemsAround) {
				FoodItem foodItem = new FoodItem();
				foodItem.setFoodItemId(dao.getFoodItemId());
				foodItem.setFoodItemName(dao.getFoodItemName());
				foodItem.setFoodItemDescription(dao.getFoodItemDescription());
				foodItem.setFoodItemImageUrl(dao.getFoodItemImageUrl());
				foodItems.add(foodItem);
			}
		}

		return foodItems;
	}

	private List<FoodItemDAO> getAllFoodItemsInZipCodes(
			List<String> neighboringZipCodes, AvailableDay availableDay) {
		
		DateTime forDate = DateTime.now();
		if(availableDay == AvailableDay.TOMORROW) {
			forDate = forDate.plusDays(1);
		}
		
		return PersistenceFactory.getInstance().getAllAvailableFoodItemForZipCodes(neighboringZipCodes, forDate);
	}

	private List<String> getAllNeighboringZipCodes(String zipCode, int radius) {
		return PersistenceFactory.getInstance().getNeighbouringZipCodes(
				zipCode, radius);
	}

	@Override
	public Class<SearchQuery> getRequestClass() {
		return SearchQuery.class;
	}

}
