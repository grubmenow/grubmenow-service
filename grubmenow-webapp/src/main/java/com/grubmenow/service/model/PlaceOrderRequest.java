package com.grubmenow.service.model;

import java.util.List;

import lombok.Data;

@Data
public class PlaceOrderRequest {

	private String providerId;
	
	private List<OrderItem> orderItems;
	
	private Amount orderAmount;
	
	private String websiteAuthenticationToken;
	
	private PaymentMethod paymentMethod;
	
	private String onlinePaymentToken;
	
	private DeliveryMethod deliveryMethod;
	
	private String customerName;
	private String customerEmailId;
	private String customerPhoneNumber;
}
