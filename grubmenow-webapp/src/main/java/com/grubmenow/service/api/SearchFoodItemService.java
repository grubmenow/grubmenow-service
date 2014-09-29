package com.grubmenow.service.api;

import java.util.ArrayList;
import java.util.List;

import com.grubmenow.service.datamodel.FoodItemDAO;
import com.grubmenow.service.model.FoodItem;
import com.grubmenow.service.model.SearchQuery;
import com.grubmenow.service.persist.PersistenceFactory;

public class SearchFoodItemService implements
		IService<SearchQuery, List<FoodItem>> {

	@Override
	public List<FoodItem> executeService(SearchQuery request) {

		List<String> neighboringZipCodes = getAllNeighboringZipCodes(request.getZipCode(), request.getRadius());

		// get all the distinct food items in the related zip code
		List<FoodItemDAO> foodItemsAround = getAllFoodItemsInZipCodes(neighboringZipCodes);
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
			List<String> neighboringZipCodes) {
		return PersistenceFactory.getInstance()
				.getAllAvailableFoodItemForZipCodes(neighboringZipCodes);
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
