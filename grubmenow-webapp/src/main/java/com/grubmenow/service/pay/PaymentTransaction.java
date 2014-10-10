package com.grubmenow.service.pay;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class PaymentTransaction {
	private String transactionId;
	private boolean live;
}
