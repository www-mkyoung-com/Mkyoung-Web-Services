RESTEasy, is required JAXB to support XML file. In this tutorial, we show you how to create an “user” object, convert it into XML file, and return it back to the client.

## 1\. RESTEasy + JAXB

To use JAXB in RESTEasy, you need to include the “**resteasy-jaxb-provider.jar**” dependency.

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

      </dependencies>

## 2\. JAXB XML Provider

Create an object, annotate with JAXB annotation to support XML file conversion.

    import javax.xml.bind.annotation.XmlAttribute;
    import javax.xml.bind.annotation.XmlElement;
    import javax.xml.bind.annotation.XmlRootElement;

    @XmlRootElement(name = "user")
    public class User {

    	String username;
    	String password;
    	int pin;

    	@XmlElement
    	public String getUsername() {
    		return username;
    	}

    	public void setUsername(String username) {
    		this.username = username;
    	}

    	@XmlElement
    	public String getPassword() {
    		return password;
    	}

    	public void setPassword(String password) {
    		this.password = password;
    	}

    	@XmlAttribute
    	public int getPin() {
    		return pin;
    	}

    	public void setPin(int pin) {
    		this.pin = pin;
    	}

    }

With JAXB annotation, above object will convert it into following XML format.

    <user pin="value">
    <password>value</password>
    <username>value</username>
      </user>

## 3\. JAX-RS

To return a XML file, annotate the service method with `@Produces("application/xml")`. RESTEasy will convert the JAXB annotated object into XML file, and return back to the client.

    import javax.ws.rs.GET;
    import javax.ws.rs.Path;
    import javax.ws.rs.Produces;

    @Path("/xml/user")
    public class XMLService {

    	@GET
    	@Path("/get")
    	@Produces("application/xml")
    	public User getUserInXML() {

    		User user = new User();
    		user.setUsername("mkyong");
    		user.setPassword("password");
    		user.setPin(123456);

    		return user;

    	}

    }

## 3\. Demo

When URI pattern “**/xml/user/get**” is requested, following XML file will be returned.

    <user pin="123456">
    	<password>password</password>
    	<username>mkyong</username>
    </user>

![result](http://www.mkyong.com/wp-content/uploads/2011/07/jaxb-xml-provider-resteasy.png)

[http://www.mkyong.com/webservices/jax-rs/download-xml-file-from-jax-rs-with-jaxb-resteasy/](http://www.mkyong.com/webservices/jax-rs/download-xml-file-from-jax-rs-with-jaxb-resteasy/)
