**Note**  
Not much comments on this [official RESTEasy guide to integrate Spring](http://docs.jboss.org/resteasy/docs/2.2.1.GA/userguide/html/RESTEasy_Spring_Integration.html), the documentation need to be improve, because it’s simply too “**abstract**” for others to understand (at least to me :)).

Here we show you two general ways to inject Spring bean into **JBoss RESTEasy**, below solutions should works on most of the web frameworks and JAX-RS implementations also.

1.  Use **WebApplicationContextUtils** + ServletContext.
2.  Create a class to implement **ApplicationContextAware intrefaces**, with singleton pattern is recommended.

In this tutorial, we are integrating **RESTEasy 2.2.1.GA** with **Spring 3.0.5.RELEASE**.

## 1\. Project Dependencies

Not many dependencies, just declare RESTEasy and Spring core in your Maven `pom.xml` file.

    <repositories>
    	<repository>
    	  <id>JBoss repository</id>
    	  <url>https://repository.jboss.org/nexus/content/groups/public-jboss/</url>
    	</repository>
    </repositories>

    <dependencies>

    	<!-- JBoss RESTEasy -->
    	<dependency>
    		<groupId>org.jboss.resteasy</groupId>
    		<artifactId>resteasy-jaxrs</artifactId>
    		<version>2.2.1.GA</version>
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

    	<!-- need for solution 1, if your container don't have this -->
    	<dependency>
    		<groupId>javax.servlet</groupId>
    		<artifactId>servlet-api</artifactId>
    		<version>2.4</version>
    	</dependency>

    </dependencies>

## 2\. Spring Bean

A simple Spring bean named “**customerBo**“, later you will inject this bean into RESTEasy service, via Spring.

    package com.mkyong.customer;

    public interface CustomerBo{

    	String getMsg();

    }

    package com.mkyong.customer.impl;

    import com.mkyong.customer.CustomerBo;

    public class CustomerBoImpl implements CustomerBo {

    	public String getMsg() {

    		return "RESTEasy + Spring example";

    	}

    }

_File : applicationContext.xml_

    <beans xmlns="http://www.springframework.org/schema/beans"
    	xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
    	xsi:schemaLocation="http://www.springframework.org/schema/beans
    	http://www.springframework.org/schema/beans/spring-beans-3.0.xsd ">

    	<bean id="customerBo" class="com.mkyong.customer.impl.CustomerBoImpl">
            </bean>

    </beans>

## 3.1 WebApplicationContextUtils + ServletContext

The first solution is uses JAX-RS `@Context` to get the **ServletContext**, and `WebApplicationContextUtils` to get the Spring application context, with this Spring application context, you are able to access and get beans from Spring container.

    package com.mkyong.rest;

    import javax.servlet.ServletContext;
    import javax.ws.rs.GET;
    import javax.ws.rs.Path;
    import javax.ws.rs.core.Context;
    import javax.ws.rs.core.Response;
    import org.springframework.context.ApplicationContext;
    import org.springframework.web.context.support.WebApplicationContextUtils;
    import com.mkyong.customer.CustomerBo;

    @Path("/customer")
    public class PrintService {

    	CustomerBo customerBo;

    	@GET
    	@Path("/print")
    	public Response printMessage(@Context ServletContext servletContext) {

    		//get Spring application context
    		ApplicationContext ctx =
                         WebApplicationContextUtils.getWebApplicationContext(servletContext);
    		customerBo= ctx.getBean("customerBo",CustomerBo.class);

    		String result = customerBo.getMsg();

    		return Response.status(200).entity(result).build();

    	}

    }

## 3.2 Implement ApplicationContextAware

The second solution is create a class implement Spring `ApplicationContextAware`, and make it singleton, to prevent instantiation from other classes.

    package com.mkyong.context;

    import org.springframework.beans.BeansException;
    import org.springframework.context.ApplicationContext;
    import org.springframework.context.ApplicationContextAware;

    public class SpringApplicationContext implements ApplicationContextAware {

    	private static ApplicationContext appContext;

    	// Private constructor prevents instantiation from other classes
            private SpringApplicationContext() {}

    	@Override
    	public void setApplicationContext(ApplicationContext applicationContext)
    			throws BeansException {
    		appContext = applicationContext;

    	}

    	public static Object getBean(String beanName) {
    		return appContext.getBean(beanName);
    	}

    }

Remember register this bean, otherwise, Spring container will not aware of this class.

_File : applicationContext.xml_

    <beans xmlns="http://www.springframework.org/schema/beans"
    	xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
    	xsi:schemaLocation="http://www.springframework.org/schema/beans
    	http://www.springframework.org/schema/beans/spring-beans-3.0.xsd ">

    	<bean class="com.mkyong.context.SpringApplicationContext"></bean>

    	<bean id="customerBo" class="com.mkyong.customer.impl.CustomerBoImpl">
            </bean>

    </beans>

In REST service, you can use the new singleton class – “`SpringApplicationContext`“, to get bean from Spring container.

    package com.mkyong.rest;

    import javax.ws.rs.GET;
    import javax.ws.rs.Path;
    import javax.ws.rs.core.Response;
    import com.mkyong.context.SpringApplicationContext;
    import com.mkyong.customer.CustomerBo;

    @Path("/customer")
    public class PrintService {

    	CustomerBo customerBo;

    	@GET
    	@Path("/print")
    	public Response printMessage() {

    		customerBo = (CustomerBo) SpringApplicationContext.getBean("customerBo");

    		String result = customerBo.getMsg();

    		return Response.status(200).entity(result).build();

    	}

    }

## 4\. Integrate RESTEasy with Spring

To integrate both in a web application, add Spring “`ContextLoaderListener`” in your **web.xml**.

_File :web.xml_

    <web-app id="WebApp_ID" version="2.4"
    	xmlns="http://java.sun.com/xml/ns/j2ee"
    	xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
    	xsi:schemaLocation="http://java.sun.com/xml/ns/j2ee
    	http://java.sun.com/xml/ns/j2ee/web-app_2_4.xsd">
    	<display-name>Restful Web Application</display-name>

    	<context-param>
    		<param-name>resteasy.resources</param-name>
    		<param-value>com.mkyong.rest.PrintService</param-value>
    	</context-param>

    	<listener>
    		<listener-class>
    			org.jboss.resteasy.plugins.server.servlet.ResteasyBootstrap
                    </listener-class>
    	</listener>

    	<listener>
    		<listener-class>
                            org.springframework.web.context.ContextLoaderListener
                    </listener-class>
    	</listener>

    	<servlet>
    		<servlet-name>resteasy-servlet</servlet-name>
    		<servlet-class>
    			org.jboss.resteasy.plugins.server.servlet.HttpServletDispatcher
                    </servlet-class>
    	</servlet>

    	<servlet-mapping>
    		<servlet-name>resteasy-servlet</servlet-name>
    		<url-pattern>/*</url-pattern>
    	</servlet-mapping>

    </web-app>

## 5\. Demo

Both solution 1 and 2 will generate following same output :

![result](http://www.mkyong.com/wp-content/uploads/2011/07/resteasy-spring-demo.png)

**Note**  
Again, if you understand this [RESTEasy Spring guide](http://docs.jboss.org/resteasy/docs/2.2.1.GA/userguide/html/RESTEasy_Spring_Integration.html), or has a better solution, please share it to me.

[http://www.mkyong.com/webservices/jax-rs/resteasy-spring-integration-example/](http://www.mkyong.com/webservices/jax-rs/resteasy-spring-integration-example/)
