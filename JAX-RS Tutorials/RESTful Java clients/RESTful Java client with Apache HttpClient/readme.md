[Apache HttpClient](http://hc.apache.org/httpcomponents-client-ga/index.html) is a robust and complete solution Java library to perform HTTP operations, including RESTful service. In this tutorial, we show you how to create a RESTful Java client with **Apache HttpClient**, to perform a “**GET**” and “**POST**” request.

**Note**  
The RESTful services from last “[Jackson + JAX-RS](http://www.mkyong.com/webservices/jax-rs/integrate-jackson-with-resteasy/)” article will be reused.

## 1\. Get Apache HttpClient

Apache HttpClient is available in Maven central repository, just declares it in your Maven `pom.xml` file.

_File : pom.xml_

    <dependency>
    	<groupId>org.apache.httpcomponents</groupId>
    	<artifactId>httpclient</artifactId>
    	<version>4.1.1</version>
    </dependency>

## 2\. GET Request

Review last REST service again.

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

Apache HttpClient to send a “GET” request.

    import java.io.BufferedReader;
    import java.io.IOException;
    import java.io.InputStreamReader;
    import org.apache.http.HttpResponse;
    import org.apache.http.client.ClientProtocolException;
    import org.apache.http.client.methods.HttpGet;
    import org.apache.http.impl.client.DefaultHttpClient;

    public class ApacheHttpClientGet {

    	public static void main(String[] args) {
    	  try {

    		DefaultHttpClient httpClient = new DefaultHttpClient();
    		HttpGet getRequest = new HttpGet(
    			"http://localhost:8080/RESTfulExample/json/product/get");
    		getRequest.addHeader("accept", "application/json");

    		HttpResponse response = httpClient.execute(getRequest);

    		if (response.getStatusLine().getStatusCode() != 200) {
    			throw new RuntimeException("Failed : HTTP error code : "
    			   + response.getStatusLine().getStatusCode());
    		}

    		BufferedReader br = new BufferedReader(
                             new InputStreamReader((response.getEntity().getContent())));

    		String output;
    		System.out.println("Output from Server .... \n");
    		while ((output = br.readLine()) != null) {
    			System.out.println(output);
    		}

    		httpClient.getConnectionManager().shutdown();

    	  } catch (ClientProtocolException e) {

    		e.printStackTrace();

    	  } catch (IOException e) {

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

Apache HttpClient to send a “POST” request.

    import java.io.BufferedReader;
    import java.io.IOException;
    import java.io.InputStreamReader;
    import java.net.MalformedURLException;
    import org.apache.http.HttpResponse;
    import org.apache.http.client.methods.HttpPost;
    import org.apache.http.entity.StringEntity;
    import org.apache.http.impl.client.DefaultHttpClient;

    public class ApacheHttpClientPost {

    	public static void main(String[] args) {

    	  try {

    		DefaultHttpClient httpClient = new DefaultHttpClient();
    		HttpPost postRequest = new HttpPost(
    			"http://localhost:8080/RESTfulExample/json/product/post");

    		StringEntity input = new StringEntity("{\"qty\":100,\"name\":\"iPad 4\"}");
    		input.setContentType("application/json");
    		postRequest.setEntity(input);

    		HttpResponse response = httpClient.execute(postRequest);

    		if (response.getStatusLine().getStatusCode() != 201) {
    			throw new RuntimeException("Failed : HTTP error code : "
    				+ response.getStatusLine().getStatusCode());
    		}

    		BufferedReader br = new BufferedReader(
                            new InputStreamReader((response.getEntity().getContent())));

    		String output;
    		System.out.println("Output from Server .... \n");
    		while ((output = br.readLine()) != null) {
    			System.out.println(output);
    		}

    		httpClient.getConnectionManager().shutdown();

    	  } catch (MalformedURLException e) {

    		e.printStackTrace();

    	  } catch (IOException e) {

    		e.printStackTrace();

    	  }

    	}

    }

Output…

    Output from Server ....

    Product created : Product [name=iPad 4, qty=100]

[http://www.mkyong.com/webservices/jax-rs/restful-java-client-with-apache-httpclient/](http://www.mkyong.com/webservices/jax-rs/restful-java-client-with-apache-httpclient/)
