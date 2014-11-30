package com.grubmenow.service.model;

import lombok.Data;

@Data
public class SubmitGeneralFeedbackRequest {
    private FeedbackType feedbackType;
    private String zipcode;
    private String emailId;
	private String feedbackMessage; 
}
