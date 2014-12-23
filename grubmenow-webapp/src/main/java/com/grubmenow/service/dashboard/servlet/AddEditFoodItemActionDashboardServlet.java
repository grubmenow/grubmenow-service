package com.grubmenow.service.dashboard.servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;

import com.grubmenow.service.datamodel.FoodItemDAO;
import com.grubmenow.service.datamodel.FoodItemState;
import com.grubmenow.service.datamodel.IDGenerator;
import com.grubmenow.service.persist.PersistenceFactory;

public class AddEditFoodItemActionDashboardServlet extends AbstractDashboadServlet {

	@Override  
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

		RequestReader requestReader = new RequestReader(request);
		
		// check access
		if(!StringUtils.equals(requestReader.read("code"), "hampton")) {
			throw new IllegalArgumentException("Invalid Code: " + requestReader.read("code"));
		}
		
		FoodItemDAO foodItemDAO = updateFoodObject(requestReader);

		String message = "Saved. Food Item Id:" + foodItemDAO.getFoodItemId();

		returnSuccessMessageAndRedirectToUrl(request, response, message, "fooditem");
    }
	
	private FoodItemDAO updateFoodObject (RequestReader reader) {
		
		String foodItemId = reader.readOptional("foodItemId");
		
		boolean isNew = StringUtils.isBlank(foodItemId);
		
		FoodItemDAO foodItemDAO = null;
		
		if(isNew) {
			foodItemDAO = new FoodItemDAO(); 
			foodItemDAO.setFoodItemId(IDGenerator.generateFoodItemId());
			foodItemDAO.setFoodItemDescription("");
			foodItemDAO.setFoodItemDescriptionTags("");
			foodItemDAO.setFoodItemState(FoodItemState.ACTIVE);
		} else {
			foodItemDAO = PersistenceFactory.getInstance().getFoodItemById(foodItemId);
		}
		
		foodItemDAO.setFoodItemName(reader.read("foodItemName"));
		foodItemDAO.setFoodItemImageUrl(reader.read("foodItemImageUrl"));
		
		if(isNew) {
			PersistenceFactory.getInstance().createFoodItem(foodItemDAO);
		}else {
			PersistenceFactory.getInstance().updateFoodItem(foodItemDAO);
		}
		
		return foodItemDAO;
	}
	
	
	@Override
	public void init() throws ServletException {
		super.init();
		
		System.out.println("Init Done");
	}
}