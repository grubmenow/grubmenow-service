package com.grubmenow.service.model;

import lombok.Data;

@Data
public class FoodAlert {
    private String foodAlertId;
    private FoodAlertType foodAlertType;
    private String zipCode;
    private int radius; // miles
    private String providerId;
    private String subscriberCustomerId;
    private String websiteAuthenticationToken;
}
