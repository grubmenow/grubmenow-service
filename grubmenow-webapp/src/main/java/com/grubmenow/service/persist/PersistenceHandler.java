package com.grubmenow.service.persist;

import java.util.List;

import com.grubmenow.service.datamodel.CustomerDAO;
import com.grubmenow.service.datamodel.CustomerOrderDAO;
import com.grubmenow.service.datamodel.CustomerOrderItemDAO;
import com.grubmenow.service.datamodel.FoodItemDAO;
import com.grubmenow.service.datamodel.FoodItemOfferDAO;
import com.grubmenow.service.datamodel.ProviderDAO;


/**
 * Persistence handler to handle storage of various objects in the system
 *
 */
public interface PersistenceHandler {

	/**
	 * Get All Food Items
	 */
	public List<String> getNeighbouringZipCodes(String zipCode, int numberOfMilesAround);

	
	/**
	 * Get All Food Items
	 */
	public List<FoodItemDAO> getAllFoodItem();

	
	/**
	 * Get All Food Items for ZipCodes
	 */
	public List<FoodItemDAO> getAllAvailableFoodItemForZipCodes(List<String> zipCodes);
	
	
	/**
	 * Get All Food Item Offer within ZipCodes
	 */
	public List<FoodItemOfferDAO> getCurrentProviderOfferingWithinZipCodes(String foodItemId, List<String> zipCodes);
	
	/**
	 * Create Food Item
	 */
	public void createFoodItem(FoodItemDAO foodItem);

	/**
	 * Update Food Item
	 */
	public void updateFoodItem(FoodItemDAO foodItem);
	
	/**
	 * Get Food Item by Id
	 */
	public FoodItemDAO getFoodItemById(String foodItemId);

	/**
	 * Get All Provider
	 */
	public List<ProviderDAO> getAllProvider();
	
	/**
	 * Create Provider
	 */
	public void createProvider(ProviderDAO provider);

	/**
	 * Update Provider
	 */
	public void updateProvider(ProviderDAO provider);

	/**
	 * Get Provider By Id
	 */
	public ProviderDAO getProviderById(String providerId);
	
	/**
	 * Create Customer
	 */
	public void createCustomer(CustomerDAO customer);

	/**
	 *  Update Customer
	 */
	public void updateCustomer(CustomerDAO customer);
	
	/**
	 * Get Customer By Id
	 */
	public CustomerDAO getCustomerById(String customerId);
	
	/**
	 * Create Food Item Offer 
	 */
	public void createFoodItemOffer(FoodItemOfferDAO foodItemOffer);

	/**
	 * Create Food Item Offer 
	 */
	public void udpateFoodItemOffer(FoodItemOfferDAO foodItemOffer);
	
	/**
	 * Get Food Item Offer By id
	 */
	public FoodItemOfferDAO getFoodItemOfferbyId(String offerId);

	
	public List<FoodItemOfferDAO> getAllFoodItemOffer();
	/**
	 * Create Order
	 */
	public void createCustomerOrder(SessionHandler sessionHandler, CustomerOrderDAO order);
	
	/**
	 * Create Order
	 */
	public void createCustomerOrderItem(SessionHandler sessionHandler, CustomerOrderItemDAO orderItem);
	
	/**
	 * Update Order
	 */
	public void updateCustomerOrder(SessionHandler sessionHandler, CustomerOrderDAO order);
	
	/**
	 * Update Order Item
	 */
	public void updateCustomerOrderItem(SessionHandler sessionHandler, CustomerOrderItemDAO orderItem);
	
	/**
	 * Get Order By Id
	 */
	public CustomerOrderDAO getCustomerOrderbyId(String orderId);
	
	/**
	 * Get Order By Id
	 */
	public CustomerOrderItemDAO getCustomerOrderItembyId(String orderId);


}
