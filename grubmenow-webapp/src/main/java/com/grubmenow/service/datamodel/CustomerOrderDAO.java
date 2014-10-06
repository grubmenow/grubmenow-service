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

import org.hibernate.annotations.Type;
import org.joda.time.DateTime;

import com.grubmenow.service.model.DeliveryMethod;
import com.grubmenow.service.model.PaymentMethod;

/**
 * Data Access Object Class to represent an Order placed by Customer 
 */

@Entity
@Table(name = "CUSTOMER_ORDER")
@ToString
@Data
@NoArgsConstructor
public class CustomerOrderDAO {

	@Id
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
    @Column(name = "PAYMENT_METHOD")
    @Enumerated(EnumType.STRING)
	private PaymentMethod paymentMethod;
	
	@NonNull
    @Column(name = "DELIVERY_METHOD")
    @Enumerated(EnumType.STRING)
	private DeliveryMethod deliveryMethod;
	
    @NonNull
    @Column(name = "ORDER_PAYMENT_STATE")
    @Enumerated(EnumType.STRING)
	private OrderPaymentState orderPaymentState;
    
    @NonNull
    @Column(name = "ORDER_STATE")
    @Enumerated(EnumType.STRING)
	private OrderState orderState;
    
    @NonNull
    @Column(name = "ORDER_STATE_MESSAGE")
    private String orderStateMessage;

	@NonNull
    @Column(name = "ORDER_CREATION_DATE")
	@Type(type="org.jadira.usertype.dateandtime.joda.PersistentDateTime")
	private DateTime orderCreationDate;

}
