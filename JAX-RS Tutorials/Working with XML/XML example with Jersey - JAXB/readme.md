This tutorial show you how to use JAXB to convert **object to XML in Jersey**, and return it back to user.

## 1\. Dependency

To integrate JAXB with Jersey, no extra dependency is required. Just include “**jersey-server.jar**” will do.

## 2\. JAXB Annotation

Annotate object with JAXB annotation, for conversion later.

    import javax.xml.bind.annotation.XmlAttribute;
    import javax.xml.bind.annotation.XmlElement;
    import javax.xml.bind.annotation.XmlRootElement;

    @XmlRootElement(name = "customer")
    public class Customer {

    	String name;
    	int pin;

    	@XmlElement
    	public String getName() {
    		return name;
    	}

    	public void setName(String name) {
    		this.name = name;
    	}

    	@XmlAttribute
    	public int getPin() {
    		return pin;
    	}

    	public void setPin(int pin) {
    		this.pin = pin;
    	}

    }

Above object will convert into following XML format.

    <customer pin="value">
    <name>value</name>
      </customer>

## 3\. Jersey and XML

To return a XML file, annotate the method with `@Produces(MediaType.APPLICATION_XML)`. Jersey will convert the JAXB annotated object into XML file automatically.

    import javax.ws.rs.GET;
    import javax.ws.rs.Path;
    import javax.ws.rs.PathParam;
    import javax.ws.rs.Produces;
    import javax.ws.rs.core.MediaType;
    import com.mkyong.Customer;

    @Path("/xml/customer")
    public class XMLService {

    	@GET
    	@Path("/{pin}")
    	@Produces(MediaType.APPLICATION_XML)
    	public Customer getCustomerInXML(@PathParam("pin") int pin) {

    		Customer customer = new Customer();
    		customer.setName("mkyong");
    		customer.setPin(pin);

    		return customer;

    	}

    }

## 4\. Demo

When URI pattern “**xml/customer/{param value}**” is requested, a formatted XML file will be returned.

URL : _http://localhost:8080/RESTfulExample/rest/xml/customer/999_

![xml and jersey](http://www.mkyong.com/wp-content/uploads/2011/07/jersey-xml.png)

[http://www.mkyong.com/webservices/jax-rs/download-xml-with-jersey-jaxb/](http://www.mkyong.com/webservices/jax-rs/download-xml-with-jersey-jaxb/)
