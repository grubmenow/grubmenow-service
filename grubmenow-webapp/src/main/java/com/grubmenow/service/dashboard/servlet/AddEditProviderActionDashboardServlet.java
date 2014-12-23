package com.grubmenow.service.dashboard.servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;

import com.grubmenow.service.dashboard.DashboardUtils;
import com.grubmenow.service.datamodel.IDGenerator;
import com.grubmenow.service.datamodel.ProviderDAO;
import com.grubmenow.service.datamodel.ProviderState;
import com.grubmenow.service.persist.PersistenceFactory;

public class AddEditProviderActionDashboardServlet extends AbstractDashboadServlet {

	@Override  
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

		RequestReader requestReader = new RequestReader(request);
		
		// check access
		if(!DashboardUtils.isDashboardAccess(request)) {
			throw new IllegalArgumentException("Invalid Code");
		}
		
		ProviderDAO providerDAO = updateProviderObject(requestReader);

		String message = "Saved. Provider Id:" + providerDAO.getProviderId();

		returnSuccessMessageAndRedirectToUrl(request, response, message, "provider");
    }
	
	private ProviderDAO updateProviderObject (RequestReader reader) {
		String providerId = reader.readOptional("providerId");
		
		boolean isNew = StringUtils.isBlank(providerId);
		
		ProviderDAO providerDAO = null;
		
		if(isNew) {
			providerDAO = new ProviderDAO(); 
			providerDAO.setProviderId(IDGenerator.generateProviderId());
			providerDAO.setTotalRatingPoints(0);
			providerDAO.setNumberOfRatings(0);
			providerDAO.setProviderState(ProviderState.ACTIVE);
		} else {
			providerDAO = PersistenceFactory.getInstance().getProviderById(providerId);
		}
		
		providerDAO.setProviderName(reader.read("providerName"));
		providerDAO.setProviderEmailId(reader.read("providerEmailId"));
		providerDAO.setProviderAddressStreetNumber(reader.read("providerAddressStreetNumber"));
		providerDAO.setProviderAddressStreet(reader.read("providerAddressStreet"));
		providerDAO.setProviderAddressApartmentNumber(reader.read("providerAddressApartmentNumber"));
		providerDAO.setProviderAddressZipCode(reader.read("providerAddressZipCode"));
		providerDAO.setProviderAddressCity(reader.read("providerAddressCity"));
		providerDAO.setProviderAddressState(reader.read("providerAddressState"));
		providerDAO.setProviderPhoneNumber(reader.read("providerPhoneNumber"));
		providerDAO.setIsOnlinePaymentAccepted(Boolean.valueOf(reader.read("isOnlinePaymentAccepted")));
		providerDAO.setIsCashOnDeliverPaymentAccepted(Boolean.valueOf(reader.read("isCashOnDeliverPaymentAccepted")));
		providerDAO.setIsCardOnDeliverPaymentAccepted(Boolean.valueOf(reader.read("isCardOnDeliverPaymentAccepted")));
		
		if(isNew) {
			PersistenceFactory.getInstance().createProvider(providerDAO);
		}else {
			PersistenceFactory.getInstance().updateProvider(providerDAO);
		}
		
		return providerDAO;
	}
	
	
	@Override
	public void init() throws ServletException {
		super.init();
		
		System.out.println("Init Done");
	}
}