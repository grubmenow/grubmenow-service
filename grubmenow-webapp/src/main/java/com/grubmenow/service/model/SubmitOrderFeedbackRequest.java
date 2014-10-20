package com.grubmenow.service.model;

import lombok.Data;

@Data
public class SubmitOrderFeedbackRequest {
	
	private String websiteAuthenticationToken;
	
	private int rating = -1;
	
	private String orderId;
	
	private String feedback;
	
}
