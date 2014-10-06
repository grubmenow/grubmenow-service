package com.grubmenow.service.model;

import lombok.Data;

@Data
public class OrderItem {

	private String foodItemOfferId;
	
	private int quantity;
}
