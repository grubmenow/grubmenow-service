package com.grubmenow.service.datamodel;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.ToString;

/**
 * Data Access Object Class to represent the food item and it's various
 * attributes
 */

@Entity
@Table(name = "FOOD_ITEM")
@ToString
@Data
@NoArgsConstructor
public class FoodItemDAO {

	@Id
	@NonNull
	@Column(name = "FOOD_ITEM_ID")
	private String foodItemId;

	@NonNull
	@Column(name = "FOOD_ITEM_NAME")
	private String foodItemName;

	@NonNull
	@Column(name = "FOOD_ITEM_IMAGE_URL")
	private String foodItemImageUrl;

	@NonNull
	@Column(name = "FOOD_ITEM_DESCRIPTION")
	private String foodItemDescription;

	@NonNull
	@Column(name = "FOOD_ITEM_DESCRIPTION_TAGS")
	private String foodItemDescriptionTags;

	@NonNull
	@Column(name = "FOOD_ITEM_STATE")
	private FoodItemState foodItemState;

}
