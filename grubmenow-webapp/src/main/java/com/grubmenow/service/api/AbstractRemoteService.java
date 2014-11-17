package com.grubmenow.service.api;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import com.grubmenow.service.model.exception.ValidationException;

public abstract class AbstractRemoteService  {

	@ExceptionHandler(ValidationException.class)
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	  public @ResponseBody
	   Map<String,Object> handleValidationException(ValidationException ex,
	                                           HttpServletRequest request, HttpServletResponse resp) {
	     HashMap<String, Object> result = new HashMap<>();
	     result.put("error", true);
	     result.put("error_message", ex.getMessage());
	     if (ex.getInternalErrorMessage() != null)
	     {
	         result.put("internal_error_message", ex.getInternalErrorMessage());
	     }
	     return result;
	  }
	
}
