package com.grubmenow.service.model;

import java.math.BigDecimal;

import lombok.Data;

@Data
public class Provider {

	private String providerId;
	
	private String providerName;
	
	private String providerAddress;
	
	private String providerPhoneNumber;
	
	private String providerImageURL;
	
	private String zipCode;
	
	private BigDecimal rating;
	
	private boolean isOnlinePaymentAccepted;
	
	private boolean isCashOnDeliverPaymentAccepted;
	
	private boolean isCardOnDeliverPaymentAccepted;
}
