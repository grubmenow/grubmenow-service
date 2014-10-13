package com.grubmenow.service;

import com.grubmenow.service.model.Currency;
import com.grubmenow.service.pay.PaymentProcessorException;
import com.grubmenow.service.pay.PaymentTransaction;
import com.grubmenow.service.pay.StripePaymentProcessor;

public class StripeTest {
	public static void main(String[] args) throws PaymentProcessorException {
		new StripeTest().charge();
	}
	private void charge() throws PaymentProcessorException
	{
		// grubmenow test secret key is  sk_test_jXSLNqhMVvsziLZWgHg0vVJy 
		StripePaymentProcessor processor = new StripePaymentProcessor("sk_test_jXSLNqhMVvsziLZWgHg0vVJy");
		
		PaymentTransaction id = processor.charge("tok_14kR79CxEv9dhAFuk1wudmBu", 5000, Currency.USD, "emailId", "orderId");
		System.out.println(id.getTransactionId());
		System.out.println(id.isLive());
	}
}
