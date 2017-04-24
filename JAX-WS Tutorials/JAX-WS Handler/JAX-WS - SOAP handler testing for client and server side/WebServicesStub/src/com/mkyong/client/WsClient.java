package com.mkyong.client;

import com.mkyong.ws.ServerInfo;
import com.mkyong.ws.ServerInfoService;

public class WsClient{
	
	public static void main(String[] args) throws Exception {
	   
		ServerInfoService sis = new ServerInfoService();
		ServerInfo si = sis.getServerInfoPort();

		System.out.println(si.getServerName());
       
    }

}
