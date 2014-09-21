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
 * Data Access Object Class to represent a Provider 
 */

@Entity
@Table(name = "PROVIDER")
@ToString
@NoArgsConstructor
@Data
public class ProviderDAO {

	@Id
	@NonNull
    @Column(name = "PROVIDER_ID")
	private String providerId;

	@NonNull
    @Column(name = "PROVIDER_NAME")
	private String providerName;
	
	@NonNull
    @Column(name = "PROVIDER_ADDRESS_STREET_NUMBER")
	private String providerAddressStreetNumber;
	
	@NonNull
    @Column(name = "PROVIDER_ADDRESS_STREET")
	private String providerAddressStreet;
	
	@NonNull
    @Column(name = "PROVIDER_ADDRESS_APARTMENT_NUMBER")
	private String providerAddressApartmentNumber;
	
	@NonNull
    @Column(name = "PROVIDER_ADDRESS_ZIP_CODE")
	private String providerAddressZipCode;
	
	@NonNull
    @Column(name = "PROVIDER_ADDRESS_STATE")
	private String providerAddressState;
	
	@NonNull
    @Column(name = "PROVIDER_ADDRESS_CITY")
	private String providerAddressCity;
	
	@NonNull
    @Column(name = "PROVIDER_IMAGE_URL")
	private String providerImageURL;
	
	@NonNull
    @Column(name = "IS_ONLINE_PAYMENT_ACCEPTED")
	private Boolean isOnlinePaymentAccepted;
	
	@NonNull
    @Column(name = "IS_CASH_ON_DELIVERY_PAYMENT_ACCEPTED")
	private Boolean isCashOnDeliverPaymentAccepted;
	
	@NonNull
    @Column(name = "IS_CARD_ON_DELIVERY_PAYMENT_ACCEPTED")
	private Boolean isCardOnDeliverPaymentAccepted;
	
    @NonNull
    @Column(name = "PROVIDER_STATE")
	private ProviderState providerState;
}
