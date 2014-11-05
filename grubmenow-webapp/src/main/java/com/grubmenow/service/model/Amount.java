package com.grubmenow.service.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Amount {
    /**
     * the value in cents
     */
	private int value;
	
	private Currency currency;
}
