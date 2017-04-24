## Problem

In Jersey development, hit following error message on Tomcat.

    SEVERE: Servlet /RESTfulExample threw load() exception
      java.lang.ClassNotFoundException: com.sun.jersey.spi.container.servlet.ServletContainer

      at org.apache.catalina.loader.WebappClassLoader.loadClass(WebappClassLoader.java:1516)
      at org.apache.catalina.loader.WebappClassLoader.loadClass(WebappClassLoader.java:1361)
      //...

Here’s the Maven `pom.xml`

    <dependency>
    	<groupId>com.sun.jersey</groupId>
    	<artifactId>jersey-core</artifactId>
    	<version>1.8</version>
    </dependency>

## Solution

The “**com.sun.jersey.spi.container.servlet.ServletContainer**” is included in “`jersey-server.jar`“, not “`jersey-core.jar`“.

Actually, to develop REST service with Jersey, you just need to include “`jersey-server.jar`“, it will download the “`jersey-core.jar`” dependency automatically.

    <dependency>
    	<groupId>com.sun.jersey</groupId>
    	<artifactId>jersey-server</artifactId>
    	<version>1.8</version>
    </dependency>

[http://www.mkyong.com/webservices/jax-rs/classnotfoundexception-com-sun-jersey-spi-container-servlet-servletcontainer/](http://www.mkyong.com/webservices/jax-rs/classnotfoundexception-com-sun-jersey-spi-container-servlet-servletcontainer/)
