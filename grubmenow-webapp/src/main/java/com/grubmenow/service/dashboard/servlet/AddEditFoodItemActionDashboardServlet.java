package com.grubmenow.service.dashboard.servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;

import com.grubmenow.service.dashboard.DashboardUtils;
import com.grubmenow.service.datamodel.FoodItemDAO;
import com.grubmenow.service.datamodel.FoodItemState;
import com.grubmenow.service.datamodel.IDGenerator;
import com.grubmenow.service.persist.PersistenceFactory;
import com.grubmenow.service.persist.PersistenceHandler;

public class AddEditFoodItemActionDashboardServlet extends AbstractDashboadServlet {

	@Override  
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

	    PersistenceHandler persistenceHandler = PersistenceFactory.getInstance();
	    
		RequestReader requestReader = new RequestReader(request);
		
		// check access
		if(!DashboardUtils.isDashboardAccess(request)) {
			throw new IllegalArgumentException("Invalid Code");
		}
		
		FoodItemDAO foodItemDAO = updateFoodObject(requestReader, persistenceHandler);

		String message = "Saved. Food Item Id:" + foodItemDAO.getFoodItemId();

		returnSuccessMessageAndRedirectToUrl(request, response, message, "fooditem");
    }
	
	private FoodItemDAO updateFoodObject (RequestReader reader, PersistenceHandler persistenceHandler) {
		
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
			foodItemDAO = persistenceHandler.getFoodItemById(foodItemId);
		}
		
		foodItemDAO.setFoodItemName(reader.read("foodItemName"));
		foodItemDAO.setFoodItemImageUrl(reader.read("foodItemImageUrl"));
		
		if(isNew) {
			persistenceHandler.createFoodItem(foodItemDAO);
		}else {
			persistenceHandler.updateFoodItem(foodItemDAO);
		}
		
		return foodItemDAO;
	}
	
	
	@Override
	public void init() throws ServletException {
		super.init();
		
		System.out.println("Init Done");
	}
}