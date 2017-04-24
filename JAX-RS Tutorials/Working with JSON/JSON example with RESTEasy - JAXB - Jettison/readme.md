RESTEasy uses [Jettison JSON library](http://jettison.codehaus.org/) to map JAXB annotation object to and from JSON. In this tutorial, we show you how to convert an JAXB annotated object into JSON format and return it back to client.

**Jackson as JSON provider**  
You may also interest to read this [RESTEasy + Jackson](http://www.mkyong.com/webservices/jax-rs/integrate-jackson-with-resteasy/) example.

## 1\. RESTEasy + JAXB + Jettison

To use **JSON in RESTEasy**, you need following dependencies.

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
    	<artifactId>resteasy-jaxb-provider</artifactId>
    	<version>2.2.0.GA</version>
    </dependency>

    <dependency>
    	<groupId>org.jboss.resteasy</groupId>
    	<artifactId>resteasy-jettison-provider</artifactId>
    	<version>2.2.0.GA</version>
    </dependency>

      </dependencies>

## 2\. JAXB XML Provider

Create an object, annotate with JAXB. Why using XML provider? No worry, later you will use `@BadgerFish` to convert it into JSON format.

    package com.mkyong.rest;

    import javax.xml.bind.annotation.XmlAttribute;
    import javax.xml.bind.annotation.XmlElement;
    import javax.xml.bind.annotation.XmlRootElement;

    @XmlRootElement(name = "movie")
    public class Movie {

    	String name;
    	String director;
    	int year;

    	@XmlElement
    	public String getName() {
    		return name;
    	}

    	public void setName(String name) {
    		this.name = name;
    	}

    	@XmlElement
    	public String getDirector() {
    		return director;
    	}

    	public void setDirector(String director) {
    		this.director = director;
    	}

    	@XmlAttribute
    	public int getYear() {
    		return year;
    	}

    	public void setYear(int year) {
    		this.year = year;
    	}

    }

## 3\. JAX-RS

To return a JSON file format, annotate the service method with `@BadgerFish` and `@Produces("application/json")`.

RESTEasy will handle the JSON conversion automatically.

    import javax.ws.rs.GET;
    import javax.ws.rs.Path;
    import javax.ws.rs.Produces;
    import org.jboss.resteasy.annotations.providers.jaxb.json.BadgerFish;

    @Path("/json/movie")
    public class JSONService {

    	@BadgerFish
    	@GET
    	@Path("/get")
    	@Produces("application/json")
    	public Movie getMovieInJSON() {

    		Movie movie = new Movie();
    		movie.setName("Transformers: Dark of the Moon");
    		movie.setDirector("Michael Bay");
    		movie.setYear(2011);

    		return movie;

    	}

    }

## 4\. Demo

When URI pattern “**/json/movie/get**” is requested, following JSON file will be returned.

    {
    	"movie":
    	{
    		"@year":"2011",
    		"director":{
    			"$":"Michael Bay"
    		},
    		"name":{
    			"$":"Transformers: Dark of the Moon"
    		}
    	}
    }

![result](http://www.mkyong.com/wp-content/uploads/2011/07/jaxb-json-resteasy.png)

[http://www.mkyong.com/webservices/jax-rs/download-json-from-jax-rs-with-jaxb-resteasy/](http://www.mkyong.com/webservices/jax-rs/download-json-from-jax-rs-with-jaxb-resteasy/)
