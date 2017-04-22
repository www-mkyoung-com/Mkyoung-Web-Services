In this tutorial, we show you two ways to get HTTP request header in JAX-RS :

1.  Inject directly with `@HeaderParam`
2.  Pragmatically via `@Context`

**Note**  
Refer to this wiki page for [list of the HTTP header fields](http://en.wikipedia.org/wiki/List_of_HTTP_header_fields).

## 1\. @HeaderParam Example

In this example, it gets the browser “**user-agent**” from request header.

    import javax.ws.rs.GET;
    import javax.ws.rs.HeaderParam;
    import javax.ws.rs.Path;
    import javax.ws.rs.core.Response;

    @Path("/users")
    public class UserService {

    	@GET
    	@Path("/get")
    	public Response addUser(@HeaderParam("user-agent") String userAgent) {

    		return Response.status(200)
    			.entity("addUser is called, userAgent : " + userAgent)
    			.build();

    	}

    }

Access via URI pattern “**/users/get**“, with FireFox, see following result :

    addUser is called, userAgent : Mozilla/5.0 (Windows NT 6.1; rv:5.0) Gecko/20100101 Firefox/5.0

## 2\. @Context Example

Alternatively, you can use `@Context` to get “`javax.ws.rs.core.HttpHeaders`” directly, see equivalent version to get browser “**user-agent**“.

    import javax.ws.rs.GET;
    import javax.ws.rs.Path;
    import javax.ws.rs.core.Context;
    import javax.ws.rs.core.HttpHeaders;
    import javax.ws.rs.core.Response;

    @Path("/users")
    public class UserService {

    	@GET
    	@Path("/get")
    	public Response addUser(@Context HttpHeaders headers) {

    		String userAgent = headers.getRequestHeader("user-agent").get(0);

    		return Response.status(200)
    			.entity("addUser is called, userAgent : " + userAgent)
    			.build();

    	}

    }

Access via URI pattern “**/users/get**“, with Google Chrome, see following result :

    addUser is called, userAgent : Mozilla/5.0 (Windows NT 6.1) AppleWebKit/534.30
    	(KHTML, like Gecko) Chrome/12.0.742.112 Safari/534.30

**List all request headers **  
You can list all available HTTP request headers via following code :

    for(String header : headers.getRequestHeaders().keySet()){
    	System.out.println(header);
    }

[http://www.mkyong.com/webservices/jax-rs/get-http-header-in-jax-rs/](http://www.mkyong.com/webservices/jax-rs/get-http-header-in-jax-rs/)
