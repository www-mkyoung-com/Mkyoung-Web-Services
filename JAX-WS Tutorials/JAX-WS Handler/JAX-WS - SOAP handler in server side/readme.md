SOAP handler is a SOAP message interceptor, which is able to intercept incoming or outgoing SOAP message and manipulate its values. For example, attach a SOAP handler in client side, which will inject client’s computer MAC address into the SOAP header block for every outgoing SOAP message that is send by the client. In server side, attach another SOAP handler, to retrieve back the client’s MAC address in SOAP header block from every incoming SOAP message. So that the server side is able to determine which computer is allow to access the published service.

This article is split into 3 parts :

1.  JAX-WS : SOAP handler in server side. (this article)
2.  [JAX-WS : SOAP handler in client side](http://www.mkyong.com/webservices/jax-ws/jax-ws-soap-handler-in-client-side/)
3.  [JAX-WS : SOAP handler testing for client and server side](http://www.mkyong.com/webservices/jax-ws/jax-ws-soap-handler-testing-for-client-and-server-side/)

## SOAP handler in server side

In this article, we show you how to create a SOAP handler and attach it in server side, to retrieve the mac address in SOAP header block from every incoming SOAP message. And do validation to allow only computer with MAC address “**90-4C-E5-44-B9-8F**” to access this published service. If an invalid client try to access the service, throw a `SOAPFaultException` back to the client.

_Directory structure of this example_

![](http://www.mkyong.com/wp-content/uploads/2011/01/jaxws-handler-server-example.png)

## 1\. Web Service

A simple web service , with a `getServerName()` method to return a string.

_File : ServerInfo.java_

    package com.mkyong.ws;

    import javax.jws.HandlerChain;
    import javax.jws.WebMethod;
    import javax.jws.WebService;

    @WebService
    public class ServerInfo{

    	@WebMethod
    	public String getServerName() {

    		return "mkyong server";

    	}

    }

Generate necessary Java files for the web service deployment.

    D:\workspace-new\WebServices\bin>wsgen -keep -verbose -cp . com.mkyong.ws.ServerInfo
    Note:   ap round: 1
    ...
    com\mkyong\ws\jaxws\GetServerName.java
    com\mkyong\ws\jaxws\GetServerNameResponse.java
    Note:   ap round: 2

Two files are generated :

1.  com\mkyong\ws\jaxws\GetServerName.java
2.  com\mkyong\ws\jaxws\GetServerNameResponse.java

_File : GetServerName.java_

    package com.mkyong.ws.jaxws;

    import javax.xml.bind.annotation.XmlAccessType;
    import javax.xml.bind.annotation.XmlAccessorType;
    import javax.xml.bind.annotation.XmlRootElement;
    import javax.xml.bind.annotation.XmlType;

    @XmlRootElement(name = "getServerName", namespace = "http://ws.mkyong.com/")
    @XmlAccessorType(XmlAccessType.FIELD)
    @XmlType(name = "getServerName", namespace = "http://ws.mkyong.com/")
    public class GetServerName {
    }

_File : GetServerNameResponse.java_

    package com.mkyong.ws.jaxws;

    import javax.xml.bind.annotation.XmlAccessType;
    import javax.xml.bind.annotation.XmlAccessorType;
    import javax.xml.bind.annotation.XmlElement;
    import javax.xml.bind.annotation.XmlRootElement;
    import javax.xml.bind.annotation.XmlType;

    @XmlRootElement(name = "getServerNameResponse", namespace = "http://ws.mkyong.com/")
    @XmlAccessorType(XmlAccessType.FIELD)
    @XmlType(name = "getServerNameResponse", namespace = "http://ws.mkyong.com/")
    public class GetServerNameResponse {

        @XmlElement(name = "return", namespace = "")
        private String _return;

        /**
         *
         * @return
         *     returns String
         */
        public String getReturn() {
            return this._return;
        }

        /**
         *
         * @param _return
         *     the value for the _return property
         */
        public void setReturn(String _return) {
            this._return = _return;
        }

    }

## 2\. SOAP Handler

Create a SOAP handler to retrieve the value in SOAP header block, for every incoming SOAP message. See comments for the code explanation.

_File MacAddressValidatorHandler.java_

    package com.mkyong.handler;

    import java.io.IOException;
    import java.util.Iterator;
    import java.util.Set;
    import javax.xml.namespace.QName;
    import javax.xml.soap.Node;
    import javax.xml.soap.SOAPBody;
    import javax.xml.soap.SOAPConstants;
    import javax.xml.soap.SOAPEnvelope;
    import javax.xml.soap.SOAPException;
    import javax.xml.soap.SOAPFault;
    import javax.xml.soap.SOAPHeader;
    import javax.xml.soap.SOAPMessage;
    import javax.xml.ws.handler.MessageContext;
    import javax.xml.ws.handler.soap.SOAPHandler;
    import javax.xml.ws.handler.soap.SOAPMessageContext;
    import javax.xml.ws.soap.SOAPFaultException;

    public class MacAddressValidatorHandler implements SOAPHandler<SOAPMessageContext>{

       @Override
       public boolean handleMessage(SOAPMessageContext context) {

    	System.out.println("Server : handleMessage()......");

    	Boolean isRequest = (Boolean) context.get(MessageContext.MESSAGE_OUTBOUND_PROPERTY);

    	//for response message only, true for outbound messages, false for inbound
    	if(!isRequest){

    	try{
    	    SOAPMessage soapMsg = context.getMessage();
    	    SOAPEnvelope soapEnv = soapMsg.getSOAPPart().getEnvelope();
                SOAPHeader soapHeader = soapEnv.getHeader();

                //if no header, add one
    	    if (soapHeader == null){
    	            soapHeader = soapEnv.addHeader();
    	            //throw exception
    	            generateSOAPErrMessage(soapMsg, "No SOAP header.");
    	     }

                 //Get client mac address from SOAP header
    	     Iterator it = soapHeader.extractHeaderElements(SOAPConstants.URI_SOAP_ACTOR_NEXT);

    	     //if no header block for next actor found? throw exception
    	     if (it == null || !it.hasNext()){
    	      	generateSOAPErrMessage(soapMsg, "No header block for next actor.");
                 }

    	     //if no mac address found? throw exception
    	     Node macNode = (Node) it.next();
    	     String macValue = (macNode == null) ? null : macNode.getValue();

    	      if (macValue == null){
    	      	  generateSOAPErrMessage(soapMsg, "No mac address in header block.");
    	      }

    	       //if mac address is not match, throw exception
    	       if(!macValue.equals("90-4C-E5-44-B9-8F")){
    	       	   generateSOAPErrMessage(soapMsg, "Invalid mac address, access is denied.");
    	       }

    	       //tracking
    	       soapMsg.writeTo(System.out);

    		}catch(SOAPException e){
    			System.err.println(e);
    		}catch(IOException e){
    			System.err.println(e);
    		}

    	    }

    	  //continue other handler chain
    	  return true;
    	}

    	@Override
    	public boolean handleFault(SOAPMessageContext context) {

    		System.out.println("Server : handleFault()......");

    		return true;
    	}

    	@Override
    	public void close(MessageContext context) {
    		System.out.println("Server : close()......");
    	}

    	@Override
    	public Set<QName> getHeaders() {
    		System.out.println("Server : getHeaders()......");
    		return null;
    	}

         private void generateSOAPErrMessage(SOAPMessage msg, String reason) {
           try {
              SOAPBody soapBody = msg.getSOAPPart().getEnvelope().getBody();
              SOAPFault soapFault = soapBody.addFault();
              soapFault.setFaultString(reason);
              throw new SOAPFaultException(soapFault);
           }
           catch(SOAPException e) { }
        }

    }

## 3\. SOAP Handler XML file

Create a SOAP handler XML file, and puts your SOAP handler declaration.

_File : handler-chain.xml_

    <?xml version="1.0" encoding="UTF-8" standalone="yes"?>
    <javaee:handler-chains
         xmlns:javaee="http://java.sun.com/xml/ns/javaee"
         xmlns:xsd="http://www.w3.org/2001/XMLSchema">
      <javaee:handler-chain>
        <javaee:handler>
          <javaee:handler-class>com.mkyong.handler.MacAddressValidatorHandler</javaee:handler-class>
        </javaee:handler>
      </javaee:handler-chain>
    </javaee:handler-chains>

## 4\. Attach SOAP Handler –> Web Service

To attach above SOAP handler to web service `ServerInfo.java`, just annotate with **@HandlerChain** and specify the SOAP handler file name inside.

_File : ServerInfo.java_

    package com.mkyong.ws;

    import javax.jws.HandlerChain;
    import javax.jws.WebMethod;
    import javax.jws.WebService;

    @WebService
    @HandlerChain(file="handler-chain.xml")
    public class ServerInfo{

    	@WebMethod
    	public String getServerName() {

    		return "mkyong server";

    	}

    }

## 5\. Web Service Publisher

A simple web service publisher for testing.

    package com.mkyong.endpoint;

    import javax.xml.ws.Endpoint;
    import com.mkyong.ws.ServerInfo;

    //Endpoint publisher
    public class WsPublisher{

    	public static void main(String[] args) {
    	   Endpoint.publish("http://localhost:8888/ws/server", new ServerInfo());

    	   System.out.println("Service is published!");
        }

    }

Done, please proceed on next article – [Part 2 : JAX-WS – SOAP handler in client side](http://www.mkyong.com/webservices/jax-ws/jax-ws-soap-handler-in-client-side/).

[http://www.mkyong.com/webservices/jax-ws/jax-ws-soap-handler-in-server-side/](http://www.mkyong.com/webservices/jax-ws/jax-ws-soap-handler-in-server-side/)
