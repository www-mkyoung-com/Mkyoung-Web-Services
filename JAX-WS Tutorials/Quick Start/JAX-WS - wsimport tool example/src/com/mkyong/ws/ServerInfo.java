package com.mkyong.ws;

import javax.jws.WebMethod;
import javax.jws.WebService;

@WebService
public class ServerInfo{

	@WebMethod
	public String getIpAddress() {
		
		return "10.10.10.10";
		
	}

}