package com.grubmenow.service.datamodel;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.Type;
import org.joda.time.DateTime;

import com.grubmenow.service.model.FeedbackType;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.ToString;

/**
 * Data Access Object Class to represent a General feedback or a contact
 */

@Entity
@Table(name = "GENERAL_FEEDBACK")
@ToString
@Data
@NoArgsConstructor
public class GeneralFeedbackDAO {
    
    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    private int id;

	@NonNull
	@Enumerated(EnumType.STRING)
    @Column(name = "FEEDBACK_TYPE")
	private FeedbackType feedbackType;
	
    @Column(name = "ZIP_CODE")
	private String zipCode;

    @Column(name = "EMAIL_ID")
	private String emailId;
    
    @Column(name = "MESSAGE")
    private String message;
	
    @Column(name = "SEARCH_DAY")
    private String searchDay;
    
	@NonNull
    @Column(name = "FEEDBACK_PROVIDED_DATE")
	@Type(type="org.jadira.usertype.dateandtime.joda.PersistentDateTime")
    private DateTime feedbackProvidedTime;
}
