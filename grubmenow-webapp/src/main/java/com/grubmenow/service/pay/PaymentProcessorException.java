package com.grubmenow.service.pay;

public class PaymentProcessorException extends Exception {
	public PaymentProcessorException(String message, Exception ex) {
		super(message, ex);
	}
}
