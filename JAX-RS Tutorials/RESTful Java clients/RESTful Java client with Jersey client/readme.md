This tutorial show you how to use **Jersey client APIs** to create a RESTful Java client to perform “**GET**” and “**POST**” requests to REST service that created in this “[Jersey + Json](http://www.mkyong.com/webservices/jax-rs/json-example-with-jersey-jackson/)” example.

## 1\. Jersey Client Dependency

To use Jersey client APIs, declares “**jersey-client.jar**” in your `pom.xml` file.

_File : pom.xml_

    <dependency>
    	<groupId>com.sun.jersey</groupId>
    	<artifactId>jersey-client</artifactId>
    	<version>1.8</version>
    </dependency>

## 2\. GET Request

Review last REST service.

    @Path("/json/metallica")
    public class JSONService {

    	@GET
    	@Path("/get")
    	@Produces(MediaType.APPLICATION_JSON)
    	public Track getTrackInJSON() {

    		Track track = new Track();
    		track.setTitle("Enter Sandman");
    		track.setSinger("Metallica");

    		return track;

    	}
    	//...

Jersey client to send a “GET” request and print out the returned json data.

    package com.mkyong.client;

    import com.sun.jersey.api.client.Client;
    import com.sun.jersey.api.client.ClientResponse;
    import com.sun.jersey.api.client.WebResource;

    public class JerseyClientGet {

      public static void main(String[] args) {
    	try {

    		Client client = Client.create();

    		WebResource webResource = client
    		   .resource("http://localhost:8080/RESTfulExample/rest/json/metallica/get");

    		ClientResponse response = webResource.accept("application/json")
                       .get(ClientResponse.class);

    		if (response.getStatus() != 200) {
    		   throw new RuntimeException("Failed : HTTP error code : "
    			+ response.getStatus());
    		}

    		String output = response.getEntity(String.class);

    		System.out.println("Output from Server .... \n");
    		System.out.println(output);

    	  } catch (Exception e) {

    		e.printStackTrace();

    	  }

    	}
    }

Output…

    Output from Server ....

    {"singer":"Metallica","title":"Enter Sandman"}

## 3\. POST Request

Review last REST service.

    @Path("/json/metallica")
    public class JSONService {

    	@POST
    	@Path("/post")
    	@Consumes(MediaType.APPLICATION_JSON)
    	public Response createTrackInJSON(Track track) {

    		String result = "Track saved : " + track;
    		return Response.status(201).entity(result).build();

    	}
    	//...

Jersey client to send a “POST” request, with json data and print out the returned output.

    package com.mkyong.client;

    import com.sun.jersey.api.client.Client;
    import com.sun.jersey.api.client.ClientResponse;
    import com.sun.jersey.api.client.WebResource;

    public class JerseyClientPost {

      public static void main(String[] args) {

    	try {

    		Client client = Client.create();

    		WebResource webResource = client
    		   .resource("http://localhost:8080/RESTfulExample/rest/json/metallica/post");

    		String input = "{\"singer\":\"Metallica\",\"title\":\"Fade To Black\"}";

    		ClientResponse response = webResource.type("application/json")
    		   .post(ClientResponse.class, input);

    		if (response.getStatus() != 201) {
    			throw new RuntimeException("Failed : HTTP error code : "
    			     + response.getStatus());
    		}

    		System.out.println("Output from Server .... \n");
    		String output = response.getEntity(String.class);
    		System.out.println(output);

    	  } catch (Exception e) {

    		e.printStackTrace();

    	  }

    	}
    }

Output…

    Output from Server ....

    Track saved : Track [title=Fade To Black, singer=Metallica]

[http://www.mkyong.com/webservices/jax-rs/restful-java-client-with-jersey-client/](http://www.mkyong.com/webservices/jax-rs/restful-java-client-with-jersey-client/)
