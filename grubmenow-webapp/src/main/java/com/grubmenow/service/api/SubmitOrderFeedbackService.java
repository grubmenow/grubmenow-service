package com.grubmenow.service.api;

import java.math.BigDecimal;
import java.math.BigInteger;

import org.apache.commons.lang3.StringUtils;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.exception.ConstraintViolationException;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.grubmenow.service.auth.FacebookCustomerInfo;
import com.grubmenow.service.auth.ServiceHandler;
import com.grubmenow.service.datamodel.CustomerOrderDAO;
import com.grubmenow.service.datamodel.OrderFeedbackDAO;
import com.grubmenow.service.datamodel.ProviderDAO;
import com.grubmenow.service.model.SubmitOrderFeedbackRequest;
import com.grubmenow.service.model.SubmitOrderFeedbackResponse;
import com.grubmenow.service.model.exception.ValidationException;
import com.grubmenow.service.persist.PersistenceFactory;

@RestController
public class SubmitOrderFeedbackService extends AbstractRemoteService {

	@RequestMapping(value = "/api/submitOrderFeedback", method = RequestMethod.POST, produces = "application/json; charset=utf-8")
	@ResponseBody
	public SubmitOrderFeedbackResponse executeService(@RequestBody SubmitOrderFeedbackRequest request) {
		validateInput(request);
		CustomerOrderDAO customerOrderDAO = validateCustomersOrder(request);

		
		OrderFeedbackDAO orderFeedbackDAO = new OrderFeedbackDAO();
		orderFeedbackDAO.setOrderId(request.getOrderId());
		orderFeedbackDAO.setCustomerId(customerOrderDAO.getCustomerId());
		orderFeedbackDAO.setProviderId(customerOrderDAO.getProviderId());
		orderFeedbackDAO.setFeedback(request.getFeedback());
		orderFeedbackDAO.setRating(request.getRating());
		try{
			PersistenceFactory.getInstance().createOrderFeedback(orderFeedbackDAO);
		}catch (ConstraintViolationException e) {
			PersistenceFactory.getInstance().updateOrderFeedback(orderFeedbackDAO);
		}
		
		// update average customer rating
		updateProviderRating(customerOrderDAO.getProviderId());
		
		SubmitOrderFeedbackResponse response = new SubmitOrderFeedbackResponse();
		response.setMessage("Feedback posted successfully");
		
		return response;
		
	}
	
	private void updateProviderRating(String providerId) {
		
		BigDecimal totalRatingPoints = null;
		BigInteger numberOfRatings = null;
		
		String sql = "select sum(rating), count(1) as AVG_RATING from ORDER_FEEDBACK where PROVIDER_ID = :provider_id"; 
		Session session = PersistenceFactory.getInstance().getSession();
		try {
			Query query = session.createSQLQuery(sql)
					.setString("provider_id", providerId);
				
			Object[] values = (Object[]) query.list().get(0);
			
			Validator.notNull(values, "Error updating rating");
			totalRatingPoints = (BigDecimal) values[0];
			numberOfRatings = (BigInteger) values[1];
		} finally {
			session.close();
		}
		
		ProviderDAO providerDAO = PersistenceFactory.getInstance().getProviderById(providerId);
		providerDAO.setTotalRatingPoints(totalRatingPoints.intValueExact());
		providerDAO.setNumberOfRatings(numberOfRatings.intValue());
		PersistenceFactory.getInstance().updateProvider(providerDAO);
	}
	
	private void validateInput(SubmitOrderFeedbackRequest request) {
		Validator.notBlank(request.getOrderId(), "Invalid Order id");
		Validator.notBlank(request.getFeedback(), "Feedback message not present");
		Validator.isTrue(request.getRating() > 0 && request.getRating() <=5 , "Rating should be in a range of 1 to 5");
		Validator.notBlank(request.getWebsiteAuthenticationToken(), "Auth token not present");
	}

	private CustomerOrderDAO validateCustomersOrder(SubmitOrderFeedbackRequest request) {
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
