One of the common way to handle authentication in JAX-WS is client provides “username” and “password”, attached it in SOAP request header and send to server, server parse the SOAP document and retrieve the provided “username” and “password” from request header and do validation from database, or whatever method prefer.

In this article, we show you how to implement the above “**application level authentication in JAX-WS**“.

## Ideas…

On the **web service client** site, just put your “username” and “password” into request header.

    Map<String, Object> req_ctx = ((BindingProvider)port).getRequestContext();
    req_ctx.put(BindingProvider.ENDPOINT_ADDRESS_PROPERTY, WS_URL);

    Map<String, List<String>> headers = new HashMap<String, List<String>>();
    headers.put("Username", Collections.singletonList("mkyong"));
    headers.put("Password", Collections.singletonList("password"));
    req_ctx.put(MessageContext.HTTP_REQUEST_HEADERS, headers);

On the **web service server** site, get the request header parameters via `WebServiceContext`.

    @Resource
        WebServiceContext wsctx;

        @Override
        public String method() {

            MessageContext mctx = wsctx.getMessageContext();

    //get detail from request headers
            Map http_headers = (Map) mctx.get(MessageContext.HTTP_REQUEST_HEADERS);
            List userList = (List) http_headers.get("Username");
            List passList = (List) http_headers.get("Password");

        //...

That’s all, now, your deployed JAX-WS is supported application level authentication.

## Authentication with JAX-WS Example

See a complete example.

## 1\. WebService Server

Create a simple JAX-WS hello world example to handle the authentication in application level.

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

_HelloWorldImpl.java_

    package com.mkyong.ws;

    import java.util.List;
    import java.util.Map;

    import javax.annotation.Resource;
    import javax.jws.WebService;
    import javax.xml.ws.WebServiceContext;
    import javax.xml.ws.handler.MessageContext;

    //Service Implementation Bean
    @WebService(endpointInterface = "com.mkyong.ws.HelloWorld")
    public class HelloWorldImpl implements HelloWorld{

        @Resource
        WebServiceContext wsctx;

        @Override
        public String getHelloWorldAsString() {

    	MessageContext mctx = wsctx.getMessageContext();

    	//get detail from request headers
            Map http_headers = (Map) mctx.get(MessageContext.HTTP_REQUEST_HEADERS);
            List userList = (List) http_headers.get("Username");
            List passList = (List) http_headers.get("Password");

            String username = "";
            String password = "";

            if(userList!=null){
            	//get username
            	username = userList.get(0).toString();
            }

            if(passList!=null){
            	//get password
            	password = passList.get(0).toString();
            }

            //Should validate username and password with database
            if (username.equals("mkyong") && password.equals("password")){
            	return "Hello World JAX-WS - Valid User!";
            }else{
            	return "Unknown User!";
            }

        }
    }

## 2\. EndPoint Publisher

Create an endpoint publisher to deploy above web service at this URL : “_http://localhost:9999/ws/hello_”

_File : HelloWorldPublisher.java_

    package com.mkyong.endpoint;

    import javax.xml.ws.Endpoint;
    import com.mkyong.ws.HelloWorldImpl;

    //Endpoint publisher
    public class HelloWorldPublisher{

        public static void main(String[] args) {
    	   Endpoint.publish("http://localhost:9999/ws/hello", new HelloWorldImpl());
        }

    }

## 3\. WebService Client

Create a web service client to send “username” and “password” for authentication.

_File : HelloWorldClient.java_

    package com.mkyong.client;

    import java.net.URL;
    import java.util.Collections;
    import java.util.HashMap;
    import java.util.List;
    import java.util.Map;

    import javax.xml.namespace.QName;
    import javax.xml.ws.BindingProvider;
    import javax.xml.ws.Service;
    import javax.xml.ws.handler.MessageContext;

    import com.mkyong.ws.HelloWorld;

    public class HelloWorldClient{

    	private static final String WS_URL = "http://localhost:9999/ws/hello?wsdl";

    	public static void main(String[] args) throws Exception {

    	URL url = new URL(WS_URL);
            QName qname = new QName("http://ws.mkyong.com/", "HelloWorldImplService");

            Service service = Service.create(url, qname);
            HelloWorld hello = service.getPort(HelloWorld.class);

            /*******************UserName & Password ******************************/
            Map<String, Object> req_ctx = ((BindingProvider)hello).getRequestContext();
            req_ctx.put(BindingProvider.ENDPOINT_ADDRESS_PROPERTY, WS_URL);

            Map<String, List<String>> headers = new HashMap<String, List<String>>();
            headers.put("Username", Collections.singletonList("mkyong"));
            headers.put("Password", Collections.singletonList("password"));
            req_ctx.put(MessageContext.HTTP_REQUEST_HEADERS, headers);
            /**********************************************************************/

            System.out.println(hello.getHelloWorldAsString());

        }
    }

_Output_

    Hello World JAX-WS - Valid User!

## 4\. Tracing SOAP Traffic

From top to bottom, showing how SOAP envelope flows between client and server.

1\. Client send request, the username “_mkyong_” and password “_password_” are included in the SOAP envelope.

    POST /ws/hello?wsdl HTTP/1.1
    Password: password
    Username: mkyong
    SOAPAction: ""
    Accept: text/xml, multipart/related, text/html, image/gif, image/jpeg, *; q=.2, */*; q=.2
    Content-Type: text/xml; charset=utf-8
    User-Agent: Java/1.6.0_13
    Host: localhost:8888
    Connection: keep-alive
    Content-Length: 178

    <?xml version="1.0" ?>
    	<S:Envelope xmlns:S="http://schemas.xmlsoap.org/soap/envelope/">
    		<S:Body>
    			<ns2:getHelloWorldAsString xmlns:ns2="http://ws.mkyong.com/"/>
    		</S:Body>
    	</S:Envelope>

2\. Server send back a normal response.

    HTTP/1.1 200 OK
    Transfer-encoding: chunked
    Content-type: text/xml; charset=utf-8

    <?xml version="1.0" ?>
    	<S:Envelope xmlns:S="http://schemas.xmlsoap.org/soap/envelope/">
    		<S:Body>
    			<ns2:getHelloWorldAsStringResponse xmlns:ns2="http://ws.mkyong.com/">
    				<return>Hello World JAX-WS - Valid User!</return>
    			</ns2:getHelloWorldAsStringResponse>
    		</S:Body>
    	</S:Envelope>

Done.

[http://www.mkyong.com/webservices/jax-ws/application-authentication-with-jax-ws/](http://www.mkyong.com/webservices/jax-ws/application-authentication-with-jax-ws/)
