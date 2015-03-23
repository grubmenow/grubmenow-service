package com.grubmenow.service;

import java.util.Arrays;

import org.joda.time.DateTime;

import com.grubmenow.service.datamodel.CustomerDAO;
import com.grubmenow.service.datamodel.CustomerOrderDAO;
import com.grubmenow.service.datamodel.ProviderDAO;
import com.grubmenow.service.model.Amount;
import com.grubmenow.service.model.Currency;
import com.grubmenow.service.notif.email.OrderSuccessEmailRequest;
import com.grubmenow.service.notif.email.EmailSender;
import com.grubmenow.service.notif.email.EmailableOrderItemDetail;

public class EmailSenderTest {
	public static void main(String[] args) throws Exception {
		EmailSender emailSender = new EmailSender("AKIAJ32C6BIGSSD7YL5A", "3+BkyXtniT+waUOMqUgR43oIj+IRiVpS37NKx69D", false);
		CustomerDAO consumer = new CustomerDAO();
		consumer.setCustomerFirstName("Vikas");
		consumer.setCustomerEmailId("mustvicky@gmail.com");

		CustomerOrderDAO customerOrderDAO = new CustomerOrderDAO();
		customerOrderDAO.setOrderAmount(999);
		
		Amount orderTotalPrice = new Amount();
		orderTotalPrice.setCurrency(Currency.USD);
		orderTotalPrice.setValue(999);
		
		ProviderDAO provider = new ProviderDAO();
		provider.setProviderAddressApartmentNumber("APT QQ-202");
		provider.setProviderAddressStreetNumber("4631");
		provider.setProviderAddressStreet("148th AVE NE");
		provider.setProviderAddressCity("Bellevue");
		provider.setProviderAddressState("WA");
		provider.setProviderAddressZipCode("98007");
		provider.setProviderName("Kavya's home kitchen");
		
		EmailableOrderItemDetail orderItemDetail = new EmailableOrderItemDetail();
		orderItemDetail.setFoodItemName("Masala Dosa");
		orderItemDetail.setFoodItemDescription("Awesome masala dosa");
		orderItemDetail.setFoodItemTotalPrice(orderTotalPrice);
		orderItemDetail.setFoodItemQuantity(1);

//		OrderSuccessEmailRequest request = 
//			OrderSuccessEmailRequest.builder()
//				.consumer(consumer)
//				.customerOrder(customerOrderDAO)
//				.orderAmountTotal(orderTotalPrice)
//				.provider(provider)
//				.orderItems(Arrays.asList(orderItemDetail))
//				.orderFulfillmentDate(new DateTime().plusDays(1))
//				.build();
//		
//		emailSender.sendConsumerOrderSuccessEmail(request);
	}
}
