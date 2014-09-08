package com.grubmenow.service;

import java.util.ArrayList;
import java.util.List;

import org.apache.thrift.TException;

public class SearchFoodItemHandler implements GrubMeNowService.Iface
{

	@Override
	public List<FoodItem> SearchFoodItems(SearchQuery searchQuery)
			throws TException 
	{
		FoodItem foodItem = new FoodItem();
		foodItem.setFoodItemId("foodItemId");
		foodItem.setFoodItemImageUrl("foodItemImageUrl");
		foodItem.setFoodItemName("foodItemName");
		foodItem.setFoodItemDescription("foodItemDescription");
		
		List<FoodItem> foodItems = new ArrayList<>();
		foodItems.add(foodItem);
		return foodItems;
	}

}
