package com.grubmenow.service.auth;

import lombok.Getter;
import lombok.SneakyThrows;

import com.grubmenow.service.notif.email.EmailSender;
import com.grubmenow.service.pay.StripePaymentProcessor;

public class ServiceHandler {
	
	private static ServiceHandler instance = null;   

	@Getter
	private FacebookAuthentication facebookAuthentication = null;

	@Getter
	private StripePaymentProcessor stripeProcessor = null;
	
	@Getter
	private EmailSender emailSender = null;

	@SneakyThrows
	public ServiceHandler() {
		try {
			facebookAuthentication = new FacebookAuthentication("107439809640", "156c4b8c6ef3355ab1aa8a36c8072a3d");

			stripeProcessor = new StripePaymentProcessor("sk_test_jXSLNqhMVvsziLZWgHg0vVJy");

			emailSender = new EmailSender("AKIAJ32C6BIGSSD7YL5A", "3+BkyXtniT+waUOMqUgR43oIj+IRiVpS37NKx69D");
		} catch (Exception e) {
			e.printStackTrace();
		}

	}
	
	
	public static ServiceHandler getInstance() {
		if(instance != null) {
			return instance;
		}
		
		synchronized (ServiceHandler.class) {
			if(instance == null) {
				instance = new ServiceHandler();
			}
		}
		
		return instance;
	}
}
