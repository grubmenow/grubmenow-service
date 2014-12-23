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
public class OrderSuccessEmailRequest 
{
	private CustomerDAO consumer;
	private ProviderDAO provider;
	private CustomerOrderDAO customerOrder;
	private DateTime orderFulfillmentDate;
	private Amount orderAmountTotal;
	private List<EmailableOrderItemDetail> orderItems = new ArrayList<>();
	private String orderPickupStartTime = "7 PM";
	private String orderPickupEndTime = "9 PM";
}
