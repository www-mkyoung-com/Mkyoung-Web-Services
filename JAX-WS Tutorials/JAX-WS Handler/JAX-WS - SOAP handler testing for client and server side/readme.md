This is the part 3 of JAX-WS SOAP handler. A testing result for [part 1 : SOAP handler in server side](http://www.mkyong.com/webservices/jax-ws/jax-ws-soap-handler-in-server-side/) and [part 2 : SOAP handler in client side](http://www.mkyong.com/webservices/jax-ws/jax-ws-soap-handler-in-client-side/). See following use cases

## 1\. Valid MAC Address

From top to bottom showing a client with **valid** computer’s MAC address sending a request to published service.

    Client : getHeaders()......
    Client : getHeaders()......
    Client : handleMessage()......
    Current IP address : 192.168.0.2
    Current MAC address : 90-4C-E5-44-B9-8F
    <S:Envelope xmlns:S="http://schemas.xmlsoap.org/soap/envelope/">
    	<S:Header>
    		<macAddress xmlns="http://ws.mkyong.com/"
    			xmlns:SOAP-ENV="http://schemas.xmlsoap.org/soap/envelope/"
    			SOAP-ENV:actor="http://schemas.xmlsoap.org/soap/actor/next">
    				90-4C-E5-44-B9-8F
    		</macAddress>
    	</S:Header>
    	<S:Body>
    		<ns2:getServerName xmlns:ns2="http://ws.mkyong.com/"/>
    	</S:Body>
    </S:Envelope>
    Client : handleMessage()......
    Client : close()......
    mkyong server

## 2\. Invalid MAC Address

From top to bottom showing a client with **invalid** computer’s MAC address sending a request to published service, and get a `SOAPFaultException`.

    Client : getHeaders()......
    Client : getHeaders()......
    Client : handleMessage()......
    Current IP address : 192.168.0.2
    Current MAC address : 90-4C-E5-44-B9-00
    <S:Envelope xmlns:S="http://schemas.xmlsoap.org/soap/envelope/">
    	<S:Header>
    		<macAddress xmlns="http://ws.mkyong.com/"
    			xmlns:SOAP-ENV="http://schemas.xmlsoap.org/soap/envelope/"
    			SOAP-ENV:actor="http://schemas.xmlsoap.org/soap/actor/next">
    				90-4C-E5-44-B9-00
    		</macAddress>
    	</S:Header>
    	<S:Body>
    		<ns2:getServerName xmlns:ns2="http://ws.mkyong.com/"/>
    	</S:Body>
    </S:Envelope>
    Server : handleMessage()......
    Server : close()......
    Client : handleFault()......
    Client : close()......
    Exception in thread "main" javax.xml.ws.soap.SOAPFaultException: Invalid mac address, access is denied.
            at com.sun.xml.internal.ws.fault.SOAP11Fault.getProtocolException(Unknown Source)
            at com.sun.xml.internal.ws.fault.SOAPFaultBuilder.createException(Unknown Source)
            at com.sun.xml.internal.ws.client.sei.SyncMethodHandler.invoke(Unknown Source)
            at com.sun.xml.internal.ws.client.sei.SyncMethodHandler.invoke(Unknown Source)
            at com.sun.xml.internal.ws.client.sei.SEIStub.invoke(Unknown Source)
            at $Proxy29.getServerName(Unknown Source)
            at com.mkyong.client.WsClient.main(WsClient.java:13)
    Caused by: javax.xml.ws.soap.SOAPFaultException: Invalid mac address, access is denied.
           //...

Done.

## Conclusion

In short, web service handler is just a interceptor to intercept incoming and outgoing SOAP message, both in client or server side, and it’s able to manipulate the SOAP message values as well.

[http://www.mkyong.com/webservices/jax-ws/jax-ws-soap-handler-testing-for-client-and-server-side/](http://www.mkyong.com/webservices/jax-ws/jax-ws-soap-handler-testing-for-client-and-server-side/)
