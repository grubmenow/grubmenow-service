package com.grubmenow.service.datamodel;

import org.apache.commons.lang3.RandomStringUtils;

public class IDGenerator {

	public static String generateFoodItemId() {
		return RandomStringUtils.randomNumeric(4).toLowerCase();
	}
	
	public static String generateProviderId() {
		return RandomStringUtils.randomAlphabetic(8).toLowerCase();
	}
	
	public static String generateFoodItemOfferId() {
		return RandomStringUtils.randomAlphabetic(10).toLowerCase();
	}

}
