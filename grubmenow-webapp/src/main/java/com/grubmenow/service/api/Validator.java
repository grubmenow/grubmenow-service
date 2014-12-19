package com.grubmenow.service.api;

import org.apache.commons.lang3.StringUtils;
import org.joda.time.DateTime;

import com.grubmenow.service.model.Amount;
import com.grubmenow.service.model.exception.ValidationException;

public class Validator {

	public static void isTrue(boolean value, String message) {
		if(!value) {
			throw new ValidationException(message);
		}
	}

	public static void notNull(Object value, String message) {
		if(value == null) {
			throw new ValidationException(message);
		}
	}

	public static void notBlank(String value, String message) {
		if(StringUtils.isBlank(value)) {
			throw new ValidationException(message);
		}
	}
	
	public static void notPositiveAmount(Amount amount, String message) {
		if (amount == null || amount.getValue() <= 0 || amount.getCurrency() == null) {
			throw new ValidationException(message);
		}
	}
}
