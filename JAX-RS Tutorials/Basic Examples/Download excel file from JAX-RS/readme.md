to-markdownSource on GitHub
 GitHub Flavored Markdown
HTML

<p>In JAX-RS, for excel file, annotate the method with&nbsp;<code>@Produces(&quot;application/vnd.ms-excel&quot;)</code>&nbsp;:</p>

<ol>
	<li>Put&nbsp;<strong>@Produces(&ldquo;application/vnd.ms-excel&rdquo;)</strong>&nbsp;on service method.</li>
	<li>Set &ldquo;<strong>Content-Disposition</strong>&rdquo; in Response header to prompt a download box.</li>
</ol>

<h2>1. Download Excel file in JAX-RS</h2>

<p>Full example to download an excel file from JAX-RS.</p>

<pre>
<code>import java.io.File;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.ResponseBuilder;

@Path("/excel")
public class ExcelService {

	private static final String FILE_PATH = "c:\\excel-file.xls";

	@GET
	@Path("/get")
	@Produces("application/vnd.ms-excel")
	public Response getFile() {

		File file = new File(FILE_PATH);

		ResponseBuilder response = Response.ok((Object) file);
		response.header("Content-Disposition",
			"attachment; filename=new-excel-file.xls");
		return response.build();

	}

}</code></pre>

<p>&nbsp;</p>

<h2>2. Demo</h2>

<p>Access this URI pattern : &ldquo;<strong>/excel/get</strong>&ldquo;.</p>

<p><em>Figure : Excel file &ldquo;<strong>c:\\excel-file.xls</strong>&rdquo; from server is prompted for user to download, with a new file name &ldquo;<strong>new-excel-file.xls</strong>&ldquo;</em></p>

<p><img alt="download excel file from server" height="353" src="http://www.mkyong.com/wp-content/uploads/2011/07/download-excel-jax-rs.png" width="463" /></p>
<p><a href="http://www.mkyong.com/webservices/jax-rs/download-excel-file-from-jax-rs/">http://www.mkyong.com/webservices/jax-rs/download-excel-file-from-jax-rs/</a></p>

Markdown

In JAX-RS, for excel file, annotate the method with `@Produces("application/vnd.ms-excel")` :

1.  Put **@Produces(“application/vnd.ms-excel”)** on service method.
2.  Set “**Content-Disposition**” in Response header to prompt a download box.

## 1\. Download Excel file in JAX-RS

Full example to download an excel file from JAX-RS.

    import java.io.File;
    import javax.ws.rs.GET;
    import javax.ws.rs.Path;
    import javax.ws.rs.Produces;
    import javax.ws.rs.core.Response;
    import javax.ws.rs.core.Response.ResponseBuilder;

    @Path("/excel")
    public class ExcelService {

    	private static final String FILE_PATH = "c:\\excel-file.xls";

    	@GET
    	@Path("/get")
    	@Produces("application/vnd.ms-excel")
    	public Response getFile() {

    		File file = new File(FILE_PATH);

    		ResponseBuilder response = Response.ok((Object) file);
    		response.header("Content-Disposition",
    			"attachment; filename=new-excel-file.xls");
    		return response.build();

    	}

    }

## 2\. Demo

Access this URI pattern : “**/excel/get**“.

_Figure : Excel file “**c:\\excel-file.xls**” from server is prompted for user to download, with a new file name “**new-excel-file.xls**“_

![download excel file from server](http://www.mkyong.com/wp-content/uploads/2011/07/download-excel-jax-rs.png)

[http://www.mkyong.com/webservices/jax-rs/download-excel-file-from-jax-rs/](http://www.mkyong.com/webservices/jax-rs/download-excel-file-from-jax-rs/)
to-markdown is copyright © 2011-15 Dom Christie and is released under the MIT licence.
