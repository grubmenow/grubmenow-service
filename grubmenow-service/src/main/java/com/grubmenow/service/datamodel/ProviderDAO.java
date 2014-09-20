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
 * Data Access Object Class to represent a Provider 
 */

@Entity
@Table(name = "PROVIDER")
@ToString
public class ProviderDAO {

	@Id
	@Getter
	@Setter
	@NonNull
    @Column(name = "PROVIDER_ID")
	private String providerId;

	@Getter
	@Setter
	@NonNull
    @Column(name = "PROVIDER_NAME")
	private String providerName;
	
	@Getter
	@Setter
	@NonNull
    @Column(name = "PROVIDER_ADDRESS_STREET_NUMBER")
	private String providerAddressStreetNumber;
	
	@Getter
	@Setter
	@NonNull
    @Column(name = "PROVIDER_ADDRESS_STREET")
	private String providerAddressStreet;
	
	@Getter
	@Setter
	@NonNull
    @Column(name = "PROVIDER_ADDRESS_APARTMENT_NUMBER")
	private String providerAddressApartmentNumber;
	
	@Getter
	@Setter
	@NonNull
    @Column(name = "PROVIDER_ADDRESS_ZIP_CODE")
	private String providerAddressZipCode;
	
	@Getter
	@Setter
	@NonNull
    @Column(name = "PROVIDER_ADDRESS_STATE")
	private String providerAddressState;
	
	@Getter
	@Setter
	@NonNull
    @Column(name = "PROVIDER_ADDRESS_CITY")
	private String providerAddressCity;
	
	@Getter
	@Setter
	@NonNull
    @Column(name = "PROVIDER_IMAGE_URL")
	private String providerImageURL;
	
	@Getter
	@Setter
	@NonNull
    @Column(name = "IS_ONLINE_PAYMENT_ACCEPTED")
	private Boolean isOnlinePaymentAccepted;
	
	@Getter
	@Setter
	@NonNull
    @Column(name = "IS_CASH_ON_DELIVERY_PAYMENT_ACCEPTED")
	private Boolean isCashOnDeliverPaymentAccepted;
	
	@Getter
	@Setter
	@NonNull
    @Column(name = "IS_CARD_ON_DELIVERY_PAYMENT_ACCEPTED")
	private Boolean isCardOnDeliverPaymentAccepted;
	
    @Getter
    @Setter
    @NonNull
    @Column(name = "PROVIDER_STATE")
	private String providerState;
}
