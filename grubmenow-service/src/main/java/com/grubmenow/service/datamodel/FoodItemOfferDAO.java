package com.grubmenow.service.datamodel;

import java.math.BigDecimal;
import java.util.Calendar;

import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;
import lombok.ToString;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBAttribute;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBHashKey;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBTable;

/**
 * Data Access Object Class to represent Food Item Offer by Provider 
 */
@ToString
@DynamoDBTable(tableName="FoodItemOffer")
public class FoodItemOfferDAO {

	@Getter
	@Setter
	@NonNull
	@DynamoDBHashKey(attributeName="foodItemOfferId")
	private String foodItemOfferId;
	
	@Getter
	@Setter
	@NonNull
	@DynamoDBAttribute(attributeName="providerId")
	private String providerId;
	
	@Getter
	@Setter
	@NonNull
	@DynamoDBAttribute(attributeName="foodItemId")
	private String foodItemId;
	
	@Getter
	@Setter
	@NonNull
	@DynamoDBAttribute(attributeName="providerFoodItemOfferDescription")
	private String providerFoodItemOfferDescription;
	
	@Getter
	@Setter
	@NonNull
	@DynamoDBAttribute(attributeName="providerFoodItemOfferUnitPrice")
	private BigDecimal providerFoodItemOfferUnitPrice;
	
	@Getter
	@Setter
	@NonNull
	@DynamoDBAttribute(attributeName="providerFoodItemOfferCurrency")
	private String providerFoodItemOfferCurrency;
	
	@Getter
	@Setter
	@NonNull
	@DynamoDBAttribute(attributeName="providerFoodItemOfferUnitQuantity")
	private Integer providerFoodItemOfferUnitQuantity;
	
	@Getter
	@Setter
	@NonNull
	@DynamoDBAttribute(attributeName="offerDay")
	private Calendar offerDay;
	
	@Getter
	@Setter
	@NonNull
	@DynamoDBAttribute(attributeName="mealType")
	private String mealType;

}
