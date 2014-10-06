package com.grubmenow.service.datamodel;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;
import lombok.NonNull;
import lombok.ToString;

/**
 * Data Access Object Class to represent a Customer 
 */

@Entity
@Table(name = "CUSTOMER")
@ToString
@Data
public class CustomerDAO {

	@Id
	@NonNull
    @Column(name = "CUSTOMER_ID")
	private String customerId;
	
	@NonNull
    @Column(name = "CUSTOMER_NAME")
	private String customerName;
	
    @NonNull
    @Column(name = "CUSTOMER_STATE")
	private String customerState;
}
