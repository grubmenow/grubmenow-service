package com.grubmenow.service.api;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.joda.time.DateTime;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.grubmenow.service.datamodel.FoodItemDAO;
import com.grubmenow.service.model.AvailableDay;
import com.grubmenow.service.model.FoodItem;
import com.grubmenow.service.model.SearchFoodItemRequest;
import com.grubmenow.service.model.exception.ValidationException;
import com.grubmenow.service.persist.PersistenceFactory;

@RestController
public class SearchFoodItemService extends AbstractRemoteService {

	@RequestMapping(value = "/searchFoodItems", method = RequestMethod.POST, produces = "application/json; charset=utf-8")
	@ResponseBody
	public List<FoodItem> executeService(@RequestBody SearchFoodItemRequest request) {

		validateInput(request);

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

	private void validateInput(SearchFoodItemRequest searchQuery) {
		if (StringUtils.isEmpty(searchQuery.getZipCode())) {
			throw new ValidationException("ZipCode is null or empty");
		}

		if (searchQuery.getRadius() <= 0) {
			throw new ValidationException("Radius should be > 0");
		}

		if (searchQuery.getAvailableDay() == null) {
			throw new ValidationException("AvailableDay should be present");
		}
	}

	private List<FoodItemDAO> getAllFoodItemsInZipCodes(List<String> neighboringZipCodes, AvailableDay availableDay) {

		DateTime forDate = DateTime.now();
		if (availableDay == AvailableDay.TOMORROW) {
			forDate = forDate.plusDays(1);
		}

		return PersistenceFactory.getInstance().getAllAvailableFoodItemForZipCodes(neighboringZipCodes, forDate);
	}

	private List<String> getAllNeighboringZipCodes(String zipCode, int radius) {
		return PersistenceFactory.getInstance().getNeighbouringZipCodes(zipCode, radius);
	}

}
