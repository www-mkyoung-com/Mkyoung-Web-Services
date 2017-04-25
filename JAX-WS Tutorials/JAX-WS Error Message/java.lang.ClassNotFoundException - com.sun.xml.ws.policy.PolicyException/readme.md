## Problem

Deploying a JAX-WS web service on Tomcat, hits following error message :

    java.lang.ClassNotFoundException:
    	com/sun/xml/ws/policy/PolicyException

## Solution

The JAX-WS dependency library “**policy.jar**” is missing.

1.  Go here [http://jax-ws.java.net/](http://jax-ws.java.net/).
2.  Download JAX-WS RI distribution.
3.  Unzip it and copy “**policy.jar**” to Tomcat library folder “**{$TOMCAT}/lib**“.
4.  Restart Tomcat.

[http://www.mkyong.com/webservices/jax-ws/java-lang-classnotfoundexception-comsunxmlwspolicypolicyexception/](http://www.mkyong.com/webservices/jax-ws/java-lang-classnotfoundexception-comsunxmlwspolicypolicyexception/)
