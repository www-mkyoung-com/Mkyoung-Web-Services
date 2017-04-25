Developing a Java web service development with JAX-WS, and publish an end point…

    public static void main(String[] args) {
       Endpoint.publish("http://localhost:8080/ws/hello", new WallStreetImpl());
    }

## 1\. Problem

It hits the following error message.

    Exception in thread "main" com.sun.xml.internal.ws.server.ServerRtException:
    	Server Runtime Error: java.net.BindException: Address already in use: bind
    	...
    Caused by: java.net.BindException: Address already in use: bind
    	at sun.nio.ch.Net.bind(Native Method)
    	...

## 2\. Solution

A very common error message, it means the address (usually it is the port number) is already in used by another application.

**To fix it**, change the end point port number :

    public static void main(String[] args) {
       Endpoint.publish("http://localhost:1234/ws/hello", new WallStreetImpl());
    }

## Reference

1.  [Linux – Which application is using port 8080](http://www.mkyong.com/linux/linux-which-application-is-using-port-8080/)

[http://www.mkyong.com/webservices/jax-ws/java-net-bindexception-address-already-in-use-bind/](http://www.mkyong.com/webservices/jax-ws/java-net-bindexception-address-already-in-use-bind/)
