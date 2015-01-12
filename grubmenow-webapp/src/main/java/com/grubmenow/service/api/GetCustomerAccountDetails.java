package com.grubmenow.service.api;

import lombok.extern.apachecommons.CommonsLog;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.grubmenow.service.auth.FacebookAuthentication;
import com.grubmenow.service.auth.FacebookCustomerInfo;
import com.grubmenow.service.datamodel.CustomerDAO;
import com.grubmenow.service.model.GetCustomerAccountDetailsRequest;
import com.grubmenow.service.model.GetCustomerAccountDetailsResponse;
import com.grubmenow.service.model.exception.ValidationException;
import com.grubmenow.service.persist.PersistenceFactory;

@RestController
@CommonsLog
public class GetCustomerAccountDetails extends AbstractRemoteService
{

    @Autowired
    private FacebookAuthentication facebookAuthentication;

	@RequestMapping(value = "/api/getCustomerAccountDetails", method = RequestMethod.POST, produces = "application/json; charset=utf-8")
	@ResponseBody
	public GetCustomerAccountDetailsResponse executeService(@RequestBody GetCustomerAccountDetailsRequest request) {
		validateInput(request);
		FacebookCustomerInfo fbCustomerInfo = validateAndfetchFacebookCustomerIdFromFb(request);
		return generateResponse(request, fbCustomerInfo);
	}

	private FacebookCustomerInfo validateAndfetchFacebookCustomerIdFromFb(GetCustomerAccountDetailsRequest request)
    {
	    try {
            return facebookAuthentication
                    .validateTokenAndFetchCustomerInfo(request.getWebsiteAuthenticationToken());
        } catch (Exception ex) {
            log.warn("Invalid fb authentication token", ex);
            throw new ValidationException("Invalid fb authentication token");
        }
    }

    private GetCustomerAccountDetailsResponse generateResponse(GetCustomerAccountDetailsRequest request, 
            FacebookCustomerInfo fbCustomerInfo)
    {
	    String fbUserId = fbCustomerInfo.getFacebookUserId();
	    try
	    {
	        CustomerDAO customerDAO = PersistenceFactory.getInstance().getCustomerById(fbUserId);
	        GetCustomerAccountDetailsResponse response = new GetCustomerAccountDetailsResponse();
	        response.setFirstName(customerDAO.getCustomerFirstName());
	        response.setLastName(customerDAO.getCustomerLastName());
	        response.setEmailId(customerDAO.getCustomerEmailId());
	        response.setPhoneNumber(customerDAO.getCustomerPhoneNumber());
	        return response;
	    }
	    catch (Exception ex)
	    {
	        log.warn("Customer id does not exist in database", ex);
	        throw new ValidationException("CustomerId ["+fbUserId+"] does not exist in db");
	    }
    }

	private void validateInput(GetCustomerAccountDetailsRequest request) {
		Validator.notBlank(request.getWebsiteAuthenticationToken(), "Auth token not present");
	}
}
