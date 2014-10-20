package com.grubmenow.service.model;

import lombok.Data;

@Data
public class CustomerOrderItem {
	
	private String orderItemId;
	
	private int quantity;
	
	private Amount orderItemAmount;
	
	private FoodItem foodItem;
	
}
