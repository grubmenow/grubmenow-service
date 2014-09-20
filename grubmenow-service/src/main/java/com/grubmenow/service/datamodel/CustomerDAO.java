package com.grubmenow.service.datamodel;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;
import lombok.ToString;

/**
 * Data Access Object Class to represent a Customer 
 */

@Entity
@Table(name = "CUSTOMER")
@ToString
public class CustomerDAO {

	@Id
	@Getter
	@Setter
	@NonNull
    @Column(name = "CUSTOMER_ID")
	private String customerId;
	
	@Getter
	@Setter
	@NonNull
    @Column(name = "CUSTOMER_NAME")
	private String customerName;
	
    @Getter
    @Setter
    @NonNull
    @Column(name = "CUSTOMER_STATE")
	private String customerState;
}
