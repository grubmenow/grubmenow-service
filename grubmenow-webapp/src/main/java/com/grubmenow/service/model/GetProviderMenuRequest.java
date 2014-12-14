package com.grubmenow.service.model;

import lombok.Data;

@Data
public class GetProviderMenuRequest {
	private String providerId;
	private AvailableDay availableDay;
	private int timezoneOffsetMins;
}
