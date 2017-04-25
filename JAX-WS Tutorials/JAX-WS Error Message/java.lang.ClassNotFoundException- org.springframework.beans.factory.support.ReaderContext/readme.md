## Problem

Integrating jax-ws with Spring, using **xbean-spring-2.8.jar** + **Spring-2.5.6.jar**. While server is starting up, it hits

    java.lang.ClassNotFoundException: org.springframework.beans.factory.support.ReaderContext

## Solution

The “`org.springframework.beans.factory.support.ReaderContext`” is no longer exist in Spring-2.5.x, it’s only exist in the old Spring version < 2.5.x. The solution is upgrade your **xbean-spring** to latest version , for example, v3.7.

You can get **xbean-spring.jar** from Maven central repository [here](http://repo1.maven.org/maven2/org/apache/xbean/xbean-spring/) or declare xbean’s dependency in your pom.xml file.

_File : pom.xml_

    <dependency>
    	<artifactId>xbean</artifactId>
    	<groupId>org.apache.xbean</groupId>
    	<version>3.7</version>
    </dependency>

[http://www.mkyong.com/webservices/jax-ws/java-lang-classnotfoundexception-org-springframework-beans-factory-support-readercontext/](http://www.mkyong.com/webservices/jax-ws/java-lang-classnotfoundexception-org-springframework-beans-factory-support-readercontext/)
