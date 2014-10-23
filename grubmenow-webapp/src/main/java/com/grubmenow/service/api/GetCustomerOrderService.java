package com.grubmenow.service.api;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.grubmenow.service.auth.FacebookCustomerInfo;
import com.grubmenow.service.auth.ServiceHandler;
import com.grubmenow.service.datamodel.CustomerOrderDAO;
import com.grubmenow.service.datamodel.CustomerOrderItemDAO;
import com.grubmenow.service.datamodel.FoodItemDAO;
import com.grubmenow.service.datamodel.ObjectPopulator;
import com.grubmenow.service.model.CustomerOrderItem;
import com.grubmenow.service.model.GetCustomerOrderRequest;
import com.grubmenow.service.model.GetCustomerOrderResponse;
import com.grubmenow.service.model.exception.ValidationException;
import com.grubmenow.service.persist.PersistenceFactory;

@RestController
public class GetCustomerOrderService extends AbstractRemoteService {

	@RequestMapping(value = "/api/getCustomerOrder", method = RequestMethod.POST, produces = "application/json; charset=utf-8")
	@ResponseBody
	public GetCustomerOrderResponse executeService(@RequestBody GetCustomerOrderRequest request) {
		validateInput(request);
		validateCustomersOrder(request);
		
		return generateResponse(request);
		
	}
	
	private GetCustomerOrderResponse generateResponse(GetCustomerOrderRequest request) {
		GetCustomerOrderResponse response = new GetCustomerOrderResponse();

		CustomerOrderDAO customerOrderDAO = PersistenceFactory.getInstance().getCustomerOrderById(request.getOrderId());
		response.setCustomerOrder(ObjectPopulator.toCustomerOrder(customerOrderDAO));
		
		List<CustomerOrderItemDAO> customerOrderItemDAOs = PersistenceFactory.getInstance().getCustomerOrderItemByOrderId(request.getOrderId());
		
		List<CustomerOrderItem> customerOrderItems = new ArrayList<>();
		for(CustomerOrderItemDAO customerOrderItemDAO: customerOrderItemDAOs) {
			
			CustomerOrderItem customerOrderItem = ObjectPopulator.toCustomerOrderItem(customerOrderItemDAO);
			FoodItemDAO foodItemDAO = PersistenceFactory.getInstance().getFoodItemById(customerOrderItemDAO.getFoodItemId());
					
			customerOrderItem.setFoodItem(ObjectPopulator.toFoodItem(foodItemDAO));
			customerOrderItems.add(customerOrderItem);
		}
		
		response.setCustomerOrderItems(customerOrderItems);
		
		return response;
	}

	private void validateInput(GetCustomerOrderRequest request) {
		Validator.notBlank(request.getOrderId(), "Invalid Order id");
		Validator.notBlank(request.getWebsiteAuthenticationToken(), "Auth token not present");
	}

	private CustomerOrderDAO validateCustomersOrder(GetCustomerOrderRequest request) {
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