package com.grubmenow.service.model;

import lombok.Data;

@Data
public class SubmitGeneralFeedbackRequest {
    private FeedbackType feedbackType;
    private String zipCode;
    private String emailId;
	private String feedbackMessage; 
	private String searchDay;
}
