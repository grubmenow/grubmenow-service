package com.grubmenow.service.persist;

import java.util.List;

import org.hibernate.Session;
import org.joda.time.DateTime;

import com.grubmenow.service.datamodel.CustomerDAO;
import com.grubmenow.service.datamodel.CustomerOrderDAO;
import com.grubmenow.service.datamodel.CustomerOrderItemDAO;
import com.grubmenow.service.datamodel.FoodItemDAO;
import com.grubmenow.service.datamodel.FoodItemOfferDAO;
import com.grubmenow.service.datamodel.GeneralFeedbackDAO;
import com.grubmenow.service.datamodel.InvitationRequestDAO;
import com.grubmenow.service.datamodel.OrderFeedbackDAO;
import com.grubmenow.service.datamodel.ProviderDAO;
import com.grubmenow.service.datamodel.SearchSuggestionFeedbackDAO;


/**
 * Persistence handler to handle storage of various objects in the system
 *
 */
public interface PersistenceHandler {

	
	/**
	 * Returns a Hibernate Session  
	 */
	public Session getSession();
	
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
	public List<FoodItemDAO> getAllAvailableFoodItemForZipCodes(List<String> zipCodes, DateTime forDate);
	
	
	/**
	 * Get All Food Item Offer within ZipCodes
	 */
	public List<FoodItemOfferDAO> getCurrentProviderOfferingWithinZipCodes(String foodItemId, List<String> zipCodes, DateTime forDate);
	
	/**
	 * Get All Food Item Offer for the specified food Item irrespective of location
	 */
	public List<FoodItemOfferDAO> getCurrentProviderOffering(String foodItemId, DateTime forDate);
	
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
	 * Get all the offers by a provider;
	 */
	public List<FoodItemOfferDAO> getAllOffersByProvider(String providerId, DateTime forDate);
	
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
	public FoodItemOfferDAO getFoodItemOfferById(String offerId);

	
	public List<FoodItemOfferDAO> getAllFoodItemOffer();
	/**
	 * Create Order
	 */
	public void createCustomerOrder(CustomerOrderDAO order);
	
	/**
	 * Create Order
	 */
	public void createCustomerOrderItem(CustomerOrderItemDAO orderItem);
	
	/**
	 * Update Order
	 */
	public void updateCustomerOrder(CustomerOrderDAO order);
	
	/**
	 * Update Order Item
	 */
	public void updateCustomerOrderItem(CustomerOrderItemDAO orderItem);
	
	/**
	 * Get Order By Id
	 */
	public CustomerOrderDAO getCustomerOrderById(String orderId);
	
	/**
	 * Get Order Item By Id
	 */
	public CustomerOrderItemDAO getCustomerOrderItemById(String orderId);

	/**
	 * Get Order Item By Id
	 */
	public List<CustomerOrderItemDAO> getCustomerOrderItemByOrderId(String orderId);
	
	/**
	 * get Order Feedback
	 */
	public OrderFeedbackDAO getOrderFeedbackById(String orderId);

	
	/**
	 * Create Order Feedback
	 */
	public void createOrderFeedback(OrderFeedbackDAO orderFeedbackDAO);

	/**
	 * Create Order Feedback
	 */
	public void updateOrderFeedback(OrderFeedbackDAO orderFeedbackDAO);

	/**
	 * Create Search suggestion feedback object in database
	 */
    void createSearchSuggestionFeedback(SearchSuggestionFeedbackDAO searchSuggestion);

    /**
     * Create general feedback object in database 
     */
    public void createGeneralFeedback(GeneralFeedbackDAO generalFeedbackDAO);
    /**
     * Invitation request object creation
     */
    void createInvitationRequest(InvitationRequestDAO dao);
}
