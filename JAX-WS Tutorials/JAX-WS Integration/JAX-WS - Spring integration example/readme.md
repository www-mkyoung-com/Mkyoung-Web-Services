Here’s a guide to show you how to integrate Spring with JAX-WS, as mention in this link : [http://jax-ws-commons.java.net/spring/](http://jax-ws-commons.java.net/spring/). Upon finishing this tutorial, you will create a simple HelloWorld web service (JAX-WS), and DI a bean into the web service via Spring.

## 1\. Project Folder

See the final project folder structure.

![jaxws-spring-folder-structure](http://www.mkyong.com/wp-content/uploads/2011/03/jaxws-spring-folder-structure.png)

## 2\. Project Dependencies

Use Maven to get all the library dependencies. The key to integrate Spring with JAX-WS is via **jaxws-spring.jar**.

_File : pom.xml_

    <project xmlns="http://maven.apache.org/POM/4.0.0"
      xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
      xsi:schemaLocation="http://maven.apache.org/POM/4.0.0
      http://maven.apache.org/maven-v4_0_0.xsd">
      <modelVersion>4.0.0</modelVersion>
      <groupId>com.mkyong</groupId>
      <artifactId>WebServicesExample</artifactId>
      <packaging>war</packaging>
      <version>1.0-SNAPSHOT</version>
      <name>WebServicesExample Maven Webapp</name>
      <url>http://maven.apache.org</url>

      <repositories>
        <repository>
          <id>java.net</id>
          <url>http://download.java.net/maven/2</url>
        </repository>
      </repositories>

      <dependencies>

            <dependency>
                    <groupId>junit</groupId>
                    <artifactId>junit</artifactId>
                    <version>3.8.1</version>
                    <scope>test</scope>
            </dependency>

    	<!-- Spring framework -->
    	<dependency>
    		<groupId>org.springframework</groupId>
    		<artifactId>spring</artifactId>
    		<version>2.5.6</version>
    	</dependency>

            <!-- JAX-WS -->
    	<dependency>
    	  	<groupId>com.sun.xml.ws</groupId>
    	        <artifactId>jaxws-rt</artifactId>
    	        <version>2.2.3</version>
    	</dependency>

     	<!-- Library from java.net, integrate Spring with JAX-WS -->
    	<dependency>
    		<groupId>org.jvnet.jax-ws-commons.spring</groupId>
    		<artifactId>jaxws-spring</artifactId>
    		<version>1.8</version>
    		<exclusions>
    		  <exclusion>
               		<groupId>org.springframework</groupId>
          			<artifactId>spring-core</artifactId>
            	  </exclusion>
            	  <exclusion>
               		<groupId>org.springframework</groupId>
          			<artifactId>spring-context</artifactId>
            	  </exclusion>
            	  <exclusion>
               		<groupId>com.sun.xml.stream.buffer</groupId>
          			<artifactId>streambuffer</artifactId>
            	  </exclusion>
            	  <exclusion>
               		<groupId>org.jvnet.staxex</groupId>
          			<artifactId>stax-ex</artifactId>
            	  </exclusion>
    		</exclusions>
    	</dependency>

      </dependencies>
      <build>
        <finalName>web services</finalName>
        <plugins>
           <plugin>
               <groupId>org.apache.maven.plugins</groupId>
               <artifactId>maven-compiler-plugin</artifactId>
               <version>2.3.1</version>
               <configuration>
                   <source>1.6</source>
                   <target>1.6</target>
               </configuration>
           </plugin>
        </plugins>
      </build>
    </project>

**Note**  
The jaxws-spring’s pom.xml has a lot of unnecessary dependencies, you may need to exclude it via </exclusions> tag.

## 3\. JAX-WS Hello World

A simple JAX-WS example, and dependency inject (DI) “HelloWorldBo” via Spring.

_File : HelloWorldWS.java_

    package com.mkyong.ws;

    import javax.jws.WebMethod;
    import javax.jws.WebService;

    import com.mkyong.bo.HelloWorldBo;

    @WebService
    public class HelloWorldWS{

    	//DI via Spring
    	HelloWorldBo helloWorldBo;

    	@WebMethod(exclude=true)
    	public void setHelloWorldBo(HelloWorldBo helloWorldBo) {
    		this.helloWorldBo = helloWorldBo;
    	}

    	@WebMethod(operationName="getHelloWorld")
    	public String getHelloWorld() {

    		return helloWorldBo.getHelloWorld();

    	}

    }

## 4\. Beans

Here’s the HelloWorldBo class, with a `getHelloWorld()` method to return a simple string.

_File : HelloWorldBo.java_

    package com.mkyong.bo;

    public interface HelloWorldBo{

    	String getHelloWorld();

    }

_File : HelloWorldBoImpl.java_

    package com.mkyong.bo.impl;

    import com.mkyong.bo.HelloWorldBo;

    public class HelloWorldBoImpl implements HelloWorldBo{

    	public String getHelloWorld(){
    		return "JAX-WS + Spring!";
    	}

    }

## 5\. Spring Beans Configuration

Spring beans configuration file to bind URL pattern “**/hello**” to “**com.mkyong.ws.HelloWorldWS**” web service class.

_File : applicationContext.xml_

    <?xml version="1.0" encoding="UTF-8"?>
    <beans xmlns="http://www.springframework.org/schema/beans"
           xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
           xmlns:ws="http://jax-ws.dev.java.net/spring/core"
           xmlns:wss="http://jax-ws.dev.java.net/spring/servlet"
           xsi:schemaLocation="http://www.springframework.org/schema/beans
           http://www.springframework.org/schema/beans/spring-beans-2.5.xsd
           http://jax-ws.dev.java.net/spring/core
            http://jax-ws.dev.java.net/spring/core.xsd
            http://jax-ws.dev.java.net/spring/servlet
            http://jax-ws.dev.java.net/spring/servlet.xsd"
    >

        <wss:binding url="/hello">
            <wss:service>
                <ws:service bean="#helloWs"/>
            </wss:service>
        </wss:binding>

        <!-- Web service methods -->
        <bean id="helloWs" class="com.mkyong.ws.HelloWorldWS">
        	<property name="helloWorldBo" ref="HelloWorldBo" />
        </bean>

        <bean id="HelloWorldBo" class="com.mkyong.bo.impl.HelloWorldBoImpl" />

    </beans>

**Note**  
With this jaxws-spring integration mechanism, the **sun-jaxws.xml** file is no longer required.

## 6\. web.xml

In web.xml, declares “`com.sun.xml.ws.transport.http.servlet.WSSpringServlet`“, and link it to “`/hello`“.

    <web-app id="WebApp_ID" version="2.4"
    	xmlns="http://java.sun.com/xml/ns/j2ee"
    	xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
    	xsi:schemaLocation="http://java.sun.com/xml/ns/j2ee
    	http://java.sun.com/xml/ns/j2ee/web-app_2_4.xsd">

    	<display-name>Spring + JAX-WS</display-name>

    	<servlet>
        	        <servlet-name>jaxws-servlet</servlet-name>
        	       <servlet-class>
        		          com.sun.xml.ws.transport.http.servlet.WSSpringServlet
        	        </servlet-class>
      	</servlet>

    	<servlet-mapping>
                    <servlet-name>jaxws-servlet</servlet-name>
                    <url-pattern>/hello</url-pattern>
             </servlet-mapping>

             <!-- Register Spring Listener -->
      	<listener>
        	        <listener-class>
        		     org.springframework.web.context.ContextLoaderListener
        	        </listener-class>
      	</listener>

    </web-app>

## 7\. Demo

Start the project, and access the deployed web service via URL “**/hello**“, for example _http://localhost:8080/WebServicesExample/hello?wsdl_

![jaxws-spring-demo](http://www.mkyong.com/wp-content/uploads/2011/03/jaxws-spring-demo.png)

[http://www.mkyong.com/webservices/jax-ws/jax-ws-spring-integration-example/](http://www.mkyong.com/webservices/jax-ws/jax-ws-spring-integration-example/)
