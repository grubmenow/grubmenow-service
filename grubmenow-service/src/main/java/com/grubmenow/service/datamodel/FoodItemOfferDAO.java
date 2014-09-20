package com.grubmenow.service.datamodel;

import java.math.BigDecimal;
import java.util.Calendar;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;
import lombok.ToString;

/**
 * Data Access Object Class to represent Food Item Offer by Provider 
 */
@ToString
@Entity
@Table(name = "FOOD_ITEM_OFFER")
public class FoodItemOfferDAO {

	@Id
	@Getter
	@Setter
	@NonNull
    @Column(name = "FOOD_ITEM_OFFER_ID")
	private String foodItemOfferId;
	
	@Getter
	@Setter
	@NonNull
    @Column(name = "PROVIDER_ID")
	private String providerId;
	
	@Getter
	@Setter
	@NonNull
    @Column(name = "FOOD_ITEM_ID")
	private String foodItemId;
	
	@Getter
	@Setter
	@NonNull
    @Column(name = "OFFER_DESCRIPTION")
	private String offerDescription;
	
	@Getter
	@Setter
	@NonNull
    @Column(name = "OFFER_DESCRIPTION_TAGS")
	private String offerDescriptionTags;
	
	@Getter
	@Setter
	@NonNull
    @Column(name = "OFFER_UNIT_PRICE")
	private BigDecimal offerUnitPrice;
	
	@Getter
	@Setter
	@NonNull
    @Column(name = "OFFER_CURRENCY")
	private String offerCurrency;
	
	@Getter
	@Setter
	@NonNull
	@Column(name = "OFFER_QUANTITY")
	private Integer offerQuantity;
	
	@Getter
	@Setter
	@NonNull
	@Column(name = "OFFER_DAY")
	private Calendar offerDay;
	
	@Getter
	@Setter
	@NonNull
	@Column(name ="OFFER_MEAL_TYPE")
	private String offerMealType;
	
	
	@Getter
	@Setter
	@NonNull
    @Column(name = "IS_FOOD_DELIVERY_OPTION_AVAILABLE")
	private Boolean isFoodDeliveryOptionAvailable;
	
	@Getter
	@Setter
	@NonNull
    @Column(name = "IS_FOOD_PICK_UP_OPTION_AVAILABLE")
	private Boolean isFoodPickUpOptionAvailable;
	
    @Getter
    @Setter
    @NonNull
    @Column(name = "OFFER_STATE")
	private String offerState;


}
