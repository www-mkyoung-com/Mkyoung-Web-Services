**Note**  
Tested with Eclipse 3.6, Maven 3 and RESTEasy 2.2.1.GA

## Question

Developing file upload feature with RESTEasy, see following **RESTEasy multipart file upload** example :

    public class FileUploadForm {

    	private byte[] data;

    	@FormParam("file")
    	public void setData(byte[] data) {
    		this.data = data;
    	}

    	//...code omitted
    }

    @Path("/file")
    public class UploadFileService {

    	@POST
    	@Path("/upload")
    	@Consumes("multipart/form-data")
    	public Response uploadFile(@MultipartForm FileUploadForm form) {
    		//...code omitted
    	}

    }

Above file upload example is able to compile without any error. However, it prompts following scary error message during when upload file to the deployed service.

![resteasy error message](http://www.mkyong.com/wp-content/uploads/2011/07/resteasy-upload-error-message.png)

    HTTP Status 500 - Bad arguments passed to public javax.ws.rs.core.Response
    com.mkyong.rest.UploadFileService.uploadFile(com.mkyong.rest.FileUploadForm)
    ( org.jboss.resteasy.spi.BadRequestException org.jboss.resteasy.spi.BadRequestException:
    Could not find message body reader for type:
    class com.mkyong.rest.FileUploadForm of content type: multipart/form-data;
    boundary="---------------------------98942870323811" )

## Solution

RESTEasy is unable to find message body reader for “**multipart/form-data**“, it should be included in “**resteasy-multipart-provider.jar**“.

To fix it, make sure follow 2 steps are checked.

## 1\. Declared resteasy-multipart-provider

Make sure “**resteasy-multipart-provider.jar**” is declared. See Maven example :

_File : pom.xml_

    <dependency>
    	<groupId>org.jboss.resteasy</groupId>
    	<artifactId>resteasy-multipart-provider</artifactId>
    	<version>2.2.0.GA</version>
    </dependency>

## 2\. mvn eclipse:eclipse -Dwtpversion=2.0

For web application project in Eclipse, you need to use following command to make sure all dependencies are deployed correctly.

    mvn eclipse:eclipse -Dwtpversion=2.0

After that, check your Eclipse “**Web Deployment Assembly**“, make sure all required dependencies are included.

**Remember -Dwtpversion=2.0**  
In Eclipse (web project), the classic “`mvn eclipse:eclipse`” is unable to get all dependencies deployed, instead, you should use “`mvn eclipse:eclipse -Dwtpversion=2.0`“.

[http://www.mkyong.com/webservices/jax-rs/resteasy-could-not-find-message-body-reader-for-type-multipartform-data/](http://www.mkyong.com/webservices/jax-rs/resteasy-could-not-find-message-body-reader-for-type-multipartform-data/)
