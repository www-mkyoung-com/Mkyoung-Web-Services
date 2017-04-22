In JAX-RS, for pdf file, annotate the method with `@Produces("application/pdf")` :

1.  Put **@Produces(“application/pdf”)** on service method.
2.  Set “**Content-Disposition**” in Response header to prompt a download box.

## 1\. Download Pdf file in JAX-RS

Full example to download a pdf file from JAX-RS.

    import java.io.File;
    import javax.ws.rs.GET;
    import javax.ws.rs.Path;
    import javax.ws.rs.Produces;
    import javax.ws.rs.core.Response;
    import javax.ws.rs.core.Response.ResponseBuilder;

    @Path("/pdf")
    public class PdfService {

    	private static final String FILE_PATH = "c:\\Android-Book.pdf";

    	@GET
    	@Path("/get")
    	@Produces("application/pdf")
    	public Response getFile() {

    		File file = new File(FILE_PATH);

    		ResponseBuilder response = Response.ok((Object) file);
    		response.header("Content-Disposition",
    				"attachment; filename=new-android-book.pdf");
    		return response.build();

    	}

    }

## 2\. Demo

Access this URI pattern : “**/pdf/get**“.

_Figure : Pdf file “**c:\\Android-Book.pdf**” from server is prompted for user to download, with a new file name “**new-android-book.pdf**“_

![download pdf file from server](http://www.mkyong.com/wp-content/uploads/2011/07/download-pdf-jax-rs.png)

[http://www.mkyong.com/webservices/jax-rs/download-pdf-file-from-jax-rs/](http://www.mkyong.com/webservices/jax-rs/download-pdf-file-from-jax-rs/)
