## Problem

Deploying a JAX-WS web service on Tomcat, hits following error message :

    java.lang.ClassNotFoundException:
    	javax.xml.ws.soap.AddressingFeature$Responses

## Solution

The JAX-WS dependency library “**jaxws-api.jar**” is missing.

1.  Go here [http://jax-ws.java.net/](http://jax-ws.java.net/).
2.  Download JAX-WS RI distribution.
3.  Unzip it and copy “**jaxws-api.jar**” to Tomcat library folder “**{$TOMCAT}/lib**“.
4.  Restart Tomcat.

[http://www.mkyong.com/webservices/jax-ws/java-lang-classnotfoundexception-javax-xml-ws-soap-addressingfeatureresponses/](http://www.mkyong.com/webservices/jax-ws/java-lang-classnotfoundexception-javax-xml-ws-soap-addressingfeatureresponses/)
