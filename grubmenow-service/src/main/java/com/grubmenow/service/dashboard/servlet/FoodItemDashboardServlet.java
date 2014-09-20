package com.grubmenow.service.dashboard.servlet;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.grubmenow.service.datamodel.FoodItemDAO;
import com.grubmenow.service.persist.PersistenceFactory;

public class FoodItemDashboardServlet extends AbstractDashboadServlet {

	@Override  
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
		
		List<FoodItemDAO> foodItems = PersistenceFactory.getInstance().getAllFoodItem();
		request.setAttribute("foodItems", foodItems);
		
    	forwardTo(request, response, "/foodItem.jsp");
    }
	
	
	@Override
	public void init() throws ServletException {
		super.init();
		
		System.out.println("Init Done");
	}
}