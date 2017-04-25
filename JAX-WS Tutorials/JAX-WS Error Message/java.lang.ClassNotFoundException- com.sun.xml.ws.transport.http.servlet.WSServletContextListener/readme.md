## Problem

Deploying a JAX-WS web service on Tomcat, hits following error message :

    java.lang.ClassNotFoundException:
    	com.sun.xml.ws.transport.http.servlet.WSServletContextListener

## Solution

The JAX-WS dependency library “**jaxws-rt.jar**” is missing.

1.  Go here[ http://jax-ws.java.net/](http://jax-ws.java.net/).
2.  Download JAX-WS RI distribution.
3.  Unzip it and copy “**jaxws-rt.jar**” to Tomcat library folder “**{$TOMCAT}/lib**“.
4.  Restart Tomcat.

## Reference

1.  [WSServletContextListener JavaDoc](https://jax-ws-architecture-document.dev.java.net/nonav/doc21/com/sun/xml/ws/transport/http/servlet/WSServletContextListener.html)

[http://www.mkyong.com/webservices/jax-ws/java-lang-classnotfoundexception-com-sun-xml-ws-transport-http-servlet-wsservletcontextlistener/](http://www.mkyong.com/webservices/jax-ws/java-lang-classnotfoundexception-com-sun-xml-ws-transport-http-servlet-wsservletcontextlistener/)
