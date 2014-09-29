package com.grubmenow.service.model;

import lombok.Data;

@Data
public class FoodItem {
	private String foodItemId;
	private String foodItemName;
	private String foodItemImageUrl;
	private String foodItemDescription;
}
