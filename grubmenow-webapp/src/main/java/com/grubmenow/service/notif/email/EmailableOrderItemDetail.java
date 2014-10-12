package com.grubmenow.service.notif.email;

import lombok.Data;

import com.grubmenow.service.model.Amount;

@Data
// TODO: kapila (11th Oct): Discuss with Sunshine if we can reuse some other object. 
public class EmailableOrderItemDetail {
	private String foodItemName;
	private String foodItemDescription;
	private int foodItemQuantity;
	private Amount foodItemTotalPrice;
}
