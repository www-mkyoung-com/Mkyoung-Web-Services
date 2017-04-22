In JAX-RS, you can use `@Path` to bind URI pattern to a Java method. See following examples to show you how it works.

## 1\. Normal URI Matching

See normal URI matching with @Path annotation.

    import javax.ws.rs.GET;
    import javax.ws.rs.Path;
    import javax.ws.rs.core.Response;

    @Path("/users")
    public class UserRestService {

    	@GET
    	public Response getUser() {

    		return Response.status(200).entity("getUser is called").build();

    	}

    	@GET
    	@Path("/vip")
    	public Response getUserVIP() {

    		return Response.status(200).entity("getUserVIP is called").build();

    	}
    }

URI pattern : “**/users**”

    getUser is called

URI pattern : “**/users/vip**”

    getUserVIP is called

## 2\. URI Matching and Parameter

The value within an open brace “{” and close brace “}”, is represents a parameter, and can be access with `@PathParam`.

    import javax.ws.rs.GET;
    import javax.ws.rs.Path;
    import javax.ws.rs.PathParam;
    import javax.ws.rs.core.Response;

    @Path("/users")
    public class UserRestService {

    	@GET
    	@Path("{name}")
    	public Response getUserByName(@PathParam("name") String name) {

    		return Response.status(200)
    			.entity("getUserByName is called, name : " + name).build();

    	}

    }

URI Pattern : “**/users/mkyong**”

    getUserByName is called, name : mkyong

URI Pattern : “**/users/abcdefg**”

    getUserByName is called, name : abcdefg

## 3\. URI Matching and Regular Expression

`@Path` support complex URI matching with regular expression, via following expression :

    {" variable-name [ ":" regular-expression ] "}

    import javax.ws.rs.GET;
    import javax.ws.rs.Path;
    import javax.ws.rs.PathParam;
    import javax.ws.rs.core.Response;

    @Path("/users")
    public class UserRestService {

    	@GET
    	@Path("{id : \\d+}") //support digit only
    	public Response getUserById(@PathParam("id") String id) {

    	   return Response.status(200).entity("getUserById is called, id : " + id).build();

    	}

    	@GET
    	@Path("/username/{username : [a-zA-Z][a-zA-Z_0-9]}")
    	public Response getUserByUserName(@PathParam("username") String username) {

    	   return Response.status(200)
    		.entity("getUserByUserName is called, username : " + username).build();

    	}

    	@GET
    	@Path("/books/{isbn : \\d+}")
    	public Response getUserBookByISBN(@PathParam("isbn") String isbn) {

    	   return Response.status(200)
    		.entity("getUserBookByISBN is called, isbn : " + isbn).build();

    	}

    }

URI Pattern : “**/users/999**”

    getUserById is called, id : 999

URI Pattern : “**/users/123456**”

    getUserById is called, id : 123456

URI Pattern : “**/users/username/aaa**” , failed, don’t match “[a-zA-Z][a-zA-Z_0-9]”, first character need “[a-zA-Z]”, second character need “[a-zA-Z_0-9]”.

    Could not find resource for relative : /users/username/aaa

URI Pattern : “**/users/username/a9**”

    getUserByUserName is called, username : a9

URI Pattern : “**users/books/999**”

    getUserBookByISBN is called, isbn : 999

[http://www.mkyong.com/webservices/jax-rs/jax-rs-path-uri-matching-example/](http://www.mkyong.com/webservices/jax-rs/jax-rs-path-uri-matching-example/)
