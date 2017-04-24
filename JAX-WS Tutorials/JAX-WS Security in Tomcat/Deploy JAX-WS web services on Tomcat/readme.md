Here’s a guide to show you how to **deploy JAX-WS web services on Tomcat** servlet container. See following summary steps of a web service deployment.

1.  Create a web service (of course).
2.  Create a **sun-jaxws.xml**, defines web service implementation class.
3.  Create a standard **web.xml**, defines `WSServletContextListener`, `WSServlet` and structure of a web project.
4.  Build tool to generate WAR file.
5.  Copy JAX-WS dependencies to “${Tomcat}/lib” folder.
6.  Copy WAR to “${Tomcat}/webapp” folder.
7.  Start It.

Directory structure of this example, so that you know where to put your files.

![jaxws-deploy-tomcat--folder](http://www.mkyong.com/wp-content/uploads/2010/11/jaxws-deploy-tomcat-folder.png)

## 1\. WebServices

A simple JAX-WS hello world example.

_File : HelloWorld.java_

    package com.mkyong.ws;

    import javax.jws.WebMethod;
    import javax.jws.WebService;
    import javax.jws.soap.SOAPBinding;
    import javax.jws.soap.SOAPBinding.Style;

    //Service Endpoint Interface
    @WebService
    @SOAPBinding(style = Style.RPC)
    public interface HelloWorld{

    	@WebMethod String getHelloWorldAsString();

    }

_File : HelloWorldImpl.java_

    package com.mkyong.ws;

    import javax.jws.WebService;

    //Service Implementation Bean

    @WebService(endpointInterface = "com.mkyong.ws.HelloWorld")
    public class HelloWorldImpl implements HelloWorld{

    	@Override
    	public String getHelloWorldAsString() {
    		return "Hello World JAX-WS";
    	}
    }

Later, you will deploy this hello world web service on Tomcat.

## 2\. sun-jaxws.xml

Create a web service deployment descriptor, which is also known as **JAX-WS RI deployment descriptor** – sun-jaxws.xml.

_File : sun-jaxws.xml_

    <?xml version="1.0" encoding="UTF-8"?>
    <endpoints
      xmlns="http://java.sun.com/xml/ns/jax-ws/ri/runtime"
      version="2.0">
      <endpoint
          name="HelloWorld"
          implementation="com.mkyong.ws.HelloWorldImpl"
          url-pattern="/hello"/>
    </endpoints>

When user access **/hello/** URL path, it will fire the declared web service, which is `HelloWorldImpl.java`.

**Note**  
For detail endpoint attributes , see this [article](http://jax-ws.java.net/nonav/2.1.4/docs/jaxws-war.html).

## 3\. web.xml

Create a standard web.xml **deployment descriptor** for the deployment. Defines `WSServletContextListener` as listener class, `WSServlet` as your hello servlet.

_File : web.xml_

    <?xml version="1.0" encoding="UTF-8"?>
    <!DOCTYPE web-app PUBLIC "-//Sun Microsystems,
    Inc.//DTD Web Application 2.3//EN"
    "http://java.sun.com/j2ee/dtds/web-app_2_3.dtd">

    <web-app>
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
        <session-config>
            <session-timeout>120</session-timeout>
        </session-config>
    </web-app>

## 4\. WAR Content

Use Ant, Maven or JAR command to build a WAR file to include everything inside. The WAR content should look like this :

    WEB-INF/classes/com/mkyong/ws/HelloWorld.class
    WEB-INF/classes/com/mkyong/ws/HelloWorldImpl.class
    WEB-INF/web.xml
    WEB-INF/sun-jaxws.xml

**Note**  
For those who are interested, here’s the Ant file to build this project and generate the WAR file.

_File : build.xml_

    <project name="HelloWorldWS" default="dist" basedir=".">
        <description>
            Web Services build file
        </description>
      <!-- set global properties for this build -->
      <property name="src" location="src"/>
      <property name="build" location="build"/>
      <property name="dist"  location="dist"/>
      <property name="webcontent"  location="WebContent"/>

      <target name="init">
            <!-- Create the time stamp -->
            <tstamp/>
            <!-- Create the build directory structure used by compile -->
            <mkdir dir="${build}"/>
      </target>

      <target name="compile" depends="init"
      	description="compile the source " >
            <!-- Compile the java code from ${src} into ${build} -->
            <javac srcdir="${src}" destdir="${build}"/>
      </target>

      <target name="war" depends="compile"
      	description="generate the distribution war" >

    	<!-- Create the war distribution directory -->
      	<mkdir dir="${dist}/war"/>

      	<!-- Follow standard WAR structure -->
      	<copydir dest="${dist}/war/build/WEB-INF/" src="${webcontent}/WEB-INF/" />
      	<copydir dest="${dist}/war/build/WEB-INF/classes/" src="${build}" />

    	<jar jarfile="${dist}/war/HelloWorld-${DSTAMP}.war" basedir="${dist}/war/build/"/>
      </target>

    </project>

## 5\. JAX-WS Dependencies

By default, Tomcat does not comes with any **JAX-WS dependencies**, So, you have to include it manually.

1\. Go here [http://jax-ws.java.net/](http://jax-ws.java.net/).  
2\. Download JAX-WS RI distribution.  
3\. Unzip it and copy following JAX-WS dependencies to Tomcat library folder “**{$TOMCAT}/lib**“.

*   jaxb-impl.jar
*   jaxws-api.jar
*   jaxws-rt.jar
*   gmbal-api-only.jar
*   management-api.jar
*   stax-ex.jar
*   streambuffer.jar
*   policy.jar

## 6\. Deployment

Copy the generated **WAR** file to **{$TOMCAT}/webapps/** folder and start the Tomcat server.

For testing, you can access this URL : _http://localhost:8080/HelloWorld/hello_, if you see following page, it means web services are deploy successfully.

![jaxws-deploy-tomcat--example](http://www.mkyong.com/wp-content/uploads/2010/11/jaxws-deploy-tomcat-example.png)

[http://www.mkyong.com/webservices/jax-ws/deploy-jax-ws-web-services-on-tomcat/](http://www.mkyong.com/webservices/jax-ws/deploy-jax-ws-web-services-on-tomcat/)
