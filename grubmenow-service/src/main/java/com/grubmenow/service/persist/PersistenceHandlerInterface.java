package com.grubmenow.service.persist;

import com.grubmenow.service.datamodel.CustomerDAO;
import com.grubmenow.service.datamodel.FoodItemDAO;
import com.grubmenow.service.datamodel.FoodItemOfferDAO;
import com.grubmenow.service.datamodel.OrderDAO;
import com.grubmenow.service.datamodel.ProviderDAO;


/**
 * Persistence handler to handle storage of various objects in the system
 *
 */
public interface PersistenceHandlerInterface {

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

	/**
	 * Create Order
	 */
	public void createOrder(OrderDAO order);
	
	/**
	 * Create Order
	 */
	public void updateOrder(OrderDAO order);
	
	/**
	 * Get Order By Id
	 */
	public OrderDAO getOrderbyId(String orderId);

}
