package com.grubmenow.service.datamodel;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.Type;
import org.joda.time.DateTime;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.ToString;

/**
 * Data Access Object Class to represent a Customer 
 */

@Entity
@Table(name = "SEARCH_SUGGESTION_FEEDBACK")
@ToString
@Data
@NoArgsConstructor
public class SearchSuggestionFeedbackDAO {
    
    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    private int id;

	@NonNull
    @Column(name = "SEARCH_SUGGESTION_FOOD_ITEM_NAME")
	private String foodItemName;
	
    @Column(name = "ZIP_CODE")
	private String zipCode;

    @Column(name = "EMAIL_ID")
	private String emailId;
	
	@NonNull
    @Column(name = "FEEDBACK_PROVIDED_DATE")
	@Type(type="org.jadira.usertype.dateandtime.joda.PersistentDateTime")
    private DateTime feedbackProvidedTime;
}
