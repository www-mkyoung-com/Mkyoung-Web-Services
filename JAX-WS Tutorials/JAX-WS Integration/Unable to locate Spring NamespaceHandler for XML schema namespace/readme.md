## Problem

Integrate Spring with JAX-WS, according to this link: [http://jax-ws-commons.java.net/spring/](http://jax-ws-commons.java.net/spring/) . When start the web application, get this exception :

    org.springframework.beans.factory.parsing.BeanDefinitionParsingException:
    Configuration problem: Unable to locate Spring NamespaceHandler
    for XML schema namespace [http://jax-ws.dev.java.net/spring/servlet]

Here’s the Spring + JAX-WS configuration file.

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
        </bean>

    </beans>

## Solution

The link : [http://jax-ws-commons.java.net/spring/](http://jax-ws-commons.java.net/spring/)is lacking of information, you need `jaxws-spring.jar` to integrate Spring with JAX-WS.

1.  Get it from [java.net Maven repository](http://download.java.net/maven/2/org/jvnet/jax-ws-commons/spring/jaxws-spring/) directly.
2.  For Maven, declare jaxws-spring in pom.xml, like this

    <project ...>

      <repositories>
        <repository>
          <id>java.net</id>
          <url>http://download.java.net/maven/2</url>
        </repository>
      </repositories>

      <dependencies>

     	<!-- Exclude some unnecessary libraries -->
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

[http://www.mkyong.com/webservices/jax-ws/unable-to-locate-spring-namespacehandler-for-xml-schema-namespace-httpjax-ws-dev-java-netspringservlet/](http://www.mkyong.com/webservices/jax-ws/unable-to-locate-spring-namespacehandler-for-xml-schema-namespace-httpjax-ws-dev-java-netspringservlet/)
