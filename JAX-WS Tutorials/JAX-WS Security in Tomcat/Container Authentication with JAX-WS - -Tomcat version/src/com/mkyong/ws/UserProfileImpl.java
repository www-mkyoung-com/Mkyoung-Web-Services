package com.mkyong.ws;

import javax.jws.WebService;

//Service Implementation Bean
@WebService(endpointInterface = "com.mkyong.ws.UserProfile")
public class UserProfileImpl implements UserProfile{

	@Override
	public String getUserName() {
		
		return "getUserName() : returned value";
		
	}

}