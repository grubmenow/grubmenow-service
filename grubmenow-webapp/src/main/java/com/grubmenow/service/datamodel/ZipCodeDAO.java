package com.grubmenow.service.datamodel;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.ToString;

@Entity
@Table(name = "ZIP_DATA")
@ToString
@NoArgsConstructor
@Data
public class ZipCodeDAO {

	@Id
	@NonNull
    @Column(name = "PROVIDER_ID")
	private String zipCode;

	@NonNull
    @Column(name = "STATE_ABBREVIATION")
	private String stateAbbrevation;

    @Column(name = "LATTITUDE")
	private float lattitude;
	
    @Column(name = "LONGITUDE")
	private float longitude;
	
	@NonNull
    @Column(name = "CITY")
	private String city;
	
	@NonNull
    @Column(name = "STATE")
	private String state;
}
