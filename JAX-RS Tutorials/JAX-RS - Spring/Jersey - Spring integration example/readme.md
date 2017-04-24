This tutorial show you how to integrate Jersey web application with Spring framework.

Technologies used :

1.  Jersey 1.8
2.  Spring 3.0.5.RELEASE
3.  Eclipse 3.6
4.  Maven 3

## 1\. Project Dependency

Declares Jersey 1.8, Spring3 and “**jersey-spring.jar**” dependencies in Maven `pom.xml` file.

**Note**  
In “**jersey-spring.jar**” version, it will download all the Spring 2.5.6 dependencies. To use Spring 3, you need to exclude those old Spring libraries manually.

    <repositories>
    	<repository>
    		<id>maven2-repository.java.net</id>
    		<name>Java.net Repository for Maven</name>
    		<url>http://download.java.net/maven/2/</url>
    	</repository>
    </repositories>

    <dependencies>

    	<!-- Jersey -->
    	<dependency>
    		<groupId>com.sun.jersey</groupId>
    		<artifactId>jersey-server</artifactId>
    		<version>1.8</version>
    	</dependency>

    	<!-- Spring 3 dependencies -->
    	<dependency>
    		<groupId>org.springframework</groupId>
    		<artifactId>spring-core</artifactId>
    		<version>3.0.5.RELEASE</version>
    	</dependency>

    	<dependency>
    		<groupId>org.springframework</groupId>
    		<artifactId>spring-context</artifactId>
    		<version>3.0.5.RELEASE</version>
    	</dependency>

    	<dependency>
    		<groupId>org.springframework</groupId>
    		<artifactId>spring-web</artifactId>
    		<version>3.0.5.RELEASE</version>
    	</dependency>

    	<!-- Jersey + Spring -->
    	<dependency>
    		<groupId>com.sun.jersey.contribs</groupId>
    		<artifactId>jersey-spring</artifactId>
    		<version>1.8</version>
    		<exclusions>
    			<exclusion>
    				<groupId>org.springframework</groupId>
    				<artifactId>spring</artifactId>
    			</exclusion>
    			<exclusion>
    				<groupId>org.springframework</groupId>
    				<artifactId>spring-core</artifactId>
    			</exclusion>
    			<exclusion>
    				<groupId>org.springframework</groupId>
    				<artifactId>spring-web</artifactId>
    			</exclusion>
    			<exclusion>
    				<groupId>org.springframework</groupId>
    				<artifactId>spring-beans</artifactId>
    			</exclusion>
    			<exclusion>
    				<groupId>org.springframework</groupId>
    				<artifactId>spring-context</artifactId>
    			</exclusion>
    		</exclusions>
    	</dependency>

    </dependencies>

## 2\. Spring Bean

A simple “transactionBo” bean is registered in Spring Ioc container. Later you will inject this bean into Jersey service.

    package com.mkyong.transaction;

    public interface TransactionBo{

    	String save();

    }

    package com.mkyong.transaction.impl;

    import com.mkyong.transaction.TransactionBo;

    public class TransactionBoImpl implements TransactionBo {

    	public String save() {

    		return "Jersey + Spring example";

    	}

    }

_File : applicationContext.xml_ – Register bean and enable the component auto scanning feature.

    <beans xmlns="http://www.springframework.org/schema/beans"
    	xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
    	xmlns:context="http://www.springframework.org/schema/context"
    	xsi:schemaLocation="http://www.springframework.org/schema/beans
    	http://www.springframework.org/schema/beans/spring-beans-3.0.xsd
    	http://www.springframework.org/schema/context
    	http://www.springframework.org/schema/context/spring-context-3.0.xsd">

    	<context:component-scan base-package="com.mkyong.rest" />

            <bean id="transactionBo"
                      class="com.mkyong.transaction.impl.TransactionBoImpl" />

    </beans>

## 3\. Jersey

In REST method, you can to auto inject the “transactionBo” bean from Spring into Jersey.

    package com.mkyong.rest;

    import javax.ws.rs.GET;
    import javax.ws.rs.Path;
    import javax.ws.rs.core.Response;
    import org.springframework.beans.factory.annotation.Autowired;
    import org.springframework.stereotype.Component;
    import com.mkyong.transaction.TransactionBo;

    @Component
    @Path("/payment")
    public class PaymentService {

    	@Autowired
    	TransactionBo transactionBo;

    	@GET
    	@Path("/mkyong")
    	public Response savePayment() {

    		String result = transactionBo.save();

    		return Response.status(200).entity(result).build();

    	}

    }

## 4\. Integrate Jersey with Spring

The core integration is in `web.xml`

1.  Register Spring “`ContextLoaderListener`” listener class
2.  Change Jersey servlet from “`com.sun.jersey.spi.container.servlet.ServletContainer`” to “`com.sun.jersey.spi.spring.container.servlet.SpringServlet`“.

_File : web.xml_

    <web-app id="WebApp_ID" version="2.4"
    	xmlns="http://java.sun.com/xml/ns/j2ee"
    	xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
    	xsi:schemaLocation="http://java.sun.com/xml/ns/j2ee
    	http://java.sun.com/xml/ns/j2ee/web-app_2_4.xsd">
    	<display-name>Restful Web Application</display-name>

    	<context-param>
    		<param-name>contextConfigLocation</param-name>
    		<param-value>classpath:applicationContext.xml</param-value>
    	</context-param>

    	<listener>
    		<listener-class>
    			org.springframework.web.context.ContextLoaderListener
    		</listener-class>
    	</listener>

    	<servlet>
    		<servlet-name>jersey-serlvet</servlet-name>
    		<servlet-class>
    			com.sun.jersey.spi.spring.container.servlet.SpringServlet
    		</servlet-class>
    		<init-param>
    			<param-name>
                                     com.sun.jersey.config.property.packages
                            </param-name>
    			<param-value>com.mkyong.rest</param-value>
    		</init-param>
    		<load-on-startup>1</load-on-startup>
    	</servlet>

    	<servlet-mapping>
    		<servlet-name>jersey-serlvet</servlet-name>
    		<url-pattern>/rest/*</url-pattern>
    	</servlet-mapping>

    </web-app>

## 5\. Demo

![jersey spring demo](http://www.mkyong.com/wp-content/uploads/2011/07/jersey-spring-demo.png)

[http://www.mkyong.com/webservices/jax-rs/jersey-spring-integration-example/](http://www.mkyong.com/webservices/jax-rs/jersey-spring-integration-example/)
