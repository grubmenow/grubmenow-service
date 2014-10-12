package com.grubmenow.service.notif.email;

public class EmailSendException extends Exception {
	public EmailSendException(String message, Throwable ex) {
		super(message, ex);
	}
}
