In this article, we show you how to implement **container authentication with JAX-WS**, under Tomcat 6.0\. In this way, the authentication is declarative rather than programmatic like this – [application authentication in JAX-WS](http://www.mkyong.com/webservices/jax-ws/application-authentication-with-jax-ws/). And Tomcat implement the container authentication via [security realm](http://tomcat.apache.org/tomcat-6.0-doc/realm-howto.html).

At the end of this article, the deployed web service will authenticate user based on the authentication data stored in Tomcat’s `conf/tomcat-users.xml` file.

## 1\. WebService

Create a simple JAX-WS, RPC style.

_File : UserProfile.java_

    package com.mkyong.ws;

    import javax.jws.WebMethod;
    import javax.jws.WebService;
    import javax.jws.soap.SOAPBinding;
    import javax.jws.soap.SOAPBinding.Style;

    //Service Endpoint Interface
    @WebService
    @SOAPBinding(style = Style.RPC)
    public interface UserProfile{

    	@WebMethod
    	String getUserName();

    }

_File : UserProfileImpl.java_

    package com.mkyong.ws;

    import javax.jws.WebService;

    //Service Implementation Bean
    @WebService(endpointInterface = "com.mkyong.ws.UserProfile")
    public class UserProfileImpl implements UserProfile{

    	@Override
    	public String getUserName() {

    		return "getUserName() : returned value";

    	}

    }

## 2\. web.xml

Configure a security role “operator”, make url “/user” required basic http authentication. See below `web.xml` file, self-explanatory.

_File : web.xml_

    <?xml version="1.0" encoding="UTF-8"?>
    <!DOCTYPE web-app PUBLIC "-//Sun Microsystems,
    Inc.//DTD Web Application 2.3//EN"
    "http://java.sun.com/dtd/web-app_2_3.dtd">

    <web-app>
        //...
        <security-role>
         	<description>Normal operator user</description>
         	<role-name>operator</role-name>
       	</security-role>

    	<security-constraint>
          	<web-resource-collection>
            	<web-resource-name>Operator Roles Security</web-resource-name>
            	<url-pattern>/user</url-pattern>
          	</web-resource-collection>

          	<auth-constraint>
            	<role-name>operator</role-name>
          	</auth-constraint>
          	<user-data-constraint>
              	<transport-guarantee>NONE</transport-guarantee>
          	</user-data-constraint>
       	</security-constraint>

    	<login-config>
          	<auth-method>BASIC</auth-method>
       	</login-config>

        <servlet-mapping>
            <servlet-name>user</servlet-name>
            <url-pattern>/user</url-pattern>
        </servlet-mapping>
        //...
    </web-app>

**Note**  
In production, it’s recommended to set the transport guarantee to “**CONFIDENTIAL**“, so that any access to resources via normal http request, such as _http://localhost:8080/ws/user_, Tomcat will redirect the request to https request _https://localhost:8443/ws/user_. Of course, the redirect https can be configure in The Tomcat’s `conf/server.xml`.

    <user-data-constraint>
        <transport-guarantee>CONFIDENTIAL</transport-guarantee>
    </user-data-constraint>

See this article – [Make Tomcat to support SSL or https connection](http://www.mkyong.com/tomcat/how-to-configure-tomcat-to-support-ssl-or-https/)

## 3\. Tomcat Users

Add new role, username and password in `$Tomcat/conf/tomcat-users.xml` file. In this case, add new user “mkyong”,”123456″ and attached it to a role named “operator”.

_File : $Tomcat/conf/tomcat-users.xml_

    <?xml version='1.0' encoding='utf-8'?>
    <tomcat-users>
      <role rolename="tomcat"/>
      <role rolename="operator"/>
      <user username="tomcat" password="tomcat" roles="tomcat"/>
      <user username="mkyong" password="123456" roles="operator"/>
      <user name="admin" password="admin" roles="admin,manager" />
    </tomcat-users>

## 4\. Tomcat Realm

Configure security realm in `$Tomcat/conf/server.xml` file. In this case, uses default `UserDatabaseRealm` to read the authentication information in `$Tomcat/conf/tomcat-users.xml`.

_File : $Tomcat/conf/server.xml_

    <GlobalNamingResources>

      <Resource name="UserDatabase" auth="Container"
                type="org.apache.catalina.UserDatabase"
                description="User database that can be updated and saved"
                factory="org.apache.catalina.users.MemoryUserDatabaseFactory"
                pathname="conf/tomcat-users.xml" />
    </GlobalNamingResources>

    <Realm className="org.apache.catalina.realm.UserDatabaseRealm"
               resourceName="UserDatabase"/>

## 5\. Deploy JAX-WS web service on Tomcat

See this detail guide on [how to deploy JAX-WS web services on Tomcat](http://www.mkyong.com/webservices/jax-ws/deploy-jax-ws-web-services-on-tomcat/).

## 6\. Testing

Now, any access to your deployed web service is required username and password authentication, see figure :  
_URL : http://localhost:8080/WebServiceExample/user_

![jaxws-container-authentication--example](http://www.mkyong.com/wp-content/uploads/2010/12/jaxws-container-authentication-example.png)

## 7\. WebService Client

To access the deployed web service, bind a correct username and password like this :

    UserProfile port = service.getPort(UserProfile.class);
    BindingProvider bp = (BindingProvider) port;
    bp.getRequestContext().put(BindingProvider.USERNAME_PROPERTY, "mkyong");
    bp.getRequestContext().put(BindingProvider.PASSWORD_PROPERTY, "123456");

_File : WsClient.java_

    package com.mkyong.client;

    import java.net.URL;
    import javax.xml.namespace.QName;
    import javax.xml.ws.BindingProvider;
    import javax.xml.ws.Service;

    import com.mkyong.ws.UserProfile;

    public class WsClient{

            //can't parse wsdl "http://localhost:8080/WebServiceExample/user.wsdl" directly
    	//save it as local file, and parse it
    	private static final String WS_URL = "file:c://user.wsdl";

    	public static void main(String[] args) throws Exception {

    	URL url = new URL(WS_URL);
            QName qname = new QName("http://ws.mkyong.com/", "UserProfileImplService");

            Service service = Service.create(url, qname);
            UserProfile port = service.getPort(UserProfile.class);

            //add username and password for container authentication
            BindingProvider bp = (BindingProvider) port;
            bp.getRequestContext().put(BindingProvider.USERNAME_PROPERTY, "mkyong");
            bp.getRequestContext().put(BindingProvider.PASSWORD_PROPERTY, "123456");

            System.out.println(port.getUserName());

        }

    }

_output_

    getUserName() : returned value

**Note**  
For those clients provided an invalid username or password, Tomcat will return following exception :

    Exception in thread "main" com.sun.xml.internal.ws.client.ClientTransportException:
    	request requires HTTP authentication: Unauthorized

Done.

[http://www.mkyong.com/webservices/jax-ws/container-authentication-with-jax-ws-tomcat/](http://www.mkyong.com/webservices/jax-ws/container-authentication-with-jax-ws-tomcat/)
