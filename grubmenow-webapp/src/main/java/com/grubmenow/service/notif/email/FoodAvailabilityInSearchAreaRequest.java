package com.grubmenow.service.notif.email;

import com.grubmenow.service.datamodel.CustomerDAO;

import lombok.Data;

@Data
public class FoodAvailabilityInSearchAreaRequest {
    private CustomerDAO customer;
}
