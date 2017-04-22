Not many complete file upload example in JAX-RS, especially **RESTEasy**. Here, we show you two complete RESTEasy examples to handle file upload from HTML form.

1.  Normal way to handle uploaded file via `MultipartFormDataInput`
2.  Map uploaded file to a POJO class via `@MultipartForm`

## 1\. RESTEasy Multipart Dependency

In RESTEasy, you need “**resteasy-multipart-provider.jar**” to handle multipart file upload.

_File : pom.xml_

    <dependency>
    	<groupId>org.jboss.resteasy</groupId>
    	<artifactId>resteasy-jaxrs</artifactId>
    	<version>2.2.1.GA</version>
    </dependency>

    <dependency>
    	<groupId>org.jboss.resteasy</groupId>
    	<artifactId>resteasy-multipart-provider</artifactId>
    	<version>2.2.0.GA</version>
    </dependency>

    <!-- optional, good for handle I/O task -->
    <dependency>
    	<groupId>commons-io</groupId>
    	<artifactId>commons-io</artifactId>
    	<version>2.0.1</version>
    </dependency>

## 2\. File Upload HTML Form

Simple HTML form to upload file.

    <html>
    <body>
    	<h1>JAX-RS Upload Form</h1>

    	<form action="rest/file/upload" method="post" enctype="multipart/form-data">

    	   <p>
    		Select a file : <input type="file" name="uploadedFile" size="50" />
    	   </p>

    	   <input type="submit" value="Upload It" />
    	</form>

    </body>
    </html>

## 3.1 MultipartFormDataInput example

First example, the uploaded file will map to “**MultipartFormDataInput**” automatically.

    import java.io.File;
    import java.io.FileOutputStream;
    import java.io.IOException;
    import java.io.InputStream;
    import java.util.List;
    import java.util.Map;
    import javax.ws.rs.Consumes;
    import javax.ws.rs.POST;
    import javax.ws.rs.Path;
    import javax.ws.rs.core.MultivaluedMap;
    import javax.ws.rs.core.Response;
    import org.apache.commons.io.IOUtils;
    import org.jboss.resteasy.plugins.providers.multipart.InputPart;
    import org.jboss.resteasy.plugins.providers.multipart.MultipartFormDataInput;

    @Path("/file")
    public class UploadFileService {

    	private final String UPLOADED_FILE_PATH = "d:\\";

    	@POST
    	@Path("/upload")
    	@Consumes("multipart/form-data")
    	public Response uploadFile(MultipartFormDataInput input) {

    		String fileName = "";

    		Map<String, List<InputPart>> uploadForm = input.getFormDataMap();
    		List<InputPart> inputParts = uploadForm.get("uploadedFile");

    		for (InputPart inputPart : inputParts) {

    		 try {

    			MultivaluedMap<String, String> header = inputPart.getHeaders();
    			fileName = getFileName(header);

    			//convert the uploaded file to inputstream
    			InputStream inputStream = inputPart.getBody(InputStream.class,null);

    			byte [] bytes = IOUtils.toByteArray(inputStream);

    			//constructs upload file path
    			fileName = UPLOADED_FILE_PATH + fileName;

    			writeFile(bytes,fileName);

    			System.out.println("Done");

    		  } catch (IOException e) {
    			e.printStackTrace();
    		  }

    		}

    		return Response.status(200)
    		    .entity("uploadFile is called, Uploaded file name : " + fileName).build();

    	}

    	/**
    	 * header sample
    	 * {
    	 * 	Content-Type=[image/png],
    	 * 	Content-Disposition=[form-data; name="file"; filename="filename.extension"]
    	 * }
    	 **/
    	//get uploaded filename, is there a easy way in RESTEasy?
    	private String getFileName(MultivaluedMap<String, String> header) {

    		String[] contentDisposition = header.getFirst("Content-Disposition").split(";");

    		for (String filename : contentDisposition) {
    			if ((filename.trim().startsWith("filename"))) {

    				String[] name = filename.split("=");

    				String finalFileName = name[1].trim().replaceAll("\"", "");
    				return finalFileName;
    			}
    		}
    		return "unknown";
    	}

    	//save to somewhere
    	private void writeFile(byte[] content, String filename) throws IOException {

    		File file = new File(filename);

    		if (!file.exists()) {
    			file.createNewFile();
    		}

    		FileOutputStream fop = new FileOutputStream(file);

    		fop.write(content);
    		fop.flush();
    		fop.close();

    	}
    }

In this example, if you select a file “**c:\\abc.png**” from your local C drive, click on the submit button, then it will uploaded to “**d:\\abc.png**“.

## 3.2 MultipartForm example

This example will map the uploaded file to a POJO class, you dun need to handle the `inputPart` like 3.1 example.

_POJO file, map uploaded file to this class._

    import javax.ws.rs.FormParam;
    import org.jboss.resteasy.annotations.providers.multipart.PartType;

    public class FileUploadForm {

    	public FileUploadForm() {
    	}

    	private byte[] data;

    	public byte[] getData() {
    		return data;
    	}

    	@FormParam("uploadedFile")
    	@PartType("application/octet-stream")
    	public void setData(byte[] data) {
    		this.data = data;
    	}

    }

_Handle the uploaded file._

    import java.io.File;
    import java.io.FileOutputStream;
    import java.io.IOException;
    import javax.ws.rs.Consumes;
    import javax.ws.rs.POST;
    import javax.ws.rs.Path;
    import javax.ws.rs.core.Response;
    import org.jboss.resteasy.annotations.providers.multipart.MultipartForm;

    @Path("/file")
    public class UploadFileService {

    	@POST
    	@Path("/upload")
    	@Consumes("multipart/form-data")
    	public Response uploadFile(@MultipartForm FileUploadForm form) {

    		String fileName = "d:\\anything";

    		try {
    			writeFile(form.getData(), fileName);
    		} catch (IOException e) {

    			e.printStackTrace();
    		}

    		System.out.println("Done");

    		return Response.status(200)
    		    .entity("uploadFile is called, Uploaded file name : " + fileName).build();

    	}

    	// save to somewhere
    	private void writeFile(byte[] content, String filename) throws IOException {

    		File file = new File(filename);

    		if (!file.exists()) {
    			file.createNewFile();
    		}

    		FileOutputStream fop = new FileOutputStream(file);

    		fop.write(content);
    		fop.flush();
    		fop.close();

    	}
    }

The problem is, you can’t get the uploaded filename! To fix it, you can put a textbox in HTML form, so that user is able to key in the uploaded filename, and map it to “**FileUploadForm**” via `@FormParam("filename")`.

## Conclusion

Overall, RESTEasy is able to handle multipart well, but it’s rather hard to get the uploaded file header information, like filename. Or, may be i do not know how to use the RESTEasy API?

**Note**  
Please comment or correct me if you have a more elegant way to get the uploaded filename.

## References

1.  [How do I do a multipart/form file upload with jax-rs?](http://stackoverflow.com/questions/2637017/how-do-i-do-a-multipart-form-file-upload-with-jax-rs)
2.  [Resteasy multipart/data-form file upload on GAE](http://stackoverflow.com/questions/4875780/resteasy-multipart-data-form-file-upload-on-gae)
