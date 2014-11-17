package com.grubmenow.service.model;

import lombok.Data;

@Data
public class GetCustomerAccountDetailsResponse {
    private String firstName;
    private String lastName;
    private String emailId;
    private String phoneNumber;
}
