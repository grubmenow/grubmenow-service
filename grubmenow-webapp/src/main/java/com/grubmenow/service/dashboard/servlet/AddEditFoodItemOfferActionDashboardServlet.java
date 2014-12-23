package com.grubmenow.service.dashboard.servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.joda.time.DateTime;

import com.grubmenow.service.datamodel.FoodItemOfferDAO;
import com.grubmenow.service.datamodel.IDGenerator;
import com.grubmenow.service.datamodel.OfferMealType;
import com.grubmenow.service.datamodel.OfferState;
import com.grubmenow.service.model.Currency;
import com.grubmenow.service.persist.PersistenceFactory;

public class AddEditFoodItemOfferActionDashboardServlet extends AbstractDashboadServlet {

	@Override  
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

		RequestReader requestReader = new RequestReader(request);
		
		// check access
		if(!StringUtils.equals(requestReader.read("code"), "hampton")) {
			throw new IllegalArgumentException("Invalid Code: " + requestReader.read("code"));
		}
		
		FoodItemOfferDAO foodItemOfferDAO = updateFoodItemOfferObject(requestReader);

		String message = "Saved. Food Item Offer Id:" + foodItemOfferDAO.getFoodItemOfferId();

		returnSuccessMessageAndRedirectToUrl(request, response, message, "fooditemoffer");
    }
	
	private FoodItemOfferDAO updateFoodItemOfferObject (RequestReader reader) {
		String foodItemOfferId = reader.readOptional("foodItemOfferId");
		
		boolean isNew = StringUtils.isBlank(foodItemOfferId);
		
		FoodItemOfferDAO foodItemOfferDAO = null;
		
		if(isNew) {
			foodItemOfferDAO = new FoodItemOfferDAO(); 
			foodItemOfferDAO.setFoodItemOfferId(IDGenerator.generateFoodItemOfferId());
			
			foodItemOfferDAO.setOfferCurrency(Currency.USD);
			foodItemOfferDAO.setOfferMealType(OfferMealType.DINNER);
			foodItemOfferDAO.setIsFoodDeliveryOptionAvailable(false);
			foodItemOfferDAO.setIsFoodPickUpOptionAvailable(true);
			foodItemOfferDAO.setOfferState(OfferState.ACTIVE);
			foodItemOfferDAO.setAvailableQuantity(reader.readInt("offerQuantity"));
		} else {
			foodItemOfferDAO = PersistenceFactory.getInstance().getFoodItemOfferById(foodItemOfferId);
		}
		
		foodItemOfferDAO.setProviderId(reader.read("providerId"));
		foodItemOfferDAO.setFoodItemId(reader.read("foodItemId"));
		foodItemOfferDAO.setOfferDescription(reader.read("offerDescription"));
		foodItemOfferDAO.setOfferDescriptionTags(reader.read("offerDescriptionTags"));
		foodItemOfferDAO.setOfferUnitPrice(reader.readInt("offerUnitPrice"));
		foodItemOfferDAO.setOfferQuantity(reader.readInt("offerQuantity"));
		foodItemOfferDAO.setOfferDay(new DateTime(reader.readInt("offerDayYYYY"), reader.readInt("offerDayMM"), reader.readInt("offerDayDD"), 0, 0));
		
		if(isNew) {
			PersistenceFactory.getInstance().createFoodItemOffer(foodItemOfferDAO);
		}else {
			PersistenceFactory.getInstance().udpateFoodItemOffer(foodItemOfferDAO);
		}
		
		return foodItemOfferDAO;
	}
	
	
	@Override
	public void init() throws ServletException {
		super.init();
		
		System.out.println("Init Done");
	}
}