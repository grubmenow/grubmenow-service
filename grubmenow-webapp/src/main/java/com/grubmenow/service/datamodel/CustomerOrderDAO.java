package com.grubmenow.service.datamodel;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;
import lombok.ToString;

import org.joda.time.DateTime;

/**
 * Data Access Object Class to represent an Order placed by Customer 
 */

@Entity
@Table(name = "CUSTOMER_ORDER")
@ToString
public class CustomerOrderDAO {

	@Id
	@Getter
	@Setter
	@NonNull
    @Column(name = "ORDER_ID")
	private String orderId;
	
	@Getter
	@Setter
	@NonNull
    @Column(name = "CUSTOMER_ID")
	private String customerId;

	@Getter
	@Setter
	@NonNull
    @Column(name = "PROVIDER_ID")
	private String providerId;

	@Getter
	@Setter
	@NonNull
    @Column(name = "ORDER_CREATION_DATE")
	private DateTime orderCreationDate;
	
    @Getter
    @Setter
    @NonNull
    @Column(name = "ORDER_STATE")
	private String orderState;

}
