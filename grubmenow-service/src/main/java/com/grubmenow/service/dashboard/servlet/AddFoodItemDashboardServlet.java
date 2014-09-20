package com.grubmenow.service.dashboard.servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.grubmenow.service.datamodel.FoodItemDAO;
import com.grubmenow.service.datamodel.FoodItemState;
import com.grubmenow.service.datamodel.IDGenerator;
import com.grubmenow.service.persist.PersistenceFactory;

public class AddFoodItemDashboardServlet extends AbstractDashboadServlet {

	@Override  
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
		
		FoodItemDAO foodItemDAO = populateFoodObject(new RequestReader(request));
		
		PersistenceFactory.getInstance().createFoodItem(foodItemDAO);
		
		response.getWriter().write("Food Item created with id:" + foodItemDAO.getFoodItemId());
    	
    }
	
	private FoodItemDAO populateFoodObject (RequestReader reader) {
		FoodItemDAO foodItemDAO = new FoodItemDAO(); 
		foodItemDAO.setFoodItemId(IDGenerator.generateFoodItemId());
		foodItemDAO.setFoodItemName(reader.read("foodItemName"));
		foodItemDAO.setFoodItemImageUrl(reader.read("foodItemImageUrl"));
		foodItemDAO.setFoodItemDescription(reader.read("foodItemDescription"));
		foodItemDAO.setFoodItemDescriptionTags(reader.read("foodItemDescriptionTags"));
		foodItemDAO.setFoodItemState(FoodItemState.valueOf(reader.read("foodItemState")));
		
		return foodItemDAO;
	}
	
	
	@Override
	public void init() throws ServletException {
		super.init();
		
		System.out.println("Init Done");
	}
}