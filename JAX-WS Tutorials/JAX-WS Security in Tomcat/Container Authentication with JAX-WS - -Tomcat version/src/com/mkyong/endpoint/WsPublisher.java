package com.mkyong.endpoint;
 
import javax.xml.ws.Endpoint;
import com.mkyong.ws.UserProfileImpl;
 
//Endpoint publisher
public class WsPublisher{
 
	public static void main(String[] args) {
	   Endpoint.publish("http://localhost:8888/ws/user", new UserProfileImpl());
    }
 
}