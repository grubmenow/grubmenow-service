package com.grubmenow.service.datamodel;

import java.util.Calendar;

import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;
import lombok.ToString;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBAttribute;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBHashKey;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBTable;

/**
 * Data Access Object Class to represent an Order placed by Customer 
 */

@ToString
@DynamoDBTable(tableName="Order")
public class OrderDAO {

	@Getter
	@Setter
	@NonNull
	@DynamoDBHashKey(attributeName="orderId")
	private String orderId;
	
	@Getter
	@Setter
	@NonNull
	@DynamoDBAttribute(attributeName="customerId")
	private String customerId;

	@Getter
	@Setter
	@NonNull
	@DynamoDBAttribute(attributeName="providerId")
	private String providerId;

	@Getter
	@Setter
	@NonNull
	@DynamoDBAttribute(attributeName="orderCreationDate")
	private Calendar orderCreationDate;
	
	@Getter
	@Setter
	@NonNull
	private String foodItemId;

	@Getter
	@Setter
	@NonNull
	private String foodItemOfferId;

}
