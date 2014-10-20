package com.grubmenow.service.model;

import lombok.Data;

@Data
public class GetCustomerOrderFeedbackRequest {
	
	private String websiteAuthenticationToken;
	
	private String orderId;
	
}
