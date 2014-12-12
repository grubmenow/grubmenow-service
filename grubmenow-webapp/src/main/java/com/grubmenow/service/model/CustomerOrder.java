package com.grubmenow.service.model;

import lombok.Data;

import com.grubmenow.service.datamodel.OrderState;

@Data
public class CustomerOrder {
	
	private String orderId;
	
	private OrderState orderState;

	private String orderCreationDate;
	private String orderFulfilmentDate;
	
	private Amount orderAmount;
	private PaymentMethod paymentMethod;
	
	private Amount taxAmount;
	
	private DeliveryMethod deliveryMethod;
	private String deliveryAddress;
}
