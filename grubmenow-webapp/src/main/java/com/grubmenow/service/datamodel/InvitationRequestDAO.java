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
 * Data Access Object class for invitation request
 */

@Entity
@Table(name = "INVITATION_REQUEST")
@ToString
@Data
@NoArgsConstructor
public class InvitationRequestDAO {
    
    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    private int id;

    @Column(name = "ZIP_CODE")
	private String zipCode;

    @Column(name = "EMAIL_ID")
	private String emailId;
	
	@NonNull
    @Column(name = "INVITATION_REQUEST_DATE")
	@Type(type="org.jadira.usertype.dateandtime.joda.PersistentDateTime")
    private DateTime invitationRequestTime;
}
