package com.grubmenow.service.dashboard.servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.grubmenow.service.datamodel.IDGenerator;
import com.grubmenow.service.datamodel.ProviderDAO;
import com.grubmenow.service.datamodel.ProviderState;
import com.grubmenow.service.persist.PersistenceFactory;

public class AddProviderDashboardServlet extends AbstractDashboadServlet {

	@Override  
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
		
		ProviderDAO providerDAO = populateProvider(new RequestReader(request));
		
		PersistenceFactory.getInstance().createProvider(providerDAO);
		
		response.getWriter().write("Provider created with id:" + providerDAO.getProviderId());
    	
    }
	
	private ProviderDAO populateProvider (RequestReader reader) {
		ProviderDAO providerDAO = new ProviderDAO();
		providerDAO.setProviderId(IDGenerator.generateProviderId());
		providerDAO.setProviderName(reader.read("providerName"));
		providerDAO.setProviderAddressStreetNumber(reader.read("providerAddressStreetNumber"));
		providerDAO.setProviderAddressStreet(reader.read("providerAddressStreet"));
		providerDAO.setProviderAddressApartmentNumber(reader.read("providerAddressApartmentNumber"));
		providerDAO.setProviderAddressZipCode(reader.read("providerAddressZipCode"));
		providerDAO.setProviderAddressState(reader.read("providerAddressState"));
		providerDAO.setProviderAddressCity(reader.read("providerAddressCity"));
		providerDAO.setProviderImageURL(reader.read("providerImageURL"));
		providerDAO.setIsOnlinePaymentAccepted(Boolean.valueOf(reader.read("isOnlinePaymentAccepted")));
		providerDAO.setIsCashOnDeliverPaymentAccepted(Boolean.valueOf(reader.read("isCashOnDeliverPaymentAccepted")));
		providerDAO.setIsCardOnDeliverPaymentAccepted(Boolean.valueOf(reader.read("isCardOnDeliverPaymentAccepted")));
		providerDAO.setProviderState(ProviderState.valueOf(reader.read("providerState")));
		
		return providerDAO;
	}
}