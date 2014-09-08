package com.grubmenow.service.persist;

import java.util.List;

import lombok.extern.apachecommons.CommonsLog;

import org.apache.commons.lang3.Validate;

import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClient;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBQueryExpression;
import com.grubmenow.service.datamodel.CustomerDAO;
import com.grubmenow.service.datamodel.FoodItemDAO;
import com.grubmenow.service.datamodel.FoodItemOfferDAO;
import com.grubmenow.service.datamodel.OrderDAO;
import com.grubmenow.service.datamodel.ProviderDAO;

/**
 * Implementation of {@link PersistenceHandlerInterface} using Amazon AWS as a storage 
 */

@CommonsLog
public class PersistenceHandlerImpl implements PersistenceHandlerInterface {
	
	private DynamoDBMapper dynamoDBMapper;
	
	public PersistenceHandlerImpl(AmazonDynamoDBClient awsDynamoDBClient) {
		this.dynamoDBMapper = new DynamoDBMapper(awsDynamoDBClient);
	}

	@Override
	public void createFoodItem(FoodItemDAO foodItem) {
		log.info(String.format("Creating food item: %s " , foodItem));
		
		dynamoDBMapper.save(foodItem);
	}
	
	@Override
	public void updateFoodItem(FoodItemDAO foodItem) {
		log.info(String.format("Updating food item: %s " , foodItem));
		
		dynamoDBMapper.save(foodItem);
	}


	@Override
	public FoodItemDAO getFoodItemById(String foodItemId) {

		log.info(String.format("Retrieving food item: %s " , foodItemId));
		
		Validate.notNull(foodItemId);

		FoodItemDAO foodItem = new FoodItemDAO();
		foodItem.setFoodItemId(foodItemId);

		DynamoDBQueryExpression<FoodItemDAO> queryExpression = new DynamoDBQueryExpression<FoodItemDAO>()
				.withHashKeyValues(foodItem);

		List<FoodItemDAO> foodItems = dynamoDBMapper.query(FoodItemDAO.class,
				queryExpression);
		Validate.isTrue(foodItems.size() == 1,
				"Unable to find FoodItem: %s", foodItemId);

		return foodItems.get(0);
	}

	@Override
	public void createProvider(ProviderDAO provider) {
		log.info(String.format("Creating provider: %s " , provider));
		
		dynamoDBMapper.save(provider);
	}

	@Override
	public void updateProvider(ProviderDAO provider) {
		log.info(String.format("Ppdating provider: %s " , provider));
		
		dynamoDBMapper.save(provider);
	}

	
	@Override
	public ProviderDAO getProviderById(String providerId) {
		log.info(String.format("Retrieving provider: %s " , providerId));
		
		Validate.notNull(providerId);

		ProviderDAO provider = new ProviderDAO();
		provider.setProviderId(providerId);

		DynamoDBQueryExpression<ProviderDAO> queryExpression = new DynamoDBQueryExpression<ProviderDAO>()
				.withHashKeyValues(provider);

		List<ProviderDAO> providers = dynamoDBMapper.query(ProviderDAO.class,
				queryExpression);
		Validate.isTrue(providers.size() == 1,
				"Unable to find provider: %s", providerId);

		return providers.get(0);
	}

	@Override
	public void createCustomer(CustomerDAO customer) {
		log.info(String.format("Creating customer: %s " , customer));
		
		dynamoDBMapper.save(customer);
	}

	@Override
	public void updateCustomer(CustomerDAO customer) {
		log.info(String.format("Updating Customer: %s " , customer));
		
		dynamoDBMapper.save(customer);
	}
	
	@Override
	public CustomerDAO getCustomerById(String customerId) {
		log.info(String.format("Retrieving Customer: %s " , customerId));
		
		Validate.notNull(customerId);

		CustomerDAO customer = new CustomerDAO();
		customer.setCustomerId(customerId);

		DynamoDBQueryExpression<CustomerDAO> queryExpression = new DynamoDBQueryExpression<CustomerDAO>()
				.withHashKeyValues(customer);

		List<CustomerDAO> customers = dynamoDBMapper.query(CustomerDAO.class,
				queryExpression);
		Validate.isTrue(customers.size() == 1,
				"Unable to find Customer: %s", customerId);

		return customers.get(0);
	}

	@Override
	public void createFoodItemOffer(FoodItemOfferDAO foodItemOffer) {
		log.info(String.format("Creating food item offer: %s " , foodItemOffer));
		
		dynamoDBMapper.save(foodItemOffer);
	}

	@Override
	public void udpateFoodItemOffer(FoodItemOfferDAO foodItemOffer) {
		log.info(String.format("Updating food item offer: %s " , foodItemOffer));
		
		dynamoDBMapper.save(foodItemOffer);
	}
	
	@Override
	public FoodItemOfferDAO getFoodItemOfferbyId(String foodItemOfferId) {
		log.info(String.format("Retrieving food item offer: %s " , foodItemOfferId));
		
		Validate.notNull(foodItemOfferId);

		FoodItemOfferDAO foodItemOffer = new FoodItemOfferDAO();
		foodItemOffer.setFoodItemId(foodItemOfferId);

		DynamoDBQueryExpression<FoodItemOfferDAO> queryExpression = new DynamoDBQueryExpression<FoodItemOfferDAO>()
				.withHashKeyValues(foodItemOffer);

		List<FoodItemOfferDAO> foodItemOffers = dynamoDBMapper.query(FoodItemOfferDAO.class,
				queryExpression);
		Validate.isTrue(foodItemOffers.size() == 1,
				"Unable to find food item offer: %s", foodItemOfferId);

		return foodItemOffers.get(0);
	}

	@Override
	public void createOrder(OrderDAO order) {
		log.info(String.format("Creating order: %s " , order));
		
		dynamoDBMapper.save(order);
	}

	@Override
	public void updateOrder(OrderDAO order) {
		log.info(String.format("Updating order: %s " , order));
		
		dynamoDBMapper.save(order);
	}

	
	@Override
	public OrderDAO getOrderbyId(String orderId) {
		log.info(String.format("Retrieving order: %s " , orderId));
		
		Validate.notNull(orderId);

		OrderDAO order = new OrderDAO();
		order.setOrderId(orderId);

		DynamoDBQueryExpression<OrderDAO> queryExpression = new DynamoDBQueryExpression<OrderDAO>()
				.withHashKeyValues(order);

		List<OrderDAO> orders = dynamoDBMapper.query(OrderDAO.class,
				queryExpression);
		Validate.isTrue(orders.size() == 1,
				"Unable to find order: %s", orderId);

		return orders.get(0);
	}
	
}
