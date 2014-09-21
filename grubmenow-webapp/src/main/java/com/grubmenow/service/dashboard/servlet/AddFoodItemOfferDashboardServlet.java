package com.grubmenow.service.dashboard.servlet;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.Calendar;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.grubmenow.service.datamodel.FoodItemOfferDAO;
import com.grubmenow.service.datamodel.IDGenerator;
import com.grubmenow.service.datamodel.OfferState;
import com.grubmenow.service.persist.PersistenceFactory;

public class AddFoodItemOfferDashboardServlet extends AbstractDashboadServlet {

	@Override
	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {

		FoodItemOfferDAO foodItemOfferDAO = populateProvider(new RequestReader(request));

		PersistenceFactory.getInstance().createFoodItemOffer(foodItemOfferDAO);

		response.getWriter().write("Food Item Offer created with id:" + foodItemOfferDAO.getFoodItemId());

	}

	private FoodItemOfferDAO populateProvider(RequestReader reader) {
		FoodItemOfferDAO foodItemOffer = new FoodItemOfferDAO();
		foodItemOffer.setFoodItemOfferId(IDGenerator.generateFoodItemOfferId());
		foodItemOffer.setFoodItemId(reader.read("foodItemId"));
		foodItemOffer.setProviderId(reader.read("providerId"));
		foodItemOffer.setOfferDescription(reader.read("offerDescription"));
		foodItemOffer.setOfferDescriptionTags(reader
				.read("offerDescriptionTags"));
		foodItemOffer.setOfferUnitPrice(new BigDecimal(reader.read("offerUnitPrice")));
		foodItemOffer.setOfferCurrency(reader.read("offerCurrency"));
		foodItemOffer.setOfferQuantity(new Integer(reader.read("offerQuantity")));
		foodItemOffer.setOfferDay(Calendar.getInstance());
		foodItemOffer.setOfferMealType(reader.read("offerMealType"));
		foodItemOffer.setIsFoodDeliveryOptionAvailable(Boolean.valueOf(reader
				.read("isFoodDeliveryOptionAvailable")));
		foodItemOffer.setIsFoodPickUpOptionAvailable(Boolean.valueOf(reader
				.read("isFoodPickUpOptionAvailable")));
		foodItemOffer.setOfferState(OfferState.valueOf(reader
				.read("offerState")));

		return foodItemOffer;
	}
}