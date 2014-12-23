package com.grubmenow.service.dashboard;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;

public class DashboardUtils {

	private static final String ACCESS_CODE = "hampton";
	
	private static boolean isValidAccessCode(String accessCode) {
		return StringUtils.equals(accessCode, ACCESS_CODE);
	}
	
	public static boolean isDashboardAccess(HttpServletRequest request) { 
		Cookie[] cookies = request.getCookies();
		if (cookies != null) {
		    for (Cookie cookie : cookies) {
		        if(StringUtils.equals(cookie.getName(), "accesscode")) {
		        	return isValidAccessCode(cookie.getValue());
		        }
		    }
		}
		
		return false;
	}
}
