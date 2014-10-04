package com.grubmenow.service.pay;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Transaction {
	private String transactionId;
	private boolean live;
}
