In JAX-RS, you can use `@QueryParam` annotation to inject URI query parameter into Java method. for example,

    /users/query?url=mkyong.com

In above URI pattern, query parameter is “**url=mkyong.com**“, and you can get the url value with `@QueryParam("url")`.

## 1\. @QueryParam example

See a full example of using `@QueryParam` in JAX-RS.

    import java.util.List;
    import javax.ws.rs.GET;
    import javax.ws.rs.Path;
    import javax.ws.rs.QueryParam;
    import javax.ws.rs.core.Response;

    @Path("/users")
    public class UserService {

    	@GET
    	@Path("/query")
    	public Response getUsers(
    		@QueryParam("from") int from,
    		@QueryParam("to") int to,
    		@QueryParam("orderBy") List<String> orderBy) {

    		return Response
    		   .status(200)
    		   .entity("getUsers is called, from : " + from + ", to : " + to
    			+ ", orderBy" + orderBy.toString()).build();

    	}

    }

URI Pattern : “**users/query?from=100&to=200&orderBy=age&orderBy=name**”

    getUsers is called, from : 100, to : 200, orderBy[age, name]

**Like it ?**  
@QueryParam will convert the query parameter “**orderBy=age&orderBy=name**” into `java.util.List` automatically.

## 2\. Programmatic Query Parameter

Alternatively, you can get the query parameters grammatically, via “`@Context UriInfo`“. See equivalent version below :

    import java.util.List;
    import javax.ws.rs.GET;
    import javax.ws.rs.Path;
    import javax.ws.rs.core.Context;
    import javax.ws.rs.core.Response;
    import javax.ws.rs.core.UriInfo;

    @Path("/users")
    public class UserService {

    	@GET
    	@Path("/query")
    	public Response getUsers(@Context UriInfo info) {

    		String from = info.getQueryParameters().getFirst("from");
    		String to = info.getQueryParameters().getFirst("to");
    		List<String> orderBy = info.getQueryParameters().get("orderBy");

    		return Response
    		   .status(200)
    		   .entity("getUsers is called, from : " + from + ", to : " + to
    			+ ", orderBy" + orderBy.toString()).build();

    	}

    }

URI Pattern : “**users/query?from=100&to=200&orderBy=age&orderBy=name**”

    getUsers is called, from : 100, to : 200, orderBy[age, name]

## 3\. @DefaultValue example

`@DefaultValue` is good for optional parameter.

    import java.util.List;
    import javax.ws.rs.GET;
    import javax.ws.rs.Path;
    import javax.ws.rs.QueryParam;
    import javax.ws.rs.core.Response;

    @Path("/users")
    public class UserService {

    	@GET
    	@Path("/query")
    	public Response getUsers(
    		@DefaultValue("1000") @QueryParam("from") int from,
    		@DefaultValue("999")@QueryParam("to") int to,
    		@DefaultValue("name") @QueryParam("orderBy") List<String> orderBy) {

    		return Response
    		   .status(200)
    		   .entity("getUsers is called, from : " + from + ", to : " + to
    			+ ", orderBy" + orderBy.toString()).build();

    	}

    }

URI Pattern : “**users/query**”

    getUsers is called, from : 1000, to : 999, orderBy[name]

[http://www.mkyong.com/webservices/jax-rs/jax-rs-queryparam-example/](http://www.mkyong.com/webservices/jax-rs/jax-rs-queryparam-example/)
