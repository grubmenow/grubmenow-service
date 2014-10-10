package com.grubmenow.service.api;

import java.math.BigDecimal;

import org.apache.commons.lang3.StringUtils;

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
		if (amount == null || amount.getValue() == null || amount.getCurrency() == null) {
			throw new ValidationException(message);
		}

		if (amount.getValue().compareTo(BigDecimal.ZERO) <= 0) {
			throw new ValidationException(message);
		}
	}
}
