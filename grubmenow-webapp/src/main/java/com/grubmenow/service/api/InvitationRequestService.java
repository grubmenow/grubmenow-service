package com.grubmenow.service.api;

import lombok.extern.apachecommons.CommonsLog;

import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.grubmenow.service.datamodel.InvitationRequestDAO;
import com.grubmenow.service.model.InvitationRequest;
import com.grubmenow.service.model.InvitationResponse;
import com.grubmenow.service.persist.PersistenceHandler;

@RestController
@CommonsLog
public class InvitationRequestService extends AbstractRemoteService 
{
    @Autowired
    PersistenceHandler persistenceHandler;
	@RequestMapping(value = "/api/invitationRequest", method = RequestMethod.POST, produces = "application/json; charset=utf-8")
	@ResponseBody
	public InvitationResponse executeService(@RequestBody InvitationRequest request) {
	    validateRequest(request);
		log.debug("Invitation Request for zip code: " + request.getZipCode());

	    InvitationRequestDAO dao = new InvitationRequestDAO();
	    dao.setInvitationRequestTime(new DateTime());
	    dao.setEmailId(request.getEmailId());
	    dao.setZipCode(request.getZipCode());
	    persistenceHandler.createInvitationRequest(dao);

	    InvitationResponse response = new InvitationResponse();
		return response;
	}

    private void validateRequest(InvitationRequest request)
    {
    }
}
