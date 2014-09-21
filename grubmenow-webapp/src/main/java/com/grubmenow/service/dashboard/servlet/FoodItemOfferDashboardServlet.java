package com.grubmenow.service.dashboard.servlet;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.grubmenow.service.datamodel.FoodItemOfferDAO;
import com.grubmenow.service.persist.PersistenceFactory;

public class FoodItemOfferDashboardServlet extends AbstractDashboadServlet {

	@Override  
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
		
		List<FoodItemOfferDAO> foodItemOffers = PersistenceFactory.getInstance().getAllFoodItemOffer();
		request.setAttribute("foodItemOffers", foodItemOffers);
		
    	forwardTo(request, response, "/dashboard/foodItemOffer.jsp");
    }
}