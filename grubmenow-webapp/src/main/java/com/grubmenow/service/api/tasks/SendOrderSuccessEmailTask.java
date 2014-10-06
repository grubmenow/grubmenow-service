package com.grubmenow.service.api.tasks;

import com.grubmenow.service.datamodel.CustomerDAO;
import com.grubmenow.service.datamodel.CustomerOrderDAO;

public class SendOrderSuccessEmailTask extends Task {

	private CustomerDAO customerDAO;
	private CustomerOrderDAO customerOrderDAO;
	
	public SendOrderSuccessEmailTask(CustomerDAO customerDAO, CustomerOrderDAO customerOrderDAO) {
		this.customerDAO = customerDAO;
		this.customerOrderDAO = customerOrderDAO;
	}
	
	@Override
	public void execute() {
		
	}

}
