package com.grubmenow.service.dashboard.servlet;


import java.util.Arrays;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import lombok.AllArgsConstructor;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.Validate;


@AllArgsConstructor
public class RequestReader {

	private HttpServletRequest request;
	
	public String read(String parameter) {
		String value = request.getParameter(parameter);
		
		Validate.notNull(value, "Unable to find value for key: %s", parameter);
		
		return value;
	}
	
	
	public String readOptional(String parameter) {
		return request.getParameter(parameter);
	}
	
	public List<String> readCommaSeperated(String parameter) {
		String value = request.getParameter(parameter);
		
		Validate.notNull(value, "Unable to find value for key: %s", parameter);
		
		return Arrays.asList(StringUtils.split(value, ','));
	}
	
}
