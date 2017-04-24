## Problem

Developing **RESTEasy + JAXB provider** to support XML, when return it back to client, it prompts following error message :

    org.jboss.resteasy.core.NoMessageBodyWriterFoundFailure:
    	Could not find MessageBodyWriter for response object of type:
                    com.mkyong.rest.User of media type: application/xml

        at org.jboss.resteasy.core.ServerResponse.writeTo(ServerResponse.java:216)
        at org.jboss.resteasy.core.SynchronousDispatcher.invoke(SynchronousDispatcher.java:500)
        at org.jboss.resteasy.core.SynchronousDispatcher.invoke(SynchronousDispatcher.java:119)
        //...

## Solution

To use JAXB in RESTEasy, you need to include “**resteasy-jaxb-provider.jar**“.

    <repositories>
    <repository>
    	<id>JBoss repository</id>
    	<url>https://repository.jboss.org/nexus/content/groups/public-jboss/</url>
    </repository>
      </repositories>

      <dependencies>

    <dependency>
    	<groupId>org.jboss.resteasy</groupId>
    	<artifactId>resteasy-jaxrs</artifactId>
    	<version>2.2.1.GA</version>
    </dependency>

    <dependency>
    	<groupId>org.jboss.resteasy</groupId>
    	<artifactId>resteasy-jaxb-provider</artifactId>
    	<version>2.2.0.GA</version>
    </dependency>

      </dependencies>

[http://www.mkyong.com/webservices/jax-rs/resteasy-could-not-find-messagebodywriter-for-response-object-of-typexx-of-media-type-applicationxml/](http://www.mkyong.com/webservices/jax-rs/resteasy-could-not-find-messagebodywriter-for-response-object-of-typexx-of-media-type-applicationxml/)
