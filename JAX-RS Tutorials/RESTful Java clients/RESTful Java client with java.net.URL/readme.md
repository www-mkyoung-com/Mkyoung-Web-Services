In this tutorial, we show you how to create a RESTful Java client with Java build-in **HTTP client library**. It’s simple to use and good enough to perform basic operations for REST service.

The RESTful services from last “[Jackson + JAX-RS](http://www.mkyong.com/webservices/jax-rs/integrate-jackson-with-resteasy/)” article will be reused, and we will use “`java.net.URL`” and “`java.net.HttpURLConnection`” to create a simple Java client to send “**GET**” and “**POST**” request.

## 1\. GET Request

Review last REST service, return “json” data back to client.

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

Java client to send a “GET” request.

    import java.io.BufferedReader;
    import java.io.IOException;
    import java.io.InputStreamReader;
    import java.net.HttpURLConnection;
    import java.net.MalformedURLException;
    import java.net.URL;

    public class NetClientGet {

    	// http://localhost:8080/RESTfulExample/json/product/get
    	public static void main(String[] args) {

    	  try {

    		URL url = new URL("http://localhost:8080/RESTfulExample/json/product/get");
    		HttpURLConnection conn = (HttpURLConnection) url.openConnection();
    		conn.setRequestMethod("GET");
    		conn.setRequestProperty("Accept", "application/json");

    		if (conn.getResponseCode() != 200) {
    			throw new RuntimeException("Failed : HTTP error code : "
    					+ conn.getResponseCode());
    		}

    		BufferedReader br = new BufferedReader(new InputStreamReader(
    			(conn.getInputStream())));

    		String output;
    		System.out.println("Output from Server .... \n");
    		while ((output = br.readLine()) != null) {
    			System.out.println(output);
    		}

    		conn.disconnect();

    	  } catch (MalformedURLException e) {

    		e.printStackTrace();

    	  } catch (IOException e) {

    		e.printStackTrace();

    	  }

    	}

    }

Output…

    Output from Server ....

    {"qty":999,"name":"iPad 3"}

## 2\. POST Request

Review last REST service, accept “json” data and convert it into Product object, via Jackson provider automatically.

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

Java client to send a “POST” request, with json string.

    import java.io.BufferedReader;
    import java.io.IOException;
    import java.io.InputStreamReader;
    import java.io.OutputStream;
    import java.net.HttpURLConnection;
    import java.net.MalformedURLException;
    import java.net.URL;

    public class NetClientPost {

    	// http://localhost:8080/RESTfulExample/json/product/post
    	public static void main(String[] args) {

    	  try {

    		URL url = new URL("http://localhost:8080/RESTfulExample/json/product/post");
    		HttpURLConnection conn = (HttpURLConnection) url.openConnection();
    		conn.setDoOutput(true);
    		conn.setRequestMethod("POST");
    		conn.setRequestProperty("Content-Type", "application/json");

    		String input = "{\"qty\":100,\"name\":\"iPad 4\"}";

    		OutputStream os = conn.getOutputStream();
    		os.write(input.getBytes());
    		os.flush();

    		if (conn.getResponseCode() != HttpURLConnection.HTTP_CREATED) {
    			throw new RuntimeException("Failed : HTTP error code : "
    				+ conn.getResponseCode());
    		}

    		BufferedReader br = new BufferedReader(new InputStreamReader(
    				(conn.getInputStream())));

    		String output;
    		System.out.println("Output from Server .... \n");
    		while ((output = br.readLine()) != null) {
    			System.out.println(output);
    		}

    		conn.disconnect();

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

[http://www.mkyong.com/webservices/jax-rs/restfull-java-client-with-java-net-url/](http://www.mkyong.com/webservices/jax-rs/restfull-java-client-with-java-net-url/)
