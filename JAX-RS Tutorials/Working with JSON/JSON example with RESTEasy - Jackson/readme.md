**Jettison as JSON provider**  
You may also interest to read this [RESTEasy + Jettison + JAXB](http://www.mkyong.com/webservices/jax-rs/download-json-from-jax-rs-with-jaxb-resteasy/) example.

Many like **Jackson JSON processor**, and it supported in RESTEasy. In this tutorial, we show you how to convert an object to JSON format and return it back to the client.

## 1\. RESTEasy + Jackson

To integrate Jackson with RESTEasy, you just need to include “**resteasy-jackson-provider.jar**“.

**Note**  
When RESTEasy returned a json output, it will use Jackson provider to convert it automatically. You do not need to code a single line to integrate both.

_File : pom.xml_

    <repositories>
    <repository>
    	<id>JBoss repository</id>
    	<url>https://repository.jboss.org/nexus/content/groups/public-jboss/</url>
    </repository>
      </repositories>

      <dependencies>

    <dependency>
    	<groupId>org.jboss.resteasy</groupId>
    	<artifactId>resteasy-jaxrs</artifactId>
    	<version>2.2.1.GA</version>
    </dependency>

    <dependency>
    	<groupId>org.jboss.resteasy</groupId>
    	<artifactId>resteasy-jackson-provider</artifactId>
    	<version>2.2.1.GA</version>
    </dependency>

      </dependencies>

## 2\. Simple Object

A simple object, later convert it into JSON format.

    package com.mkyong.rest;

    public class Product {

    	String name;
    	int qty;

    	public String getName() {
    		return name;
    	}

    	public void setName(String name) {
    		this.name = name;
    	}

    	public int getQty() {
    		return qty;
    	}

    	public void setQty(int qty) {
    		this.qty = qty;
    	}

    }

## 3\. JAX-RS

Annotate the method with `@Produces("application/json")`. RESTEasy will use Jackson provider to handle the JSON conversion automatically.

    import javax.ws.rs.Consumes;
    import javax.ws.rs.GET;
    import javax.ws.rs.POST;
    import javax.ws.rs.Path;
    import javax.ws.rs.Produces;
    import javax.ws.rs.core.Response;

    @Path("/json/product")
    public class JSONService {

    	@GET
    	@Path("/get")
    	@Produces("application/json")
    	public Product getProductInJSON() {

    		Product product = new Product();
    		product.setName("iPad 3");
    		product.setQty(999);

    		return product;

    	}

    	@POST
    	@Path("/post")
    	@Consumes("application/json")
    	public Response createProductInJSON(Product product) {

    		String result = "Product created : " + product;
    		return Response.status(201).entity(result).build();

    	}

    }

**Disabled RESTEasy auto scanning.**  
You must disabled the RESTEasy auto scanning, and register your REST service manually, otherwise, you will hits this [error](http://www.mkyong.com/webservices/jax-rs/illegal-to-inject-a-message-body-into-a-singleton-into-public-org-codehaus-jackson-jaxrs-jacksonjsonprovider/). Hope it get fix in future release.

_File : web.xml_

    <!-- disabled auto scan
            <context-param>
                 <param-name>resteasy.scan</param-name>
                 <param-value>true</param-value>
    </context-param> -->

    <context-param>
    	<param-name>resteasy.resources</param-name>
    	<param-value>com.mkyong.rest.JSONService</param-value>
    </context-param>

## 4\. Demo

See GET and POST method.

**1\. GET method**  
When URI pattern “**/json/product/get**” is requested, following JSON file will be returned.

    {
    	"qty":999,
    	"name":"iPad 3"
    }

![jackson resteasy demo](http://www.mkyong.com/wp-content/uploads/2011/07/jackson-json-resteasy.png)

**2\. POST method**  
You can “post” the json format string to URI pattern “**/json/product/post**“, it will convert into “Product” automatically.

[http://www.mkyong.com/webservices/jax-rs/integrate-jackson-with-resteasy/](http://www.mkyong.com/webservices/jax-rs/integrate-jackson-with-resteasy/)
