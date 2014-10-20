package com.grubmenow.service.datamodel;

import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.ToString;

import org.hibernate.annotations.Type;
import org.joda.time.DateTime;

import com.grubmenow.service.model.Currency;

/**
 * Data Access Object Class to represent an Order placed by Customer 
 */

@Entity
@Table(name = "CUSTOMER_ORDER_ITEM")
@ToString
@Data
@NoArgsConstructor
public class CustomerOrderItemDAO {

	@Id
	@NonNull
    @Column(name = "ORDER_ITEM_ID")
	private String orderItemId;
	
	@NonNull
    @Column(name = "ORDER_ID")
	private String orderId;
	
	@NonNull
    @Column(name = "CUSTOMER_ID")
	private String customerId;

	@NonNull
    @Column(name = "PROVIDER_ID")
	private String providerId;

    @NonNull
    @Column(name = "FOOD_ITEM_ID")
	private String foodItemId;

	@NonNull
    @Column(name = "FOOD_ITEM_OFFER_ID")
	private String foodItemOfferId;

	@NonNull
	@Column(name = "QUANTITY")
	private Integer quantity;
	
	@NonNull
	@Column(name = "ORDER_ITEM_AMOUNT")
	private BigDecimal orderItemAmount;
	
	@NonNull
	@Column(name = "ORDER_CURRENCY")
	private Currency orderCurrency;
	
	@NonNull
    @Column(name = "ORDER_CREATION_DATE")
	@Type(type="org.jadira.usertype.dateandtime.joda.PersistentDateTime")
	private DateTime orderCreationDate;

}
