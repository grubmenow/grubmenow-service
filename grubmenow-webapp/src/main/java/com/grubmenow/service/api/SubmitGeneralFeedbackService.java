package com.grubmenow.service.api;

import lombok.extern.apachecommons.CommonsLog;

import org.joda.time.DateTime;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.grubmenow.service.auth.ServiceHandler;
import com.grubmenow.service.datamodel.GeneralFeedbackDAO;
import com.grubmenow.service.model.SubmitGeneralFeedbackRequest;
import com.grubmenow.service.model.SubmitGeneralFeedbackResponse;
import com.grubmenow.service.model.exception.ValidationException;
import com.grubmenow.service.notif.email.EmailSendException;
import com.grubmenow.service.notif.email.GeneralFeedbackEmailRequest;
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
	    sendEmail(generalFeedbackDAO);
	    
		return new SubmitGeneralFeedbackResponse();
	}

    private void sendEmail(GeneralFeedbackDAO generalFeedbackDAO)
    {
        GeneralFeedbackEmailRequest generalFeedbackEmailRequest = GeneralFeedbackEmailRequest.builder()
                .feedbackType(generalFeedbackDAO.getFeedbackType())
                .customerEmailId(generalFeedbackDAO.getEmailId())
                .feedbackProvidedTime(generalFeedbackDAO.getFeedbackProvidedTime())
                .message(generalFeedbackDAO.getMessage())
                .zipCode(generalFeedbackDAO.getZipCode())
                .build();
                
        try
        {
            ServiceHandler.getInstance().getEmailSender().sendGeneralFeedbackEmail(generalFeedbackEmailRequest);
        } catch (EmailSendException ex)
        {
            log.error("Failed sending best-effort feedback email", ex);
        }
    }

    private void validate(SubmitGeneralFeedbackRequest request)
    {
        if (request.getFeedbackType() == null)
        {
            throw new ValidationException("Feedback type must be present");
        }
    }
}
