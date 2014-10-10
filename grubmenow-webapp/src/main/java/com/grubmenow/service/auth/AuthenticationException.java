package com.grubmenow.service.auth;

public class AuthenticationException extends Exception {
	
	public AuthenticationException(String msg, Throwable t) {
		super(msg, t);
	}
	
	public AuthenticationException(Throwable t) {
		super(t);
	}
	
	public AuthenticationException(String msg) {
		super(msg);
	}

}
