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
		StripePaymentProcessor processor = new StripePaymentProcessor("sk_test_BQokikJOvBiI2HlWgH4olfQ2");
		
		Transaction id = processor.charge("tok_14jdvR2eZvKYlo2CttYz9knT", 1000, CurrencyDAO.USD, "emailId");
		System.out.println(id.getTransactionId());
		System.out.println(id.isLive());
	}
}
