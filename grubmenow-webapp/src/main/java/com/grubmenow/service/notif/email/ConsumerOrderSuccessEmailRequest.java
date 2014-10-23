package com.grubmenow.service.notif.email;

import java.util.ArrayList;
import java.util.List;

import lombok.Data;
import lombok.experimental.Builder;

import org.joda.time.DateTime;

import com.grubmenow.service.datamodel.CustomerDAO;
import com.grubmenow.service.datamodel.CustomerOrderDAO;
import com.grubmenow.service.datamodel.ProviderDAO;
import com.grubmenow.service.model.Amount;

/**
 * The request object for sending the customer order success email. 
 * This uses the Builder pattern to build the object. 
 */
@Data
@Builder
public class ConsumerOrderSuccessEmailRequest 
{
//	private String orderId;
	private CustomerDAO consumer;
	private ProviderDAO provider;
	private CustomerOrderDAO customerOrder;
	private DateTime orderFulfillmentDate;
	private List<EmailableOrderItemDetail> orderItems = new ArrayList<>();
//	private Amount orderTotalPrice;
	private String orderPickupStartTime = "7 PM";
	private String orderPickupEndTime = "9 PM";
	
//	private ConsumerOrderSuccessEmailRequest(){};
	
//	public static class Builder
//	{
//		private final ConsumerOrderSuccessEmailRequest request = new ConsumerOrderSuccessEmailRequest();
//		
//		public Builder orderId(String orderId)
//		{
//			request.setOrderId(orderId);
//			return this;
//		}
//		
//		public Builder consumer(CustomerDAO consumer)
//		{
//			request.setConsumer(consumer);
//			return this;
//		}
//		
//		public Builder provider(ProviderDAO provider)
//		{
//			request.setProvider(provider);
//			return this;
//		}
//		
//		public Builder orderFulfillmentDate(DateTime orderFulfillmentDate)
//		{
//			request.setOrderFulfillmentDate(orderFulfillmentDate);
//			return this;
//		}
//		
//		public Builder addOrderItem(EmailableOrderItemDetail orderItemDetail)
//		{
//			request.orderItems.add(orderItemDetail);
//			return this;
//		}
//		
//		public Builder orderTotalPrice(Amount orderTotalPrice)
//		{
//			request.setOrderTotalPrice(orderTotalPrice);
//			return this;
//		}
//		
//		public Builder orderPickupStartTime(String orderPickupStartTime)
//		{
//			request.setOrderPickupStartTime(orderPickupStartTime);
//			return this;
//		}
//		
//		public Builder orderPickupEndTime(String orderPickupEndTime)
//		{
//			request.setOrderPickupEndTime(orderPickupEndTime);
//			return this;
//		}
//		
//		public ConsumerOrderSuccessEmailRequest build() {
//			Validate.notNull(request.consumer, "Consumer cannot be null");
//			Validate.notNull(request.orderFulfillmentDate, "Order fulfilment date cannot be null");
//			Validate.notNull(request.orderId, "Order Id cannot be null");
//			Validate.notEmpty(request.orderItems, "Order items cannot be empty");
//			Validate.notNull(request.orderTotalPrice, "Order total price cannot be null");
//			return request;
//		}
//	}
}
