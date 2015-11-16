package com.grubmenow.service.api;

import lombok.extern.apachecommons.CommonsLog;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.http.HttpStatus;

import com.grubmenow.service.auth.FacebookAuthentication;
import com.grubmenow.service.auth.FacebookCustomerInfo;
import com.grubmenow.service.model.FoodAlert;
import com.grubmenow.service.model.exception.ServiceFaultException;
import com.grubmenow.service.model.exception.ValidationException;
import com.grubmenow.service.persist.PersistenceHandler;

@Controller
@RequestMapping(value = "/api/foodAlert")
@CommonsLog
public class FoodAlertService extends AbstractRemoteService
{
    @Autowired
    private PersistenceHandler persistenceHandler;

    @Autowired
    private FacebookAuthentication facebookAuthentication;

    @RequestMapping (value = "/{id}", method = RequestMethod.GET)
    @ResponseBody
    public FoodAlert get(String id)
    {
        return null;
    }

    @RequestMapping (method = RequestMethod.POST, produces = "application/json; charset=utf-8")
    @ResponseStatus (HttpStatus.CREATED)
    @ResponseBody
    public FoodAlert create(@RequestBody FoodAlert request)
    {
        validateCreateRequest(request);

        FacebookCustomerInfo fbCustomerInfo = null;
        try {
            fbCustomerInfo = facebookAuthentication.validateTokenAndFetchCustomerInfo(request
                    .getWebsiteAuthenticationToken());
        } catch (Exception e) {
            throw new ValidationException("Invalid fb authentication token");
        }


        try
        {
        }
        catch (ValidationException ex)
        {
            throw ex;
        }
        catch (Exception e)
        {
            log.error("Exception while doing image manipulation. ", e);
            throw new ServiceFaultException("Internal service error while doing image manipulation.", e.getMessage());
        }
    }

    private FoodItemDAO createFoodItemInDatabase(String foodItemName, String foodItemImageUrl, String foodItemDescription)
    {
        FoodItemDAO foodItemDAO = new FoodItemDAO();
        foodItemDAO.setFoodItemId(IDGenerator.generateFoodItemId());
        foodItemDAO.setFoodItemName(foodItemName);
        if (foodItemDescription != null)
        {
            foodItemDAO.setFoodItemDescription(foodItemDescription);
        }
        else
        {
            foodItemDAO.setFoodItemDescription("");
        }
        foodItemDAO.setFoodItemImageUrl(foodItemImageUrl);
        foodItemDAO.setFoodItemState(FoodItemState.ACTIVE);
        persistenceHandler.createFoodItem(foodItemDAO);
        return foodItemDAO;
    }

    private void validateCreateRequest(FoodAlert foodAlert)
    {
        Validator.notNull(foodAlert, "foodItem cannot be null");
        Validator.isNull(foodAlert.getFoodAlertType(), "foodAlertType must be present");
        Validator.notBlank(foodAlert.getWebsiteAuthenticationToken(), "Webiste authentication token must be present");
    }
}
