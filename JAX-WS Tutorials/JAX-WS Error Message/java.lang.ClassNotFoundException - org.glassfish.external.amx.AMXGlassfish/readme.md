## Problem

Deploying a JAX-WS web service on Tomcat, hits following error message :

    java.lang.ClassNotFoundException:
    	org.glassfish.external.amx.AMXGlassfish

## Solution

The JAX-WS dependency library “**management-api.jar**” is missing.

1.  Go here [http://jax-ws.java.net/](http://jax-ws.java.net/).
2.  Download JAX-WS RI distribution.
3.  Unzip it and copy “**management-api.jar**” to Tomcat library folder “**{$TOMCAT}/lib**“.
4.  Restart Tomcat.

[http://www.mkyong.com/webservices/jax-ws/java-lang-classnotfoundexception-org-glassfish-external-amx-amxglassfish/](http://www.mkyong.com/webservices/jax-ws/java-lang-classnotfoundexception-org-glassfish-external-amx-amxglassfish/)
