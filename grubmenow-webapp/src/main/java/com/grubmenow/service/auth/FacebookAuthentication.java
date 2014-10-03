package com.grubmenow.service.auth;

import lombok.extern.apachecommons.CommonsLog;

import org.springframework.social.RevokedAuthorizationException;
import org.springframework.social.connect.Connection;
import org.springframework.social.facebook.api.Facebook;
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
	 * Authenticates the access token against facebook if it is valid or not. 
	 * @param accessToken the user's access token recieved from the browser client
	 * @return true if the authenticated, else false
	 */
	public boolean isAuthenticated(String accessToken)
	{
		AccessGrant accessGrant = new AccessGrant(accessToken);
		try
		{
			Connection<Facebook> connection = fbConnectionFactory.createConnection(accessGrant);
			return connection.test();
		}
		catch (RevokedAuthorizationException ex)
		{
			log.debug("authorization revoked: " + ex.getMessage());
			return false;
		}
	}
}
