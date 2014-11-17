package com.grubmenow.service.model.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value=HttpStatus.BAD_REQUEST, reason="Validation error from API")  // 400
public class ValidationException extends RuntimeException {
    /**
     * The internal error message which should not be displayed on the website.
     * This is for better debug-ability, and not for the end customer.
     */
    private String internalErrorMessage = null;
	public ValidationException(String message) {
		super(message);
	}

	public ValidationException(String message, String internalErrorMessage) {
        super(message);
        this.internalErrorMessage = internalErrorMessage;
    }

	public String getInternalErrorMessage()
    {
        return internalErrorMessage;
    }
}
