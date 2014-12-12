package com.grubmenow.service.model;

import java.util.List;

import lombok.Data;

@Data
public class GetCustomerOrderResponse {
	private CustomerOrder customerOrder;
	private Provider provider;
	private List<CustomerOrderItem> customerOrderItems;
}
