package com.grubmenow.service.datamodel;

import java.math.BigDecimal;
import java.util.Calendar;

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

/**
 * Data Access Object Class to represent Food Item Offer by Provider
 */
@ToString
@Entity
@Table(name = "FOOD_ITEM_OFFER")
@Data
@NoArgsConstructor
public class FoodItemOfferDAO {

	@Id
	@NonNull
	@Column(name = "FOOD_ITEM_OFFER_ID")
	private String foodItemOfferId;

	@NonNull
	@Column(name = "PROVIDER_ID")
	private String providerId;

	@NonNull
	@Column(name = "FOOD_ITEM_ID")
	private String foodItemId;

	@NonNull
	@Column(name = "OFFER_DESCRIPTION")
	private String offerDescription;

	@NonNull
	@Column(name = "OFFER_DESCRIPTION_TAGS")
	private String offerDescriptionTags;

	@NonNull
	@Column(name = "OFFER_UNIT_PRICE")
	private BigDecimal offerUnitPrice;

	@NonNull
	@Column(name = "OFFER_CURRENCY")
	private String offerCurrency;

	@NonNull
	@Column(name = "OFFER_QUANTITY")
	private Integer offerQuantity;

	@NonNull
	@Column(name = "OFFER_DAY")
	private Calendar offerDay;

	@NonNull
	@Column(name = "OFFER_MEAL_TYPE")
	private String offerMealType;

	@NonNull
	@Column(name = "IS_FOOD_DELIVERY_OPTION_AVAILABLE")
	private Boolean isFoodDeliveryOptionAvailable;

	@NonNull
	@Column(name = "IS_FOOD_PICK_UP_OPTION_AVAILABLE")
	private Boolean isFoodPickUpOptionAvailable;

	@NonNull
	@Column(name = "OFFER_STATE")
	@Enumerated(EnumType.STRING)
	private OfferState offerState;

}
