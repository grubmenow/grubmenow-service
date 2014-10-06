package com.grubmenow.service.datamodel;

import javax.persistence.Id;

import lombok.Data;
import lombok.NonNull;
import lombok.ToString;

import org.joda.time.DateTime;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBAttribute;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBHashKey;

/**
 * Data Access Object Class to represent an Order placed by Customer 
 */

@ToString
@Data
public class OrderDAO {

	@Id
	@NonNull
	@DynamoDBHashKey(attributeName="orderId")
	private String orderId;
	
	@NonNull
	@DynamoDBAttribute(attributeName="customerId")
	private String customerId;

	@NonNull
	@DynamoDBAttribute(attributeName="providerId")
	private String providerId;

	@NonNull
	@DynamoDBAttribute(attributeName="orderCreationDate")
	private DateTime orderCreationDate;
	
	@NonNull
	private String foodItemId;

	@NonNull
	private String foodItemOfferId;
}
