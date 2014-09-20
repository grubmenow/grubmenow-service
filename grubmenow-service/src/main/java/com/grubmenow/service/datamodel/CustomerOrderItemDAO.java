package com.grubmenow.service.datamodel;

import java.util.Calendar;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;
import lombok.ToString;

/**
 * Data Access Object Class to represent an Order placed by Customer 
 */

@Entity
@Table(name = "CUSTOMER_ORDER_ITEM")
@ToString
public class CustomerOrderItemDAO {

	@Id
	@Getter
	@Setter
	@NonNull
    @Column(name = "ORDER_ITEM_ID")
	private String orderItemId;
	
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
    @Column(name = "FOOD_ITEM_ID")
	private String foodItemId;

	@Getter
	@Setter
	@NonNull
    @Column(name = "FOOD_ITEM_OFFER_ID")
	private String foodItemOfferId;
	
	@Getter
	@Setter
	@NonNull
    @Column(name = "ORDER_CREATION_DATE")
	private Calendar orderCreationDate;
}
