## Question

Developing REST service, with file upload function with resteasy, after added resteasy multipart dependency, following strange error message prompt during the application start up?

P.S Using **resteasy-jaxrs** and **resteasy-multipart** version 2.2.1.GA.

    SEVERE: Exception sending context initialized event to listener instance of class
           org.jboss.resteasy.plugins.server.servlet.ResteasyBootstrap
           java.lang.RuntimeException: Unable to scan WEB-INF for JAX-RS annotations,
           you must manually register your classes/resources
    	//...
    	at org.apache.catalina.startup.Bootstrap.start(Bootstrap.java:289)
    	at org.apache.catalina.startup.Bootstrap.main(Bootstrap.java:414)
    Caused by: java.io.EOFException: Unexpected end of ZLIB input stream
    	at java.util.zip.InflaterInputStream.fill(InflaterInputStream.java:223)
    	at java.util.zip.InflaterInputStream.read(InflaterInputStream.java:141)
    	at java.util.zip.ZipInputStream.read(ZipInputStream.java:146)
    	//...
    	at org.scannotation.AnnotationDB.scanClass(AnnotationDB.java:343)
    	at org.scannotation.AnnotationDB.scanArchives(AnnotationDB.java:326)
    	at org.jboss.resteasy.plugins.server.servlet.ConfigurationBootstrap
            .createDeployment(ConfigurationBootstrap.java:163)
    	... 17 more
    09 Julai 2011 1:52:13 PM org.apache.catalina.core.StandardContext listenerStop
    SEVERE: Exception sending context destroyed event to listener instance of class
    org.jboss.resteasy.plugins.server.servlet.ResteasyBootstrap
    java.lang.NullPointerException
    	at org.jboss.resteasy.plugins.server.servlet.ResteasyBootstrap.contextDestroyed...

## Solution

Not sure what is the root caused of it, two solutions :

## 1\. Downgrade Version

Downgrade “**resteasy-multipart-provider**” to version 2.2.0.GA get the problem solved.

    <dependency>
    	<groupId>org.jboss.resteasy</groupId>
    	<artifactId>resteasy-jaxrs</artifactId>
    	<version>2.2.1.GA</version>
    </dependency>

    <dependency>
    	<groupId>org.jboss.resteasy</groupId>
    	<artifactId>resteasy-multipart-provider</artifactId>
    	<version>2.2.0.GA</version>
    </dependency>

## 2\. Register RESTEasy Manually

Disable RESTEasy auto service scanning mode, and register it manually. Not sure why, but it works.

_File : web.xml_

    <!-- disabled auto scan mode
           <context-param>
    	<param-name>resteasy.scan</param-name>
    	<param-value>true</param-value>
    </context-param>
             -->
    <context-param>
    	<param-name>resteasy.resources</param-name>
    	<param-value>your REST service</param-value>
    </context-param>

[http://www.mkyong.com/webservices/jax-rs/resteasy-unable-to-scan-web-inf-for-jax-rs-annotations/](http://www.mkyong.com/webservices/jax-rs/resteasy-unable-to-scan-web-inf-for-jax-rs-annotations/)
