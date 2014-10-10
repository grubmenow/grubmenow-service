package com.grubmenow.service;

import com.grubmenow.service.auth.AuthenticationException;
import com.grubmenow.service.auth.FacebookAuthentication;
import com.grubmenow.service.auth.FacebookCustomerInfo;


public class FBLoginTest {
	
	public static void main(String[] args) throws AuthenticationException {
		FacebookAuthentication fbAuth = new FacebookAuthentication("730291313693447", "c313676be38b8efe9baaf9b8833d3db5");
		
		String accessToken = "CAAKYMjJV0wcBANIid6pJDy0KP6RoimPty5NM6zPAuhcIzgDBdaXtbw2ChPnVfWG2BdZCixtnLgU0KGgijKeDmdgSvfrYULq77eBUQxw8JkKOEuRcZBUlAJert8HQPlbZBzlBID6g1NDJkCNYJM05TA3go38WTKMnZAv7LloVmoazmikLAmUQCtllXtpJANHnRcYMmcGvphXFBPZC6iXJ3";
		FacebookCustomerInfo customerInfo = fbAuth.validateTokenAndFetchCustomerInfo(accessToken);
		System.out.println(customerInfo);
	}
}
