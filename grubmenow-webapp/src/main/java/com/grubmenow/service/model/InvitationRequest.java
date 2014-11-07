package com.grubmenow.service.model;

import lombok.Data;

@Data
public class InvitationRequest {
	// zip code of where the user is located
	private String zipCode;
	// email id if the user wants to provide for contacting them when the item(s) are available
	private String emailId;
}
