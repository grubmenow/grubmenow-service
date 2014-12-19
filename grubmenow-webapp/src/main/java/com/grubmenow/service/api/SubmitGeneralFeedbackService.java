package com.grubmenow.service.api;

import lombok.extern.apachecommons.CommonsLog;

import org.joda.time.DateTime;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.grubmenow.service.datamodel.GeneralFeedbackDAO;
import com.grubmenow.service.model.SubmitGeneralFeedbackRequest;
import com.grubmenow.service.model.SubmitGeneralFeedbackResponse;
import com.grubmenow.service.model.exception.ValidationException;
import com.grubmenow.service.persist.PersistenceFactory;

@RestController
@CommonsLog
public class SubmitGeneralFeedbackService extends AbstractRemoteService 
{
	@RequestMapping(value = "/api/submitGeneralFeedback", method = RequestMethod.POST, produces = "application/json; charset=utf-8")
	@ResponseBody
	public SubmitGeneralFeedbackResponse executeService(@RequestBody SubmitGeneralFeedbackRequest request) {
	    validate(request);
	    GeneralFeedbackDAO generalFeedbackDAO = new GeneralFeedbackDAO();
	    generalFeedbackDAO.setFeedbackType(request.getFeedbackType());
	    generalFeedbackDAO.setEmailId(request.getEmailId());
	    generalFeedbackDAO.setFeedbackProvidedTime(new DateTime());
	    generalFeedbackDAO.setMessage(request.getFeedbackMessage());
	    generalFeedbackDAO.setZipCode(request.getZipCode());
	    PersistenceFactory.getInstance().createGeneralFeedback(generalFeedbackDAO);
	    log.debug("Feedback request submitted. Type = ["+ request.getFeedbackType() 
	        +"], message = [" + request.getFeedbackMessage()+"]");
		return new SubmitGeneralFeedbackResponse();
	}

    private void validate(SubmitGeneralFeedbackRequest request)
    {
        if (request.getFeedbackType() == null)
        {
            throw new ValidationException("Feedback type must be present");
        }
    }
}
