package com.grubmenow.service;

import com.grubmenow.service.fbauth.FBAuth;

public class FBLoginTest {
	
	public static void main(String[] args) {
		FBAuth fbAuth = new FBAuth("730291313693447", "c313676be38b8efe9baaf9b8833d3db5");
		
		String accessToken = "CAAKYMjJV0wcBAJ7kUJEXPo3kTueFZBBxcwkwPqcCjen73BXJFWrXOfdPGLCExBZBplh9bpwtSixnHMZAswvwKjt2gznR3OjnianSYSSN4PJlW4lU0irjqlurPXwyLZA52qSeXxcH6swCFUiSZBtz95jUYYP2Lr0wfJmhRIKnXyiZAcCk2EFTzSQVltuxAlo3wjHcu3lWnZCQgOp7SmOhJGZAgJBn9ChHM9EZD";
		boolean isConnected = fbAuth.isAuthenticated(accessToken);
		System.out.println(isConnected);
	}
}
