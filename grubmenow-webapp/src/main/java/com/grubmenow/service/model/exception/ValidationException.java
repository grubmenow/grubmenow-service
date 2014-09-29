package com.grubmenow.service.model.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value=HttpStatus.BAD_REQUEST, reason="Validation error from API")  // 400
public class ValidationException extends RuntimeException {
	public ValidationException(String message) {
		super(message);
	}
}
