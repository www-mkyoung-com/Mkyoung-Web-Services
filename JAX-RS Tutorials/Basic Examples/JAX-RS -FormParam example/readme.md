In JAX-RS, you can use `@FormParam` annotation to bind HTML form parameters value to a Java method. The following example show you how to do it :

## 1\. HTML Form

See a simple HTML form with “**post**” method.

    <html>
    <body>
    	<h1>JAX-RS @FormQuery Testing</h1>

    	<form action="rest/user/add" method="post">
    		<p>
    			Name : <input type="text" name="name" />
    		</p>
    		<p>
    			Age : <input type="text" name="age" />
    		</p>
    		<input type="submit" value="Add User" />
    	</form>

    </body>
    </html>

## 2\. @FormParam Example

Example to use `@FormParam` to get above HTML form parameter values.

    import javax.ws.rs.FormParam;
    import javax.ws.rs.POST;
    import javax.ws.rs.Path;
    import javax.ws.rs.core.Response;

    @Path("/user")
    public class UserService {

    	@POST
    	@Path("/add")
    	public Response addUser(
    		@FormParam("name") String name,
    		@FormParam("age") int age) {

    		return Response.status(200)
    			.entity("addUser is called, name : " + name + ", age : " + age)
    			.build();

    	}

    }

## 3\. Demo

Access HTML Page. URL : _http://localhost:8080/RESTfulExample/UserForm.html_

![demo html page](http://www.mkyong.com/wp-content/uploads/2011/07/formparam-demo1.png)

When “add user” button is clicked, it will redirect to URL : _http://localhost:8080/RESTfulExample/rest/user/add_

![demo jax-rs result](http://www.mkyong.com/wp-content/uploads/2011/07/formparam-demo2.png)

and display the following output :

    addUser is called, name : mkyong 123, age : 12

[http://www.mkyong.com/webservices/jax-rs/jax-rs-formparam-example/](http://www.mkyong.com/webservices/jax-rs/jax-rs-formparam-example/)
