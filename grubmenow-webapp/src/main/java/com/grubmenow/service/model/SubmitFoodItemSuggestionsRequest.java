package com.grubmenow.service.model;

import lombok.Data;

@Data
public class SubmitFoodItemSuggestionsRequest {
	private String foodItemSuggestions;
	// zip code of where the feedback is suggested from. Optional
	private String zipCode;
	// Optional email id if the user wants to provide for contacting them when the item(s) are available
	private String emailId;
}
