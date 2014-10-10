package com.grubmenow.service.persist;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import lombok.extern.apachecommons.CommonsLog;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.Validate;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;

import com.grubmenow.service.datamodel.CustomerDAO;
import com.grubmenow.service.datamodel.CustomerOrderDAO;
import com.grubmenow.service.datamodel.CustomerOrderItemDAO;
import com.grubmenow.service.datamodel.FoodItemDAO;
import com.grubmenow.service.datamodel.FoodItemOfferDAO;
import com.grubmenow.service.datamodel.ProviderDAO;
import com.grubmenow.service.persist.sql.SQLReader;

/**
 * Implementation of {@link PersistenceHandler} using Amazon AWS as a storage 
 */

@CommonsLog
public class PersistenceHandlerImpl implements PersistenceHandler {
	
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
	    
	    configuration.setProperties(properties);
	    ServiceRegistry serviceRegistry = new StandardServiceRegistryBuilder().applySettings(
	            configuration.getProperties()).build();
	    
	    sessionFactory = configuration.buildSessionFactory(serviceRegistry);
	}
	
	
	public void shutDown() {
		sessionFactory.close();
	}
	
	public SessionHandler getSessionHandler() {
		return new SessionHandler(sessionFactory.openSession());
	}

	@Override
	public List<String> getNeighbouringZipCodes(String zipCode, int numberOfMilesAround) {
		return new ArrayList<>();
	}
	
	@Override
	public List<FoodItemDAO> getAllFoodItem() {
		return getAll(FoodItemDAO.class, 1024);
	}

	
	@Override
	public List<FoodItemDAO> getAllAvailableFoodItemForZipCodes(List<String> zipCodes) {

		List<String> zipCodesWithQuotes = new ArrayList<>(); 
		for(String zipCode: zipCodes) {
			zipCodesWithQuotes.add("'" + zipCode + "'");
		}
		
		Map<String, String> tokens = new HashMap<>();
		tokens.put("zip_codes", StringUtils.join(zipCodes, ", "));
		
		String sql = SQLReader.loadSQL("find_all_food_item_with_zip_code.sql", tokens);
		
		return executeCustomSQL(FoodItemDAO.class, sql);
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
		log.info(String.format("Ppdating provider: %s " , provider));
		
		updateObject(provider);
	}

	
	@Override
	public ProviderDAO getProviderById(String providerId) {
		log.info(String.format("Retrieving provider: %s " , providerId));

		return getObject(ProviderDAO.class, providerId);
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
	public FoodItemOfferDAO getFoodItemOfferbyId(String foodItemOfferId) {
		log.info(String.format("Retrieving food item offer: %s " , foodItemOfferId));
		
		return getObject(FoodItemOfferDAO.class, foodItemOfferId);
	}
	
	@Override
	public List<FoodItemOfferDAO> getAllFoodItemOffer() {
		return getAll(FoodItemOfferDAO.class, 1024);
	}


	@Override
	public void createCustomerOrder(SessionHandler sessionHandler, CustomerOrderDAO customerOrder) {
		log.info(String.format("Creating order: %s " , customerOrder));
		
		sessionHandler.getSession().save(customerOrder);
	}
	
	@Override
	public void createCustomerOrderItem(SessionHandler sessionHandler, CustomerOrderItemDAO customerOrderItem) {
		log.info(String.format("Creating order item: %s " , customerOrderItem));
		
		sessionHandler.getSession().save(customerOrderItem);
	}

	@Override
	public void updateCustomerOrder(SessionHandler sessionHandler, CustomerOrderDAO customerOrder) {
		log.info(String.format("Updating order: %s " , customerOrder));
		
		updateObject(sessionHandler.getSession(), customerOrder);
	}
	
	@Override
	public void updateCustomerOrderItem(SessionHandler sessionHandler, CustomerOrderItemDAO customerOrderItem) {
		log.info(String.format("Updating order item: %s " , customerOrderItem));
		
		updateObject(sessionHandler.getSession(), customerOrderItem);
	}

	
	@Override
	public CustomerOrderDAO getCustomerOrderbyId(String orderId) {
		log.info(String.format("Retrieving order: %s " , orderId));
		
		return getObject(CustomerOrderDAO.class, orderId);
	}
	
	
	@Override
	public CustomerOrderItemDAO getCustomerOrderItembyId(String orderItemId) {
		log.info(String.format("Retrieving order Item: %s " , orderItemId));
		
		return getObject(CustomerOrderItemDAO.class, orderItemId);
	}
	
	private <T> List<T> executeCustomSQL(Class<T> clazz, String sql) {
		Session session = sessionFactory.openSession();
		try {
			return session.createSQLQuery(sql).addEntity(clazz).list();		
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
	
//	private void saveObject(Session session, Object object) {
//		try {
//			session.save(object);
//		} catch (HibernateException e) {
//			throw e;
//		} finally {
//			session.close();
//		}	
//	}

	
	private void updateObject(Object object) {
		Transaction transaction = null;
		Session session = sessionFactory.openSession();
		
		try {
			transaction = session.beginTransaction();
			updateObject(session, object);
			transaction.commit();
		} catch (HibernateException e) {
			if (transaction != null)
				transaction.rollback();
			throw e;
		} finally {
			session.close();
		}
	}

	private void updateObject(Session session, Object object) {
		try {
			session.update(object);
		} catch (HibernateException e) {
			throw e;
		} finally {
			session.close();
		}	
	}
	
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
