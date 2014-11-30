package com.grubmenow.service.datamodel;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.ToString;

@Entity
@Table(name = "ORDER_FEEDBACK")
@ToString
@Data
@NoArgsConstructor
public class OrderFeedbackDAO {

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
	
    @Column(name = "RATING")
	private int rating;
	
    @Column(name = "FEEDBACK")
	private String feedback;
}
