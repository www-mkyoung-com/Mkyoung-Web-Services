## Problem

Created a JAX-WS handler to inject a mac address into the client side SOAP request header automatically :

_File : MacAddressInjectHandler.java_

    public class MacAddressInjectHandler implements SOAPHandler<SOAPMessageContext>{

       @Override
       public boolean handleMessage(SOAPMessageContext context) {

    	//......
    	//get mac address
    	String mac = getMACAddress();

    	//add a soap header, name as "mac address"
    	QName qname = new QName("http://ws.mkyong.com/", "mac address");
    	SOAPHeaderElement soapHeaderElement = soapHeader.addHeaderElement(qname);

    	soapHeaderElement.setActor(SOAPConstants.URI_SOAP_ACTOR_NEXT);
    	soapHeaderElement.addTextNode(mac);
    	soapMsg.saveChanges();
            //......

       }
       //......
    }

When SOAP message is generated and sent to service’s provider (or server), it return the following error message immediately :

    com.sun.xml.internal.ws.streaming.XMLStreamReaderException:
       XML reader error: javax.xml.stream.XMLStreamException: ParseError at [row,col]:[1,110]
       Message: Attribute name "address" associated with
       an element type "mac" must be followed by the ' = ' character.
       //...
    Caused by: javax.xml.stream.XMLStreamException: ParseError at [row,col]:[1,110]
       Message: Attribute name "address" associated with an element type
       "mac" must be followed by the ' = ' character.
       //...

## Solution

The `XMLStreamException` is saying that you’re trying to send an invalid SOAP message that contains invalid format. From SOAP client above, you may generate similar SOAP message as following :

    <S:Envelope xmlns:S="http://schemas.xmlsoap.org/soap/envelope/">
      <S:Header>
    	<mac address xmlns="http://ws.mkyong.com/"
    		xmlns:SOAP-ENV="http://schemas.xmlsoap.org/soap/envelope/"
    		SOAP-ENV:actor="http://schemas.xmlsoap.org/soap/actor/next">
    			90-4C-E5-44-B9-8F
    	</mac address>
      </S:Header>
      <S:Body>
    	<ns2:getServerName xmlns:ns2="http://ws.mkyong.com/"/>
      </S:Body>
    </S:Envelop

And notice the "**mac address**" attribute? The "**space**" in between is causing the "address" became an attribute for "mac" element.

To fix it, just delete the spaces like this :

    QName qname = new QName("http://ws.mkyong.com/", "macaddress");

[http://www.mkyong.com/webservices/jax-ws/javax-xml-stream-xmlstreamexception-parseerror-at-rowcolxxx/](http://www.mkyong.com/webservices/jax-ws/javax-xml-stream-xmlstreamexception-parseerror-at-rowcolxxx/)
