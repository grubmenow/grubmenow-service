package com.grubmenow.service.persist;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import lombok.extern.apachecommons.CommonsLog;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.Validate;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;
import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

import com.google.common.collect.ImmutableMap;
import com.grubmenow.service.datamodel.CustomerDAO;
import com.grubmenow.service.datamodel.CustomerOrderDAO;
import com.grubmenow.service.datamodel.CustomerOrderItemDAO;
import com.grubmenow.service.datamodel.FoodItemDAO;
import com.grubmenow.service.datamodel.FoodItemOfferDAO;
import com.grubmenow.service.datamodel.OrderFeedbackDAO;
import com.grubmenow.service.datamodel.ProviderDAO;
import com.grubmenow.service.datamodel.SearchSuggestionFeedbackDAO;
import com.grubmenow.service.persist.sql.SQLReader;

/**
 * Implementation of {@link PersistenceHandler} using Amazon AWS as a storage 
 */

@CommonsLog
public class PersistenceHandlerImpl implements PersistenceHandler {
	
	private static DateTimeFormatter printableDateTimeFormatter = DateTimeFormat.forPattern("yyyy-MM-dd");
	
	private SessionFactory sessionFactory;
	
	public PersistenceHandlerImpl() {
		Properties properties = new Properties();
	    properties.setProperty("hibernate.connection.driver_class", "com.mysql.jdbc.Driver");
	    properties.setProperty("hibernate.connection.url", "jdbc:mysql://grubmenow-db.ccaypnjyvorr.us-west-2.rds.amazonaws.com:3306/grubmenow_service_db");
	    properties.setProperty("hibernate.connection.username", "root");
	    properties.setProperty("hibernate.connection.password", "grubmen0w");
	    properties.setProperty("hibernate.show_sql", "com.mysql.jdbc.Driver");
	    properties.setProperty("hibernate.dialect", "org.hibernate.dialect.MySQLDialect");
	    
	    properties.setProperty("show_sql", "true");

	    properties.setProperty("hibernate.c3p0.min_size", "5");
	    properties.setProperty("hibernate.c3p0.max_size", "20");
	    properties.setProperty("hibernate.c3p0.timeout", "300");
	    properties.setProperty("hibernate.c3p0.max_statements", "50");
	    properties.setProperty("hibernate.c3p0.idle_test_period", "3000");
	    
	    Configuration configuration = new Configuration();
	    configuration.addAnnotatedClass(FoodItemDAO.class);
	    configuration.addAnnotatedClass(CustomerDAO.class);
	    configuration.addAnnotatedClass(CustomerOrderItemDAO.class);
	    configuration.addAnnotatedClass(CustomerOrderDAO.class);
	    configuration.addAnnotatedClass(FoodItemOfferDAO.class);
	    configuration.addAnnotatedClass(ProviderDAO.class);
	    configuration.addAnnotatedClass(OrderFeedbackDAO.class);
	    configuration.addAnnotatedClass(SearchSuggestionFeedbackDAO.class);
	    
	    configuration.setProperties(properties);
	    ServiceRegistry serviceRegistry = new StandardServiceRegistryBuilder().applySettings(
	            configuration.getProperties()).build();
	    
	    sessionFactory = configuration.buildSessionFactory(serviceRegistry);
	}
	
	
	public void shutDown() {
		sessionFactory.close();
	}
	
	
	@Override
	public Session getSession() {
		return sessionFactory.openSession();
	}

	@Override
	public List<String> getNeighbouringZipCodes(String zipCode, int numberOfMilesAround) {
		Map<String, String> tokens = ImmutableMap.of("zip_code", zipCode,
				"distance_in_miles", Integer.toString(numberOfMilesAround));
		
		String sql = SQLReader.loadSQL("/find_neighbouring_zip_codes.sql",
				tokens);
		
		return executeCustomSQL(sql);
	}
	
	@Override
	public List<FoodItemDAO> getAllFoodItem() {
		return getAll(FoodItemDAO.class, 1024);
	}

	
	@Override
	public List<FoodItemDAO> getAllAvailableFoodItemForZipCodes(List<String> zipCodes, DateTime forDate) {
		
		Map<String, String> tokens = ImmutableMap.of("zip_codes", getCommaSeperatedZipCodes(zipCodes), "offer_day",
				forDate.toString(printableDateTimeFormatter));
		
		String sql = SQLReader.loadSQL("/find_all_food_item_with_zip_code.sql", tokens);
		
		return executeCustomSQL(FoodItemDAO.class, sql);
	}
	
	@Override
	public List<FoodItemOfferDAO> getCurrentProviderOfferingWithinZipCodes(String foodItemId, List<String> zipCodes, DateTime forDate) {

		Map<String, String> tokens = ImmutableMap.of("zip_codes", getCommaSeperatedZipCodes(zipCodes), "offer_day",
				forDate.toString(printableDateTimeFormatter), "food_item_id", foodItemId);
		
		String sql = SQLReader.loadSQL("/find_all_provider_offering_within_zip_codes.sql", tokens);
		
		return executeCustomSQL(FoodItemOfferDAO.class, sql);
	}
	
	@Override
	public List<FoodItemOfferDAO> getCurrentProviderOffering(String foodItemId, DateTime forDate) {

		String sql = "SELECT  FIO.* "
					 + "FROM FOOD_ITEM_OFFER FIO, PROVIDER P, FOOD_ITEM FI "
					 + "WHERE FIO.OFFER_STATE = 'ACTIVE' "
					 + "AND P.PROVIDER_STATE = 'ACTIVE' AND FI.FOOD_ITEM_STATE = 'ACTIVE' AND FIO.AVAILABLE_QUANTITY > 0  "
					 + "AND date(FIO.OFFER_DAY) = :offer_day  AND FIO.FOOD_ITEM_ID = :food_item_id "
					 + "AND FIO.FOOD_ITEM_ID = FI.FOOD_ITEM_ID AND FIO.PROVIDER_ID = P.PROVIDER_ID" ;
		Session session = sessionFactory.openSession();
		try
		{
			Query query = session.createSQLQuery(sql)
					.addEntity(FoodItemOfferDAO.class)
					.setString("food_item_id", foodItemId)
					.setDate("offer_day", forDate.toDate());
			
			return query.list(); 
		}
		finally
		{
			session.close();
		}
		
	}
	
	private String getCommaSeperatedZipCodes(List<String> zipCodes) {
		List<String> zipCodesWithQuotes = new ArrayList<>(); 
		for(String zipCode: zipCodes) {
			zipCodesWithQuotes.add("'" + zipCode + "'");
		}

		return StringUtils.join(zipCodesWithQuotes, ", ");
	}
	
	@Override
	public void createFoodItem(FoodItemDAO foodItem) {
		log.info(String.format("Creating food item: %s ", foodItem));
		saveObject(foodItem);
	}
	

	@Override
	public void updateFoodItem(FoodItemDAO foodItem) {
		log.info(String.format("Updating food item: %s " , foodItem));
		updateObject(foodItem);		
	}


	@Override
	public FoodItemDAO getFoodItemById(String foodItemId) {

		log.info(String.format("Retrieving food item: %s " , foodItemId));

		return getObject(FoodItemDAO.class, foodItemId);
	}

	
	@Override
	public List<ProviderDAO> getAllProvider() {
		return getAll(ProviderDAO.class, 1024);
	}
	
	
	@Override
	public void createProvider(ProviderDAO provider) {
		log.info(String.format("Creating provider: %s " , provider));
		
		saveObject(provider);
	}

	@Override
	public void updateProvider(ProviderDAO provider) {
		log.info(String.format("Updating provider: %s " , provider));
		
		updateObject(provider);
	}

	@Override
	public void createSearchSuggestionFeedback(SearchSuggestionFeedbackDAO searchSuggestion)
	{
	    log.info(String.format("Adding search suggestion: %s", searchSuggestion));
	    saveObject(searchSuggestion);
	}

	
	@Override
	public ProviderDAO getProviderById(String providerId) {
		log.info(String.format("Retrieving provider: %s " , providerId));

		return getObject(ProviderDAO.class, providerId);
	}

	@Override
	public List<FoodItemOfferDAO> getAllOffersByProvider(String providerId, DateTime forDay) {
		String sql = "select * from FOOD_ITEM_OFFER where PROVIDER_ID=:providerId and OFFER_DAY=:offerDay ";
		Session session = sessionFactory.openSession();
		try {
			Query query = session.createSQLQuery(sql)
					.addEntity(FoodItemOfferDAO.class)
					.setString("providerId", providerId)
					.setDate("offerDay", forDay.toDate());

			return query.list();
		} finally {
			session.close();
		}
	}

	@Override
	public void createCustomer(CustomerDAO customer) {
		log.info(String.format("Creating customer: %s " , customer));
		
		saveObject(customer);
	}

	@Override
	public void updateCustomer(CustomerDAO customer) {
		log.info(String.format("Updating Customer: %s " , customer));
		
		updateObject(customer);
	}
	
	@Override
	public CustomerDAO getCustomerById(String customerId) {
		log.info(String.format("Retrieving Customer: %s " , customerId));
		
		return getObject(CustomerDAO.class, customerId);
	}

	@Override
	public void createFoodItemOffer(FoodItemOfferDAO foodItemOffer) {
		log.info(String.format("Creating food item offer: %s " , foodItemOffer));
		
		saveObject(foodItemOffer);
	}

	@Override
	public void udpateFoodItemOffer(FoodItemOfferDAO foodItemOffer) {
		log.info(String.format("Updating food item offer: %s " , foodItemOffer));
		
		updateObject(foodItemOffer);
	}
	
	@Override
	public FoodItemOfferDAO getFoodItemOfferById(String foodItemOfferId) {
		log.info(String.format("Retrieving food item offer: %s " , foodItemOfferId));
		
		return getObject(FoodItemOfferDAO.class, foodItemOfferId);
	}
	
	@Override
	public List<FoodItemOfferDAO> getAllFoodItemOffer() {
		return getAll(FoodItemOfferDAO.class, 1024);
	}


	@Override
	public void createCustomerOrder(CustomerOrderDAO customerOrder) {
		log.info(String.format("Creating order: %s " , customerOrder));
		
		saveObject(customerOrder);
	}
	
	@Override
	public void createCustomerOrderItem(CustomerOrderItemDAO customerOrderItem) {
		log.info(String.format("Creating order item: %s " , customerOrderItem));
		
		saveObject(customerOrderItem);
	}

	@Override
	public void updateCustomerOrder(CustomerOrderDAO customerOrder) {
		log.info(String.format("Updating order: %s " , customerOrder));
		
		updateObject(customerOrder);
	}
	
	@Override
	public void updateCustomerOrderItem(CustomerOrderItemDAO customerOrderItem) {
		log.info(String.format("Updating order item: %s " , customerOrderItem));
		
		updateObject(customerOrderItem);
	}

	
	@Override
	public CustomerOrderDAO getCustomerOrderById(String orderId) {
		log.info(String.format("Retrieving order: %s " , orderId));
		
		return getObject(CustomerOrderDAO.class, orderId);
	}
	
	
	@Override
	public CustomerOrderItemDAO getCustomerOrderItemById(String orderItemId) {
		log.info(String.format("Retrieving order Item: %s " , orderItemId));
		
		return getObject(CustomerOrderItemDAO.class, orderItemId);
	}
	
	@Override
	public List<CustomerOrderItemDAO> getCustomerOrderItemByOrderId(String orderId) {
		String sql = "select * from CUSTOMER_ORDER_ITEM where ORDER_ID=:orderId";
		Session session = sessionFactory.openSession();
		try {
			Query query = session.createSQLQuery(sql)
					.addEntity(CustomerOrderItemDAO.class)
					.setString("orderId", orderId);

			return query.list();
		} finally {
			session.close();
		}
	}
	
	public OrderFeedbackDAO getOrderFeedbackById(String orderId) {
		log.info(String.format("Retrieving order feedback: %s " , orderId));
		
		return getObject(OrderFeedbackDAO.class, orderId);
	}

	public void createOrderFeedback(OrderFeedbackDAO orderFeedbackDAO) {
		log.info(String.format("Creating order feedback: %s " , orderFeedbackDAO));
		
		saveObject(orderFeedbackDAO);
	}
	
	public void updateOrderFeedback(OrderFeedbackDAO orderFeedbackDAO) {
		log.info(String.format("Creating order feedback: %s " , orderFeedbackDAO));
		
		updateObject(orderFeedbackDAO);
	}
	
	private <T> List<T> executeCustomSQL(Class<T> clazz, String sql) {
		System.out.println(sql);
		
		Session session = sessionFactory.openSession();
		try {
			return session.createSQLQuery(sql).addEntity(clazz).list();		
		} catch (HibernateException e) {
			throw e;
		} finally {
			session.close();
		}	
	}
	
	private <T> List<T> executeCustomSQL(String sql) {
		Session session = sessionFactory.openSession();
		try {
			return session.createSQLQuery(sql).list();		
		} catch (HibernateException e) {
			throw e;
		} finally {
			session.close();
		}	
	}

	
	private <T> List<T> getAll(Class<T> clazz, int maxResult) {
		Session session = sessionFactory.openSession();
		
		try {
			return session.createCriteria(clazz).setMaxResults(maxResult).list();
		} catch (HibernateException e) {
			throw e;
		} finally {
			session.close();
		}	
	}
	
	private void saveObject(Object object) {
		Transaction transaction = null;
		Session session = sessionFactory.openSession();
		
		try {
			transaction = session.beginTransaction();
			session.save(object);
			transaction.commit();
		} catch (HibernateException e) {
			if (transaction != null)
				transaction.rollback();
			throw e;
		} finally {
			session.close();
		}	
	}
	
	
	private void updateObject(Object object) {
		Transaction transaction = null;
		Session session = sessionFactory.openSession();
		
		try {
			transaction = session.beginTransaction();
			session.update(object);
			transaction.commit();
		} catch (HibernateException e) {
			if (transaction != null)
				transaction.rollback();
			throw e;
		} finally {
			session.close();
		}
	}

//	private void updateObject(Session session, Object object) {
//		try {
//			session.update(object);
//		} catch (HibernateException e) {
//			throw e;
//		} finally {
//			session.close();
//		}	
//	}
	
	private <T> T getObject(Class<T> clazz, String id) {
		Validate.notNull(id);

		Session session = sessionFactory.openSession();
		
		try {
			return (T) session.get(clazz, id);
		} catch (HibernateException e) {
			throw e;
		} finally {
			session.close();
		}
	}
	
}
