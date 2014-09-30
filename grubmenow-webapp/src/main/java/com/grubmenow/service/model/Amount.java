package com.grubmenow.service.model;

import java.math.BigDecimal;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Amount {
	private BigDecimal value;
	
	private Currency currency;
}
