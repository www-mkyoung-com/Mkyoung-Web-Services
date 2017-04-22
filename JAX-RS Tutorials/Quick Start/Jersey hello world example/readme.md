[Jersey](http://jersey.java.net/), reference implementation to develope RESTful web service based on the [JAX-RS (JSR 311)](http://jsr311.java.net/nonav/releases/1.1/index.html) specification.

In this tutorial, we show you how to develop a simple hello world REST web application with **Jersey**.

Technologies and Tools used in this article:

1.  Jersey 1.8
2.  JDK 1.6
3.  Tomcat 6.0
4.  Maven 3.0.3
5.  Eclipse 3.6

**Note**  
If you want to know what and how REST works, just search on Google, ton of available resources.

## 1\. Directory Structure

This is the final web project structure of this tutorial.

![folder directory](http://www.mkyong.com/wp-content/uploads/2011/07/jersey-folder.png)

## 2\. Standard Web Project

Create a standard Maven web project structure.

    mvn archetype:generate -DgroupId=com.mkyong.rest -DartifactId=RESTfulExample
    	-DarchetypeArtifactId=maven-archetype-webapp -DinteractiveMode=false

**Note**  
To support Eclipse, use Maven command :

    mvn eclipse:eclipse -Dwtpversion=2.0

## 3\. Project Dependencies

Jersey is published in Java.net Maven repository. To develop Jersey REST application , just declares “**jersey-server**” in Maven `pom.xm`l.

_File : pom.xml_

    <project ...>

    	<repositories>
    		<repository>
    			<id>maven2-repository.java.net</id>
    			<name>Java.net Repository for Maven</name>
    			<url>http://download.java.net/maven/2/</url>
    			<layout>default</layout>
    		</repository>
    	</repositories>

    	<dependencies>

    		<dependency>
    			<groupId>com.sun.jersey</groupId>
    			<artifactId>jersey-server</artifactId>
    			<version>1.8</version>
    		</dependency>

    	</dependencies>

    </project>

## 4\. REST Service

Simple REST service with Jersey.

    package com.mkyong.rest;

    import javax.ws.rs.GET;
    import javax.ws.rs.Path;
    import javax.ws.rs.PathParam;
    import javax.ws.rs.core.Response;

    @Path("/hello")
    public class HelloWorldService {

    	@GET
    	@Path("/{param}")
    	public Response getMsg(@PathParam("param") String msg) {

    		String output = "Jersey say : " + msg;

    		return Response.status(200).entity(output).build();

    	}

    }

## 5\. web.xml

In `web.xml`, register “`com.sun.jersey.spi.container.servlet.ServletContainer`“, and puts your Jersey service folder under “**init-param**“, “`com.sun.jersey.config.property.packages`“.

_File : web.xml_

    <web-app id="WebApp_ID" version="2.4"
    	xmlns="http://java.sun.com/xml/ns/j2ee"
    	xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
    	xsi:schemaLocation="http://java.sun.com/xml/ns/j2ee
    	http://java.sun.com/xml/ns/j2ee/web-app_2_4.xsd">
    	<display-name>Restful Web Application</display-name>

    	<servlet>
    		<servlet-name>jersey-serlvet</servlet-name>
    		<servlet-class>
                         com.sun.jersey.spi.container.servlet.ServletContainer
                    </servlet-class>
    		<init-param>
    		     <param-name>com.sun.jersey.config.property.packages</param-name>
    		     <param-value>com.mkyong.rest</param-value>
    		</init-param>
    		<load-on-startup>1</load-on-startup>
    	</servlet>

    	<servlet-mapping>
    		<servlet-name>jersey-serlvet</servlet-name>
    		<url-pattern>/rest/*</url-pattern>
    	</servlet-mapping>

    </web-app>

## 6\. Demo

In this example, web request from “**projectURL/rest/hello/**” will match to “**HelloWorldService**“, via `@Path("/hello")`.

And the “**{any values}**” from “**projectURL/rest/hello/{any values}**” will match to parameter annotated with `@PathParam`.

_URL : http://localhost:8080/RESTfulExample/rest/hello/mkyong_

![demo](http://www.mkyong.com/wp-content/uploads/2011/07/jersey-hello-world.png)

[http://www.mkyong.com/webservices/jax-rs/jersey-hello-world-example/](http://www.mkyong.com/webservices/jax-rs/jersey-hello-world-example/)
