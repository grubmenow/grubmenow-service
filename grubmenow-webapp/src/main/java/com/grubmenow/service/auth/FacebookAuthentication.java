package com.grubmenow.service.auth;

import lombok.extern.apachecommons.CommonsLog;

import org.springframework.social.connect.Connection;
import org.springframework.social.facebook.api.Facebook;
import org.springframework.social.facebook.api.FacebookProfile;
import org.springframework.social.facebook.connect.FacebookConnectionFactory;
import org.springframework.social.oauth2.AccessGrant;


@CommonsLog
public class FacebookAuthentication {
	private final FacebookConnectionFactory fbConnectionFactory;
	
	public FacebookAuthentication(String appId, String appSecretKey) {
		fbConnectionFactory =
			    new FacebookConnectionFactory(appId, appSecretKey);
	}
	
	/**
	 * Validates the token against FB and returns the FacebookCustomerInfo object. 
	 * @param accessToken the user's access token received from the browser client
	 * @return true if the authenticated, else false
	 * @throws AuthenticationException in case of authentication failure
	 */
	public FacebookCustomerInfo validateTokenAndFetchCustomerInfo(String accessToken) throws AuthenticationException
	{
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
