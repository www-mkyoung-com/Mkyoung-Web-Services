Often times, JAX-WS always be part of your Java web application. Here we show you how to integrate JAX-WS into Java web application easily.

## 1\. Project Folder

First, review this project folder structure.

![jax-ws-web-application-example](http://www.mkyong.com/wp-content/uploads/2011/03/jax-ws-web-application-example-1.png)

## 2\. Web Service

A super simple web service. Code is self-explanatory.

_File : HelloWorld.java_

    package com.mkyong.ws;

    import javax.jws.WebMethod;
    import javax.jws.WebService;

    @WebService
    public class HelloWorld{

    	@WebMethod(operationName="getHelloWorld")
    	public String getHelloWorld(String name) {
    		return "Hello World JAX-WS " + name;
    	}

    }

## 3\. Web Service Deployment Descriptor (sun-jaxws.xml)

Create a web service deployment descriptor, named `sun-jaxws.xml`.

_File : sun-jaxws.xml_

    <?xml version="1.0" encoding="UTF-8"?>
    <endpoints
      xmlns="http://java.sun.com/xml/ns/jax-ws/ri/runtime"
      version="2.0">
      <endpoint
          name="HelloWorldWs"
          implementation="com.mkyong.ws.HelloWorld"
          url-pattern="/hello"/>
    </endpoints>

## 4\. Web Application Deployment Descriptor (web.xml)

In the standard `web.xml`,

1.  Defines “`com.sun.xml.ws.transport.http.servlet.WSServletContextListener`” as listener class.
2.  Defines “`com.sun.xml.ws.transport.http.servlet.WSServlet`” as your web service (hello) servlet.

_File : web.xml_

    <!DOCTYPE web-app PUBLIC
     "-//Sun Microsystems, Inc.//DTD Web Application 2.3//EN"
     "http://java.sun.com/dtd/web-app_2_3.dtd" >

    <web-app>
    	<display-name>Archetype Created Web Application</display-name>

    	<listener>
    		<listener-class>
    			com.sun.xml.ws.transport.http.servlet.WSServletContextListener
                    </listener-class>
    	</listener>
    	<servlet>
    		<servlet-name>hello</servlet-name>
    		<servlet-class>
    			com.sun.xml.ws.transport.http.servlet.WSServlet
                    </servlet-class>
    		<load-on-startup>1</load-on-startup>
    	</servlet>
    	<servlet-mapping>
    		<servlet-name>hello</servlet-name>
    		<url-pattern>/hello</url-pattern>
    	</servlet-mapping>

    </web-app>

## 5\. Done

The integration between JAX-WS and web application is done. Deploy it and access via URL : _http://localhost:8080/WebServicesExample/hello_

![jax-ws-web-application-example](http://www.mkyong.com/wp-content/uploads/2011/03/jax-ws-web-application-example-2.png)

[http://www.mkyong.com/webservices/jax-ws/jax-ws-java-web-application-integration-example/](http://www.mkyong.com/webservices/jax-ws/jax-ws-java-web-application-integration-example/)
