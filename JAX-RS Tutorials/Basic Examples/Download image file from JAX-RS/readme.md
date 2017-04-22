In JAX-RS, for user to download an image file, annotate the method with `@Produces("image/image-type")` :

1.  Put **@Produces(“image/png”)** on service method, for “png” image.
2.  Set “**Content-Disposition**” in Response header to prompt a download box.

**Note**  
For other image types, refer to this [list of the image types](http://en.wikipedia.org/wiki/Internet_media_type#Type_image)

## 1\. Download Image in JAX-RS

Full example to download an image file from JAX-RS.

    import java.io.File;
    import javax.ws.rs.GET;
    import javax.ws.rs.Path;
    import javax.ws.rs.Produces;
    import javax.ws.rs.core.Response;
    import javax.ws.rs.core.Response.ResponseBuilder;

    @Path("/image")
    public class ImageService {

    	private static final String FILE_PATH = "c:\\mkyong-logo.png";

    	@GET
    	@Path("/get")
    	@Produces("image/png")
    	public Response getFile() {

    		File file = new File(FILE_PATH);

    		ResponseBuilder response = Response.ok((Object) file);
    		response.header("Content-Disposition",
    			"attachment; filename=image_from_server.png");
    		return response.build();

    	}

    }

## 2\. Demo

Access this URI pattern : “**/image/get**“.

_Figure : Image file “**c:\\mkyong-logo.png**” from server is prompted for user to download, with a new file name “**image_from_server.png**“_

![download image from server](http://www.mkyong.com/wp-content/uploads/2011/07/download-image-jax-rs.png)

[http://www.mkyong.com/webservices/jax-rs/download-image-file-from-jax-rs/](http://www.mkyong.com/webservices/jax-rs/download-image-file-from-jax-rs/)
