package com.grubmenow.service.api;

import lombok.extern.apachecommons.CommonsLog;

import org.apache.commons.lang3.StringUtils;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.grubmenow.service.auth.FacebookCustomerInfo;
import com.grubmenow.service.auth.ServiceHandler;
import com.grubmenow.service.datamodel.CustomerOrderDAO;
import com.grubmenow.service.datamodel.OrderFeedbackDAO;
import com.grubmenow.service.model.GetCustomerOrderFeedbackRequest;
import com.grubmenow.service.model.GetCustomerOrderFeedbackResponse;
import com.grubmenow.service.model.exception.ValidationException;
import com.grubmenow.service.persist.PersistenceFactory;

@RestController
@CommonsLog
public class GetCustomerOrderFeedbackService extends AbstractRemoteService {

	@RequestMapping(value = "/api/getCustomerOrderFeedback", method = RequestMethod.POST, produces = "application/json; charset=utf-8")
	@ResponseBody
	public GetCustomerOrderFeedbackResponse executeService(@RequestBody GetCustomerOrderFeedbackRequest request) {
		validateInput(request);
		validateCustomersOrder(request);
		
		return generateResponse(request);
		
	}
	
	private GetCustomerOrderFeedbackResponse generateResponse(GetCustomerOrderFeedbackRequest request) {
		GetCustomerOrderFeedbackResponse response = new GetCustomerOrderFeedbackResponse();
		
		try{
			OrderFeedbackDAO orderFeedbackDAO = PersistenceFactory.getInstance().getOrderFeedbackById(request.getOrderId());
			if (orderFeedbackDAO != null)
			{
			    response.setFeedback(orderFeedbackDAO.getFeedback());
			    response.setRating(orderFeedbackDAO.getRating());
			}
			else
			{
			    response.setFeedback("");
	            response.setRating(-1);
			}
		} catch (Exception ex) {
		    log.error("Error while loading prev feedback, returning no feedback", ex);
		    response.setFeedback("");
			response.setRating(-1);
		}

		return response;
	}

	private void validateInput(GetCustomerOrderFeedbackRequest request) {
		Validator.notBlank(request.getOrderId(), "Invalid Order id");
		Validator.notBlank(request.getWebsiteAuthenticationToken(), "Auth token not present");
	}

	private CustomerOrderDAO validateCustomersOrder(GetCustomerOrderFeedbackRequest request) {
		FacebookCustomerInfo customerInfo = null;
		try {
			customerInfo = ServiceHandler.getInstance().getFacebookAuthentication()
					.validateTokenAndFetchCustomerInfo(request.getWebsiteAuthenticationToken());
		} catch (Exception e) {
			throw new ValidationException("Invalid fb authentication token");
		}
		
		// get order
		CustomerOrderDAO customerOrderDAO = PersistenceFactory.getInstance().getCustomerOrderById(request.getOrderId());
		
		Validator.isTrue(StringUtils.equals(customerOrderDAO.getCustomerId(), customerInfo.getFacebookUserId()), "Unable to confirm the order for this customer");
		
		return customerOrderDAO;
	}


}
