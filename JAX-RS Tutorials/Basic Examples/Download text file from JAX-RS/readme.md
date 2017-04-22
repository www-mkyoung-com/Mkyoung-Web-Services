In JAX-RS, for user to download a file, annotate the method with `@Produces("text/plain")` :

1.  Put **@Produces(“text/plain”)** on service method, with a `Response` return type. It means the output is a text file.
2.  Set “**Content-Disposition**” in Response header to tell browser pop up a download box for user to download.

## 1\. Download File in JAX-RS

See a full example to download a text file in JAX-RS.

    import java.io.File;
    import javax.ws.rs.GET;
    import javax.ws.rs.Path;
    import javax.ws.rs.Produces;
    import javax.ws.rs.core.Response;
    import javax.ws.rs.core.Response.ResponseBuilder;

    @Path("/file")
    public class FileService {

    	private static final String FILE_PATH = "c:\\file.log";

    	@GET
    	@Path("/get")
    	@Produces("text/plain")
    	public Response getFile() {

    		File file = new File(FILE_PATH);

    		ResponseBuilder response = Response.ok((Object) file);
    		response.header("Content-Disposition",
    			"attachment; filename=\"file_from_server.log\"");
    		return response.build();

    	}

    }

## 2\. Demo

Deploy above JAX-RS service, access this URI pattern : “**/file/get**“.

_Figure : Text file “**c:\\test.log**” from server is prompt for user to download, with a new file name “**file_from_server.log**“_

![download file from server](http://www.mkyong.com/wp-content/uploads/2011/07/download-file-jax-rs.png)

[http://www.mkyong.com/webservices/jax-rs/download-text-file-from-jax-rs/](http://www.mkyong.com/webservices/jax-rs/download-text-file-from-jax-rs/)
