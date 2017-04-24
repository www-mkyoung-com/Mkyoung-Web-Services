package com.mkyong.endpoint;

import javax.xml.ws.Endpoint;
import com.mkyong.ws.ImageServerImpl;

//Endpoint publisher
public class ImagePublisher{
	
	public static void main(String[] args) {
		
		Endpoint.publish("http://localhost:9999/ws/image", new ImageServerImpl());
		
		System.out.println("Server is published!");
	   
    }

}
