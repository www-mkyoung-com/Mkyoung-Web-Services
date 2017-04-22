Matrix parameters are a set of “**name=value**” in URI path, for example,

    /books/2011;author=mkyong

In above URI, the matrix parameter is “**author=mkyong**“, separate by a semi colon “**;**“.

## 1\. @MatrixParam example

See a full example of using `@MatrixParam` in JAX-RS.

    import javax.ws.rs.GET;
    import javax.ws.rs.MatrixParam;
    import javax.ws.rs.Path;
    import javax.ws.rs.PathParam;
    import javax.ws.rs.core.Response;

    @Path("/books")
    public class BookService {

    	@GET
    	@Path("{year}")
    	public Response getBooks(@PathParam("year") String year,
    			@MatrixParam("author") String author,
    			@MatrixParam("country") String country) {

    		return Response
    			.status(200)
    			.entity("getBooks is called, year : " + year
    				+ ", author : " + author + ", country : " + country)
    			.build();

    	}

    }

See following URI patterns and result.

1\. URI Pattern : “**/books/2011/**”

    getBooks is called, year : 2011, author : null, country : null

2\. URI Pattern : “**/books/2011;author=mkyong**”

    getBooks is called, year : 2011, author : mkyong, country : null

3\. URI Pattern : “**/books/2011;author=mkyong;country=malaysia**”

    getBooks is called, year : 2011, author : mkyong, country : malaysia

4\. URI Pattern : “**/books/2011;country=malaysia;author=mkyong**”

    getBooks is called, year : 2011, author : mkyong, country : malaysia

[http://www.mkyong.com/webservices/jax-rs/jax-rs-matrixparam-example/](http://www.mkyong.com/webservices/jax-rs/jax-rs-matrixparam-example/)
