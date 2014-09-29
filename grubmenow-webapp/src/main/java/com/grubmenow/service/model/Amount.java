package com.grubmenow.service.model;

import lombok.Data;

@Data
public class Amount {
	private Currency currency;
	private float value;
}
