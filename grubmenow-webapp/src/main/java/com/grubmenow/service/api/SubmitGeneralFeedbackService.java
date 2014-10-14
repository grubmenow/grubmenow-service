package com.grubmenow.service.api;

import lombok.extern.apachecommons.CommonsLog;

import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.grubmenow.service.model.SubmitGeneralFeedbackRequest;
import com.grubmenow.service.model.SubmitGeneralFeedbackResponse;

@RestController
@CommonsLog
public class SubmitGeneralFeedbackService extends AbstractRemoteService 
{
	@RequestMapping(value = "/api/submitGeneralFeedback", method = RequestMethod.POST, produces = "application/json; charset=utf-8")
	@ResponseBody
	public SubmitGeneralFeedbackResponse executeService(@RequestBody SubmitGeneralFeedbackRequest request) {
		// TODO: kapila: 13th Oct 2014. Complete this API
		log.debug("Feedback request submitted: " + request.getFeedbackMessage());
		return new SubmitGeneralFeedbackResponse();
	}
}
