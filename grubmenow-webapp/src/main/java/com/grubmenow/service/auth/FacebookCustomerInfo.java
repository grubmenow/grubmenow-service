package com.grubmenow.service.auth;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

@AllArgsConstructor
@EqualsAndHashCode
@ToString
public class FacebookCustomerInfo 
{
	@Getter
	String facebookUserId;
	@Getter
	String emailId;
	@Getter
	String firstName;
	@Getter
	String lastName;
}
