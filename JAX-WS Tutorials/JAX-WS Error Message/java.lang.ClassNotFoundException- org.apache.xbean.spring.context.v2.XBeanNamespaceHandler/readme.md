## Problem

Developing jax-ws with Spring, using **jdk1.6** + **jaxws-spring-1.8.jar** + **Spring-2.5.6.jar**. See following Spring XML configuration file :

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

        <wss:binding url="/ws">
            <wss:service>
                <ws:service bean="#UserWs"/>
            </wss:service>
        </wss:binding>

        <!-- this bean implements web service methods -->
        <bean id="UserWs" class="com.mkyong.user.ws.UserWS">
            <property name="UserBo" ref="com.mkyong.user.bo.UserBo" />
        </bean>

    </beans>

But, it hits following error message :

    java.lang.ClassNotFoundException: org.apache.xbean.spring.context.v2.XBeanNamespaceHandler

## Solution

The “`org.apache.xbean.spring.context.v2.XBeanNamespaceHandler`” is belong to **xbean-spring.jar**. You can get it from Maven central repository [here](http://repo1.maven.org/maven2/org/apache/xbean/xbean-spring/) or declare xbean’s dependency in your pom.xml file.

_File : pom.xml_

    <dependency>
    	<artifactId>xbean</artifactId>
    	<groupId>org.apache.xbean</groupId>
    	<version>3.7</version>
    </dependency>

[http://www.mkyong.com/webservices/jax-ws/java-lang-classnotfoundexception-org-apache-xbean-spring-context-v2-xbeannamespacehandler/](http://www.mkyong.com/webservices/jax-ws/java-lang-classnotfoundexception-org-apache-xbean-spring-context-v2-xbeannamespacehandler/)
