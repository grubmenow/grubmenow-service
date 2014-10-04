package com.grubmenow.service.pay;

import java.util.HashMap;
import java.util.Map;

import com.grubmenow.service.datamodel.CurrencyDAO;
import com.stripe.Stripe;
import com.stripe.model.Charge;

/**
 * Handles Stripe integration
 */
public class StripePaymentProcessor {
	private final String stripSecretKey;
	public StripePaymentProcessor(String stripeSecretKey) {
		this.stripSecretKey = stripeSecretKey;
	}
	
	/**
	 * Charges the token with the specified amount and desc. 
	 * @param stripeCreditCardToken
	 * @param amountInCents
	 * @param currency
	 * @param chargeDesc
	 * @return credit
	 * @throws PaymentProcessorException
	 */
	public Transaction charge(String stripeCreditCardToken, int amountInCents, CurrencyDAO currency, String chargeDesc) throws PaymentProcessorException
	{
		Stripe.apiKey = stripSecretKey;
		Stripe.setVerifySSL(true);
		Map<String, Object> chargeParams = new HashMap<>(); 
		chargeParams.put("amount", amountInCents); 
		chargeParams.put("currency", currency.name().toLowerCase()); 
		chargeParams.put("card", stripeCreditCardToken); 
		chargeParams.put("description", chargeDesc);
//		chargeParams.put("metadata", new HashMap());
		
		try {
			Charge charge =  Charge.create(chargeParams);
			Transaction transaction = new Transaction(charge.getId(), charge.getLivemode());
			return transaction;
		} catch (Exception ex) {
			throw new PaymentProcessorException("Stripe payment failed.", ex);
		}
	}
	
	public void refund(Transaction transaction)
	{
		throw new UnsupportedOperationException();
	}
}
