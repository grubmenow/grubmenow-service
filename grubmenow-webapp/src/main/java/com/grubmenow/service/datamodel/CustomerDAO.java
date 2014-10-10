package com.grubmenow.service.datamodel;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.ToString;

/**
 * Data Access Object Class to represent a Customer 
 */

@Entity
@Table(name = "CUSTOMER")
@ToString
@Data
@NoArgsConstructor
public class CustomerDAO {

	@Id
	@NonNull
    @Column(name = "CUSTOMER_ID")
	private String customerId;
	
	@NonNull
    @Column(name = "CUSTOMER_FIRST_NAME")
	private String customerFirstName;

	@NonNull
    @Column(name = "CUSTOMER_LAST_NAME")
	private String customerLastName;
	
	@NonNull
    @Column(name = "CUSTOMER_EMAIL_ID")
	private String customerEmailId;

    @NonNull
    @Column(name = "CUSTOMER_STATE")
    @Enumerated(EnumType.STRING)
	private CustomerState customerState;
}
