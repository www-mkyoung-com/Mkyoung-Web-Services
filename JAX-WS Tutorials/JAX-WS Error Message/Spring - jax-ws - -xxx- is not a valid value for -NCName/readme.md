## Problem

Here’s the Spring + JAX-WS configuration file …

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

        <wss:binding url="/ws/OrderWs">
            <wss:service>
                <ws:service bean="#orderWs"/>
            </wss:service>
        </wss:binding>

        <!-- this bean implements web service methods -->
        <bean id="#orderWs" class="com.mkyong.order.ws.OrderWS">
            <property name="orderBo" ref="OrderBo" />
        </bean>

    </beans>

During server start up, it hits following error message :

    Caused by: org.xml.sax.SAXParseException: cvc-datatype-valid.1.2.1:
    	'#orderWs' is not a valid value for 'NCName'.

## Solution

The SAX parser’s error message is misleading, it should be ‘**#orderWs’ is not exists**‘!. A very common typo mistake, an extra ‘#’ in the Spring’s bean configuration. The correct configuration should be like this :

    <wss:binding url="/ws/OrderWs">
        <wss:service>
            <ws:service bean="#orderWs"/>
        </wss:service>
    </wss:binding>

    <!-- orderWs, not #orderWs -->
    <bean id="orderWs" class="com.mkyong.order.ws.OrderWS">
        <property name="orderBo" ref="OrderBo" />
    </bean>

[http://www.mkyong.com/webservices/jax-ws/spring-jax-ws-xxx-is-not-a-valid-value-for-ncname/](http://www.mkyong.com/webservices/jax-ws/spring-jax-ws-xxx-is-not-a-valid-value-for-ncname/)
