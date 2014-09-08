package com.grubmenow.service.datamodel;

import java.util.Set;

import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;
import lombok.ToString;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBAttribute;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBHashKey;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBTable;

/**
 * Data Access Object Class to represent a Provider 
 */

@ToString
@DynamoDBTable(tableName="Provider")
public class ProviderDAO {

	@Getter
	@Setter
	@NonNull
	@DynamoDBHashKey(attributeName="providerId")
	private String providerId;

	@Getter
	@Setter
	@NonNull
	@DynamoDBAttribute(attributeName="providerName")
	private String providerName;
	
	@Getter
	@Setter
	@NonNull
	@DynamoDBAttribute(attributeName="providerAddressStreetNumber")
	private String providerAddressStreetNumber;
	
	@Getter
	@Setter
	@NonNull
	@DynamoDBAttribute(attributeName="providerAddressStreet")
	private String providerAddressStreet;
	
	@Getter
	@Setter
	@NonNull
	@DynamoDBAttribute(attributeName="providerAddressApartmentNumber")
	private String providerAddressApartmentNumber;
	
	@Getter
	@Setter
	@NonNull
	@DynamoDBAttribute(attributeName="providerAddressZipCode")
	private String providerAddressZipCode;
	
	@Getter
	@Setter
	@NonNull
	@DynamoDBAttribute(attributeName="providerAddressState")
	private String providerAddressState;
	
	@Getter
	@Setter
	@NonNull
	@DynamoDBAttribute(attributeName="providerAddressCity")
	private String providerAddressCity;
	
	@Getter
	@Setter
	@NonNull
	@DynamoDBAttribute(attributeName="providerImageURL")
	private String providerImageURL;
	
	@Getter
	@Setter
	@NonNull
	@DynamoDBAttribute(attributeName="acceptedPaymentMethods")
	private Set<String> acceptedPaymentMethods;
}
