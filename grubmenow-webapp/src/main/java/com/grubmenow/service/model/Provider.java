package com.grubmenow.service.model;

import lombok.Data;

@Data
public class Provider {

	private String providerId;
	
	private String providerName;
	
	private String providerAddress;
	
	private String providerPhoneNumber;
	
	private String providerImageURL;
	
	private String rating;
	
	private boolean isOnlinePaymentAccepted;
	
	private boolean isCashOnDeliverPaymentAccepted;
	
	private boolean isCardOnDeliverPaymentAccepted;
}
