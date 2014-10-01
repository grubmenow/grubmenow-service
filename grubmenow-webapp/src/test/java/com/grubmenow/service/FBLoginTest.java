package com.grubmenow.service;

import org.springframework.social.connect.Connection;
import org.springframework.social.facebook.api.Facebook;
import org.springframework.social.facebook.connect.FacebookConnectionFactory;
import org.springframework.social.oauth2.AccessGrant;

public class FBLoginTest {
	
	public static void main(String[] args) {
		FacebookConnectionFactory connectionFactory =
			    new FacebookConnectionFactory("clientId", "clientSecret");
		String accessToken = null;
		AccessGrant accessGrant = new AccessGrant(accessToken);
		Connection<Facebook> connection = connectionFactory.createConnection(accessGrant);
		boolean isConnected = connection.test();
		
		// test 2 things. 
		
		// test that the token is valid
		// test that some other apps tokens are not valid
	}

}
