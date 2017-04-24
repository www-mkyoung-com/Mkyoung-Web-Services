This is part 2 of JAX-WS SOAP handler. In previous article – [JAX-WS : SOAP handler in server side](http://www.mkyong.com/webservices/jax-ws/jax-ws-soap-handler-in-server-side/), you created a web service and attach a handler to retrieve the client MAC address in header block, for every incoming SOAP message.

## SOAP handler in client side

In this article, you will develop a web service client to access the published service in [previous article](http://www.mkyong.com/webservices/jax-ws/jax-ws-soap-handler-in-server-side/), and attach a handler to inject client’s MAC address into header block, for every outgoing SOAP message that’s send by client side.

Directory structure of this example

![](http://www.mkyong.com/wp-content/uploads/2011/01/jaxws-handler-client-example.png)

## 1\. Web Service Client

Uses **wsimport** command to parse the published service WSDL file (_http://localhost:8888/ws/server?wsdl_) and generate all required files to access the service.

    C:\>wsimport -keep -verbose http://localhost:8888/ws/server?wsdl
    parsing WSDL...

    generating code...
    com\mkyong\ws\GetServerName.java
    com\mkyong\ws\GetServerNameResponse.java
    com\mkyong\ws\ObjectFactory.java
    com\mkyong\ws\ServerInfo.java
    com\mkyong\ws\ServerInfoService.java
    com\mkyong\ws\package-info.java

Six files are generated automatically, you may only need to concern on the `ServerInfoService.java`.

_File : ServerInfoService.java_

    @WebServiceClient(name = "ServerInfoService",
    	targetNamespace = "http://ws.mkyong.com/",
    	wsdlLocation = "http://localhost:8888/ws/server?wsdl")
    public class ServerInfoService extends Service
    {
    	//......
    }

A client to access the published service.  
_File : WsClient.java_

    package com.mkyong.client;

    import com.mkyong.ws.ServerInfo;
    import com.mkyong.ws.ServerInfoService;

    public class WsClient{

    	public static void main(String[] args) throws Exception {

    		ServerInfoService sis = new ServerInfoService();
    		ServerInfo si = sis.getServerInfoPort();

    		System.out.println(si.getServerName());

        }

    }

## 2\. SOAP Handler

Create a SOAP handler to inject client’s MAC address into the SOAP header block, for every outgoing SOAP message. See comments for the code explanation.

_File : MacAddressInjectHandler.java_

    package com.mkyong.handler;

    import java.io.IOException;
    import java.net.InetAddress;
    import java.net.NetworkInterface;
    import java.net.SocketException;
    import java.net.UnknownHostException;
    import java.util.Set;
    import javax.xml.namespace.QName;
    import javax.xml.soap.SOAPConstants;
    import javax.xml.soap.SOAPEnvelope;
    import javax.xml.soap.SOAPException;
    import javax.xml.soap.SOAPHeader;
    import javax.xml.soap.SOAPHeaderElement;
    import javax.xml.soap.SOAPMessage;
    import javax.xml.ws.handler.MessageContext;
    import javax.xml.ws.handler.soap.SOAPHandler;
    import javax.xml.ws.handler.soap.SOAPMessageContext;

    public class MacAddressInjectHandler implements SOAPHandler<SOAPMessageContext>{

       @Override
       public boolean handleMessage(SOAPMessageContext context) {

    	System.out.println("Client : handleMessage()......");

    	Boolean isRequest = (Boolean) context.get(MessageContext.MESSAGE_OUTBOUND_PROPERTY);

    	//if this is a request, true for outbound messages, false for inbound
    	if(isRequest){

    	try{
    	    SOAPMessage soapMsg = context.getMessage();
                SOAPEnvelope soapEnv = soapMsg.getSOAPPart().getEnvelope();
                SOAPHeader soapHeader = soapEnv.getHeader();

                //if no header, add one
                if (soapHeader == null){
                	soapHeader = soapEnv.addHeader();
                }

                //get mac address
                String mac = getMACAddress();

                //add a soap header, name as "mac address"
                QName qname = new QName("http://ws.mkyong.com/", "macAddress");
                SOAPHeaderElement soapHeaderElement = soapHeader.addHeaderElement(qname);

                soapHeaderElement.setActor(SOAPConstants.URI_SOAP_ACTOR_NEXT);
                soapHeaderElement.addTextNode(mac);
                soapMsg.saveChanges();

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
    		System.out.println("Client : handleFault()......");
    		return true;
    	}

    	@Override
    	public void close(MessageContext context) {
    		System.out.println("Client : close()......");
    	}

    	@Override
    	public Set<QName> getHeaders() {
    		System.out.println("Client : getHeaders()......");
    		return null;
    	}

       //return current client mac address
       private String getMACAddress(){

    	InetAddress ip;
    	StringBuilder sb = new StringBuilder();

    	try {

    		ip = InetAddress.getLocalHost();
    		System.out.println("Current IP address : " + ip.getHostAddress());

    		NetworkInterface network = NetworkInterface.getByInetAddress(ip);

    		byte[] mac = network.getHardwareAddress();

    		System.out.print("Current MAC address : ");

    		for (int i = 0; i < mac.length; i++) {

    			sb.append(String.format("%02X%s", mac[i], (i < mac.length - 1) ? "-" : ""));

    		}
    		System.out.println(sb.toString());

    	} catch (UnknownHostException e) {

    		e.printStackTrace();

    	} catch (SocketException e){

    		e.printStackTrace();

    	}

    	return sb.toString();
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
          <javaee:handler-class>com.mkyong.handler.MacAddressInjectHandler</javaee:handler-class>
        </javaee:handler>
      </javaee:handler-chain>
    </javaee:handler-chains>

## 4\. Attach SOAP Handler --> Web Service Client

To attach above SOAP handler to web service client, edit the `ServerInfoService.java` file (generated via wsimport), and annotate with **@HandlerChain** and specify the SOAP handler file name inside.

_File : ServerInfoService.java_

    @WebServiceClient(name = "ServerInfoService",
    	targetNamespace = "http://ws.mkyong.com/",
    	wsdlLocation = "http://localhost:8888/ws/server?wsdl")
    @HandlerChain(file="handler-chain.xml")
    public class ServerInfoService extends Service
    {
    	//......
    }

Done, please proceed on next article - [Part 3 : JAX-WS - SOAP handler testing for client and server side](http://www.mkyong.com/webservices/jax-ws/jax-ws-soap-handler-testing-for-client-and-server-side/).

[http://www.mkyong.com/webservices/jax-ws/jax-ws-soap-handler-in-client-side/](http://www.mkyong.com/webservices/jax-ws/jax-ws-soap-handler-in-client-side/)
