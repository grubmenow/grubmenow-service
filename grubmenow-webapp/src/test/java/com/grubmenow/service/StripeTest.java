package com.grubmenow.service;

import com.grubmenow.service.datamodel.CurrencyDAO;
import com.grubmenow.service.pay.PaymentProcessorException;
import com.grubmenow.service.pay.StripePaymentProcessor;
import com.grubmenow.service.pay.Transaction;

public class StripeTest {
	public static void main(String[] args) throws PaymentProcessorException {
		new StripeTest().testCharge();
	}
	public void testCharge() throws PaymentProcessorException
	{
		// grubmenow test secret key is  sk_test_jXSLNqhMVvsziLZWgHg0vVJy 
		StripePaymentProcessor processor = new StripePaymentProcessor("sk_test_jXSLNqhMVvsziLZWgHg0vVJy");
		
		Transaction id = processor.charge("tok_14kR79CxEv9dhAFuk1wudmBu", 5000, CurrencyDAO.USD, "emailId", "orderId");
		System.out.println(id.getTransactionId());
		System.out.println(id.isLive());
	}
}
