package com.grubmenow.service.model;

import lombok.Data;

@Data
public class GetCustomerOrderRequest {
	
	private String websiteAuthenticationToken;
	
	private String orderId;
	
}
