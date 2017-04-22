In JAX-RS, you can use `@PathParem` to inject the value of URI parameter that defined in `@Path` expression, into Java method.

## 1\. @PathParam – Single Parameter

A simple and normal way to use `@PathParam`.

    import javax.ws.rs.GET;
    import javax.ws.rs.Path;
    import javax.ws.rs.PathParam;
    import javax.ws.rs.core.Response;

    @Path("/users")
    public class UserRestService {

    	@GET
    	@Path("{id}")
    	public Response getUserById(@PathParam("id") String id) {

    	   return Response.status(200).entity("getUserById is called, id : " + id).build();

    	}

    }

In above example, the value of **{id}** from “**/users/{id}**” will match to “**@PathParam(“id”) String var**“.

URI Pattern : “**/users/22667788**”

    getUserById is called, id : 22667788

## 2\. @PathParam – Multiple Parameters

Example to inject multiple parameters into Java method.

    import javax.ws.rs.GET;
    import javax.ws.rs.Path;
    import javax.ws.rs.PathParam;
    import javax.ws.rs.core.Response;

    @Path("/users")
    public class UserRestService {

    	@GET
    	@Path("{year}/{month}/{day}")
    	public Response getUserHistory(
    			@PathParam("year") int year,
    			@PathParam("month") int month,
    			@PathParam("day") int day) {

    	   String date = year + "/" + month + "/" + day;

    	   return Response.status(200)
    		.entity("getUserHistory is called, year/month/day : " + date)
    		.build();

    	}

    }

URI Pattern : “**/users/2011/06/30**”

    getUserHistory is called, year/month/day : 2011/6/30

[http://www.mkyong.com/webservices/jax-rs/jax-rs-pathparam-example/](http://www.mkyong.com/webservices/jax-rs/jax-rs-pathparam-example/)
