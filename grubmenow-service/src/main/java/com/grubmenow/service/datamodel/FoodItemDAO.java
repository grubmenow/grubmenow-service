package com.grubmenow.service.datamodel;

import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;
import lombok.ToString;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBAttribute;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBHashKey;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBTable;

/**
 * Data Access Object Class to represent the food item and it's various attributes 
 */

@ToString
@DynamoDBTable(tableName="FoodItem")
public class FoodItemDAO {

	@Getter
    @Setter
    @NonNull
	@DynamoDBHashKey(attributeName="foodItemId")
	private String foodItemId;
	
	@Getter
    @Setter
    @NonNull
    @DynamoDBAttribute(attributeName="foodItemName")
	private String foodItemName;

    @Getter
    @Setter
    @NonNull
    @DynamoDBAttribute(attributeName="foodItemImageUrl")  
	private String foodItemImageUrl;

    @Getter
    @Setter
    @NonNull
    @DynamoDBAttribute(attributeName="foodItemDescription")
	private String foodItemDescription;

}
