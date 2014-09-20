package com.grubmenow.service.datamodel;

import org.apache.commons.lang3.RandomStringUtils;

public class IDGenerator {

	public static String generateFoodItemId() {
		return RandomStringUtils.randomAlphabetic(8).toLowerCase();
	}
}
