## Problem

Using **Jackson** as JSON provider in **RESTEasy**.

    <dependency>
    	<groupId>org.jboss.resteasy</groupId>
    	<artifactId>resteasy-jaxrs</artifactId>
    	<version>2.2.1.GA</version>
    </dependency>

    <dependency>
    	<groupId>org.jboss.resteasy</groupId>
    	<artifactId>resteasy-jackson-provider</artifactId>
    	<version>2.2.1.GA</version>
    </dependency>

With RESTEasy auto scanning enabled.

    <context-param>
    	<param-name>resteasy.scan</param-name>
    	<param-value>true</param-value>
    </context-param>

When starting up, it hits following errors and failed to start up any of the RESTEasy services.

    SEVERE: Exception sending context initialized event to listener instance of class
    	org.jboss.resteasy.plugins.server.servlet.ResteasyBootstrap
    java.lang.RuntimeException: Unable to instantiate MessageBodyReader
    	at org.jboss.resteasy.spi.ResteasyDeployment.registerProvider(ResteasyDeployment.java:505)
    	at org.jboss.resteasy.spi.ResteasyDeployment.registration(ResteasyDeployment.java:305)
    	at org.jboss.resteasy.spi.ResteasyDeployment.start(ResteasyDeployment.java:225)
    	//...
    Caused by: java.lang.RuntimeException:
            Illegal to inject a message body into a singleton into public
    	org.codehaus.jackson.jaxrs.JacksonJsonProvider(
    	org.codehaus.jackson.map.ObjectMapper,org.codehaus.jackson.jaxrs.Annotations[])
    	... 20 more

## Solution

There is problem to integrate RESTEasy with Jackson if “**auto scanning**” mode is enabled. Hope it will fixed in later released.

To fix it, disabled the auto scanning feature, and register your RESTEasy service manually in `web.xml`.

    <!-- disabled auto scanning
    <context-param>
    	<param-name>resteasy.scan</param-name>
    	<param-value>true</param-value>
    </context-param>
            -->
    <context-param>
    	<param-name>resteasy.resources</param-name>
    	<param-value>com.mkyong.JacksonRestServiceHere</param-value>
    </context-param>

[http://www.mkyong.com/webservices/jax-rs/illegal-to-inject-a-message-body-into-a-singleton-into-public-org-codehaus-jackson-jaxrs-jacksonjsonprovider/](http://www.mkyong.com/webservices/jax-rs/illegal-to-inject-a-message-body-into-a-singleton-into-public-org-codehaus-jackson-jaxrs-jacksonjsonprovider/)
