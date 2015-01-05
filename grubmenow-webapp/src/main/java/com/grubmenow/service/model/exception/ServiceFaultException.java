package com.grubmenow.service.model.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value=HttpStatus.INTERNAL_SERVER_ERROR, reason="Internal service error")  // 500
public class ServiceFaultException extends RuntimeException {
    /**
     * The internal error message which should not be displayed on the website.
     * This is for better debug-ability, and not for the end customer.
     */
    private String internalErrorMessage = null;
	public ServiceFaultException(String message) {
		super(message);
	}

	public ServiceFaultException(String message, String internalErrorMessage) {
        super(message);
        this.internalErrorMessage = internalErrorMessage;
    }

	public String getInternalErrorMessage()
    {
        return internalErrorMessage;
    }
}
