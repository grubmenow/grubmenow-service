package com.grubmenow.service.api;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.IOUtils;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.grubmenow.service.datamodel.FoodItemDAO;
import com.grubmenow.service.model.FoodItem;
import com.grubmenow.service.model.SearchQuery;
import com.grubmenow.service.persist.PersistenceFactory;

public class SearchFoodItems extends HttpServlet {
	private static ObjectMapper objectMapper = new ObjectMapper();

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		
		StringWriter sw = new StringWriter();
		IOUtils.copy(req.getInputStream(), sw);
		
		System.out.println(sw.toString());
		
		SearchQuery searchQuery = objectMapper.readValue(sw.toString(), SearchQuery.class);
		// get all the zip codes in the given radius
		List<String> neighboringZipCodes = getAllNeighboringZipCodes(searchQuery.getZipCode(), searchQuery.getRadius());
		
		// get all the distinct food items in the related zip code
		List<FoodItemDAO> foodItemsAround = getAllFoodItemsInZipCodes(neighboringZipCodes);
		List<FoodItem> foodItems = new ArrayList<FoodItem>();
		if (foodItemsAround == null || foodItemsAround.isEmpty())
		{
			resp.setContentType("application/json");
			String jsonObject = objectMapper.writeValueAsString(foodItemsAround);
			PrintWriter out = resp.getWriter();
			out.print(jsonObject);
			out.flush();
			return;
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
		resp.setContentType("application/json");
		PrintWriter out = resp.getWriter();
		
		String jsonObject = objectMapper.writeValueAsString(foodItems);
		out.print(jsonObject);
		out.flush();
	}

	private List<FoodItemDAO> getAllFoodItemsInZipCodes(
			List<String> neighboringZipCodes) {
		return PersistenceFactory.getInstance().getAllAvailableFoodItemForZipCodes(neighboringZipCodes);
	}

	private List<String> getAllNeighboringZipCodes(String zipCode, int radius) {
		return PersistenceFactory.getInstance().getNeighbouringZipCodes(zipCode, radius);
	}

}
