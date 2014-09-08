package com.grubmenow.service.datamodel;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBHashKey;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBTable;

import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;
import lombok.ToString;

/**
 * Data Access Object Class to represent a Customer 
 */

@ToString
@DynamoDBTable(tableName="Customer")
public class CustomerDAO {

	@Getter
	@Setter
	@NonNull
	@DynamoDBHashKey(attributeName="customerId")
	private String customerId;
}
