In this tutorial, we show you how do to file upload with Jersey, JAX-RS implementation.

## 1\. Jersey Multipart Dependency

To support multipart (file upload) in Jersey, you just need to include “**jersey-multipart.jar**” in Maven `pom.xml` file.

    <project ...>

    	<repositories>
    		<repository>
    			<id>maven2-repository.java.net</id>
    			<name>Java.net Repository for Maven</name>
    			<url>http://download.java.net/maven/2/</url>
    			<layout>default</layout>
    		</repository>
    	</repositories>

    	<dependencies>

    		<dependency>
    			<groupId>com.sun.jersey</groupId>
    			<artifactId>jersey-server</artifactId>
    			<version>1.8</version>
    		</dependency>

    		<dependency>
    			<groupId>com.sun.jersey.contribs</groupId>
    			<artifactId>jersey-multipart</artifactId>
    			<version>1.8</version>
    		</dependency>

    	</dependencies>

    </project>

## 2\. File Upload HTML Form

Simple HTML form to select and upload a file.

    <html>
    <body>
    	<h1>File Upload with Jersey</h1>

    	<form action="rest/file/upload" method="post" enctype="multipart/form-data">

    	   <p>
    		Select a file : <input type="file" name="file" size="45" />
    	   </p>

    	   <input type="submit" value="Upload It" />
    	</form>

    </body>
    </html>

## 3\. Upload Service with Jersey

In Jersey, use `@FormDataParam` to receive the uploaded file. To get the uploaded file name or header detail, match it to “`FormDataContentDisposition`“.

    import java.io.File;
    import java.io.FileOutputStream;
    import java.io.IOException;
    import java.io.InputStream;
    import java.io.OutputStream;
    import javax.ws.rs.Consumes;
    import javax.ws.rs.POST;
    import javax.ws.rs.Path;
    import javax.ws.rs.core.MediaType;
    import javax.ws.rs.core.Response;
    import com.sun.jersey.core.header.FormDataContentDisposition;
    import com.sun.jersey.multipart.FormDataParam;

    @Path("/file")
    public class UploadFileService {

    	@POST
    	@Path("/upload")
    	@Consumes(MediaType.MULTIPART_FORM_DATA)
    	public Response uploadFile(
    		@FormDataParam("file") InputStream uploadedInputStream,
    		@FormDataParam("file") FormDataContentDisposition fileDetail) {

    		String uploadedFileLocation = "d://uploaded/" + fileDetail.getFileName();

    		// save it
    		writeToFile(uploadedInputStream, uploadedFileLocation);

    		String output = "File uploaded to : " + uploadedFileLocation;

    		return Response.status(200).entity(output).build();

    	}

    	// save uploaded file to new location
    	private void writeToFile(InputStream uploadedInputStream,
    		String uploadedFileLocation) {

    		try {
    			OutputStream out = new FileOutputStream(new File(
    					uploadedFileLocation));
    			int read = 0;
    			byte[] bytes = new byte[1024];

    			out = new FileOutputStream(new File(uploadedFileLocation));
    			while ((read = uploadedInputStream.read(bytes)) != -1) {
    				out.write(bytes, 0, read);
    			}
    			out.flush();
    			out.close();
    		} catch (IOException e) {

    			e.printStackTrace();
    		}

    	}

    }

## 4\. Demo

Select a file and click on the upload button, the selected file is uploaded to a pre-defined location.

URL : _http://localhost:8080/RESTfulExample/FileUpload.html_

![file upload demo 1](http://www.mkyong.com/wp-content/uploads/2011/07/jersey-file-upload.png)

URL : _http://localhost:8080/RESTfulExample/rest/file/upload_

![file upload in demo 2](http://www.mkyong.com/wp-content/uploads/2011/07/jersey-file-upload-1.png)

[http://www.mkyong.com/webservices/jax-rs/file-upload-example-in-jersey/](http://www.mkyong.com/webservices/jax-rs/file-upload-example-in-jersey/)
