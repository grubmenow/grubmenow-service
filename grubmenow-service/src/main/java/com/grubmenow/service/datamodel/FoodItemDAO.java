package com.grubmenow.service.datamodel;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;
import lombok.ToString;

/**
 * Data Access Object Class to represent the food item and it's various attributes 
 */

@Entity
@Table(name = "FOOD_ITEM")
@ToString
public class FoodItemDAO {

	@Id
	@Getter
    @Setter
    @NonNull
    @Column(name = "FOOD_ITEM_ID")
	private String foodItemId;
	
	@Getter
    @Setter
    @NonNull
	@Column(name = "FOOD_ITEM_NAME")
	private String foodItemName;

    @Getter
    @Setter
    @NonNull
    @Column(name = "FOOD_ITEM_IMAGE_URL")
	private String foodItemImageUrl;

    @Getter
    @Setter
    @NonNull
    @Column(name = "FOOD_ITEM_DESCRIPTION")
	private String foodItemDescription;
    
    @Getter
    @Setter
    @NonNull
    @Column(name = "FOOD_ITEM_DESCRIPTION_TAGS")
	private String foodItemDescriptionTags;

    @Getter
    @Setter
    @NonNull
    @Column(name = "FOOD_ITEM_STATE")
	private FoodItemState foodItemState;

}
