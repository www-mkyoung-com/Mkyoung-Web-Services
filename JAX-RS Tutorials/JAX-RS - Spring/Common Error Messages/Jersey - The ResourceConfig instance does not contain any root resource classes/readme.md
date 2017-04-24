## Problem

Deploying Jersey REST service, hit following error message on Tomcat.

    SEVERE: Servlet /RESTfulExample threw load() exception
    com.sun.jersey.api.container.ContainerException:
    	The ResourceConfig instance does not contain any root resource classes.

            //... code omitted

Here’s the `web.xml`

    <web-app ...>

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
    		<url-pattern>/*</url-pattern>
    	</servlet-mapping>

    </web-app>

## Solution

Many reasons causing this “**ResourceConfig**” error message. Few solutions that i know :

1.  **com.sun.jersey.config.property.packages** doesn’t exist in your `web.xml`.
2.  **com.sun.jersey.config.property.packages** included a resource that doesn’t include any jersey services. In above case, `"com.mkyong.rest`” doesn’t contains any jersey services.

[http://www.mkyong.com/webservices/jax-rs/jersey-the-resourceconfig-instance-does-not-contain-any-root-resource-classes/](http://www.mkyong.com/webservices/jax-rs/jersey-the-resourceconfig-instance-does-not-contain-any-root-resource-classes/)
