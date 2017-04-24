This tutorial show you how to create a RESTful Java client with [RESTEasy client framework](http://www.jboss.org/resteasy), to perform “**GET**” and “**POST**” requests to REST service that created in last “[Jackson + JAX-RS](http://www.mkyong.com/webservices/jax-rs/integrate-jackson-with-resteasy/)” tutorial.

## 1\. RESTEasy Client Framework

RESTEasy client framework is included in RESTEasy core module, so, you just need to declares the “**resteasy-jaxrs.jar**” in your `pom.xml` file.

_File : pom.xml_

    <dependency>
    	<groupId>org.jboss.resteasy</groupId>
    	<artifactId>resteasy-jaxrs</artifactId>
    	<version>2.2.1.GA</version>
    </dependency>

## 2\. GET Request

Review last REST service.

    @Path("/json/product")
    public class JSONService {

    	@GET
    	@Path("/get")
    	@Produces("application/json")
    	public Product getProductInJSON() {

    		Product product = new Product();
    		product.setName("iPad 3");
    		product.setQty(999);

    		return product;

    	}
    	//...

RESTEasy client to send a “GET” request.

    import java.io.BufferedReader;
    import java.io.ByteArrayInputStream;
    import java.io.IOException;
    import java.io.InputStreamReader;
    import org.apache.http.client.ClientProtocolException;
    import org.jboss.resteasy.client.ClientRequest;
    import org.jboss.resteasy.client.ClientResponse;

    public class RESTEasyClientGet {

    	public static void main(String[] args) {
    	  try {

    		ClientRequest request = new ClientRequest(
    				"http://localhost:8080/RESTfulExample/json/product/get");
    		request.accept("application/json");
    		ClientResponse<String> response = request.get(String.class);

    		if (response.getStatus() != 200) {
    			throw new RuntimeException("Failed : HTTP error code : "
    				+ response.getStatus());
    		}

    		BufferedReader br = new BufferedReader(new InputStreamReader(
    			new ByteArrayInputStream(response.getEntity().getBytes())));

    		String output;
    		System.out.println("Output from Server .... \n");
    		while ((output = br.readLine()) != null) {
    			System.out.println(output);
    		}

    	  } catch (ClientProtocolException e) {

    		e.printStackTrace();

    	  } catch (IOException e) {

    		e.printStackTrace();

    	  } catch (Exception e) {

    		e.printStackTrace();

    	  }

    	}

    }

Output…

    Output from Server ....

    {"qty":999,"name":"iPad 3"}

## 3\. POST Request

Review last REST service also.

    @Path("/json/product")
    public class JSONService {

            @POST
    	@Path("/post")
    	@Consumes("application/json")
    	public Response createProductInJSON(Product product) {

    		String result = "Product created : " + product;
    		return Response.status(201).entity(result).build();

    	}
    	//...

RESTEasy client to send a “POST” request.

    import java.io.BufferedReader;
    import java.io.ByteArrayInputStream;
    import java.io.IOException;
    import java.io.InputStreamReader;
    import java.net.MalformedURLException;
    import org.jboss.resteasy.client.ClientRequest;
    import org.jboss.resteasy.client.ClientResponse;

    public class RESTEasyClientPost {

    	public static void main(String[] args) {

    	  try {

    		ClientRequest request = new ClientRequest(
    			"http://localhost:8080/RESTfulExample/json/product/post");
    		request.accept("application/json");

    		String input = "{\"qty\":100,\"name\":\"iPad 4\"}";
    		request.body("application/json", input);

    		ClientResponse<String> response = request.post(String.class);

    		if (response.getStatus() != 201) {
    			throw new RuntimeException("Failed : HTTP error code : "
    				+ response.getStatus());
    		}

    		BufferedReader br = new BufferedReader(new InputStreamReader(
    			new ByteArrayInputStream(response.getEntity().getBytes())));

    		String output;
    		System.out.println("Output from Server .... \n");
    		while ((output = br.readLine()) != null) {
    			System.out.println(output);
    		}

    	  } catch (MalformedURLException e) {

    		e.printStackTrace();

    	  } catch (IOException e) {

    		e.printStackTrace();

    	  } catch (Exception e) {

    		e.printStackTrace();

    	  }

    	}

    }

Output…

    Output from Server ....

    Product created : Product [name=iPad 4, qty=100]

[http://www.mkyong.com/webservices/jax-rs/restful-java-client-with-resteasy-client-framework/](http://www.mkyong.com/webservices/jax-rs/restful-java-client-with-resteasy-client-framework/)
