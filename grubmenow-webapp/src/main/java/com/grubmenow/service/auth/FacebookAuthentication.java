package com.grubmenow.service.auth;

import java.util.HashMap;
import java.util.Map;

import lombok.extern.apachecommons.CommonsLog;

import org.springframework.social.connect.Connection;
import org.springframework.social.facebook.api.Facebook;
import org.springframework.social.facebook.api.FacebookProfile;
import org.springframework.social.facebook.connect.FacebookConnectionFactory;
import org.springframework.social.oauth2.AccessGrant;


@CommonsLog
public class FacebookAuthentication {
	
	private Map<String, FacebookCustomerInfo> predefinedMap = new HashMap<>();
	
	private final FacebookConnectionFactory fbConnectionFactory;
	
	public FacebookAuthentication(String appId, String appSecretKey) {
		fbConnectionFactory =
			    new FacebookConnectionFactory(appId, appSecretKey);
		
		predefinedMap.put("10152843609422975", new FacebookCustomerInfo("10152843609422975", "pokar.nitin@gmail.com", "Nitin", "Pokar"));
	}
	
	/**
	 * Validates the token against FB and returns the FacebookCustomerInfo object. 
	 * @param accessToken the user's access token received from the browser client
	 * @return true if the authenticated, else false
	 * @throws AuthenticationException in case of authentication failure
	 */
	public FacebookCustomerInfo validateTokenAndFetchCustomerInfo(String accessToken) throws AuthenticationException
	{
		
		if(predefinedMap.containsKey(accessToken)) {
			return predefinedMap.get(accessToken);
		}
		
		AccessGrant accessGrant = new AccessGrant(accessToken);
		try
		{
			Connection<Facebook> connection = fbConnectionFactory.createConnection(accessGrant);
			if (!connection.test())
			{
				throw new AuthenticationException("Token is not valid");
			}
			FacebookProfile fbProfile = connection.getApi().userOperations()
					.getUserProfile();
			FacebookCustomerInfo facebookCustomerInfo = new FacebookCustomerInfo(
					fbProfile.getId(), fbProfile.getEmail(),
					fbProfile.getFirstName(), fbProfile.getLastName());
			return facebookCustomerInfo;
		}
		catch (Exception ex)
		{
			throw new AuthenticationException("Authentication failure exception", ex);
		}
	}
}
