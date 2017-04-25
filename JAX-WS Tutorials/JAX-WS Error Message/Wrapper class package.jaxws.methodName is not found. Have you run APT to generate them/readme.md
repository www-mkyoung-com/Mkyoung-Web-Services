## Problem

In JAX-WS development, when following service endpoint is deploying,

_File : HelloWorld.java_

    package com.mkyong.ws;
    //Service Endpoint Interface
    @WebService
    public interface HelloWorld{

    	@WebMethod String getHelloWorldAsString();
    }

_File : HelloWorldImpl.java_

    //Service Implementation
    package com.mkyong.ws;
    @WebService(endpointInterface = "com.mkyong.ws.HelloWorld")
    public class HelloWorldImpl implements HelloWorld{

    	@Override
    	public String getHelloWorldAsString() {
    		//...
    	}

    }

It hits following error message immediately?

    Exception in thread "main" com.sun.xml.internal.ws.model.RuntimeModelerException:
    	runtime modeler error:

            Wrapper class com.mkyong.ws.jaxws.GetHelloWorldAsString is not found.
            Have you run APT to generate them?

    	at com.sun.xml.internal.ws.model.RuntimeModeler.getClass(RuntimeModeler.java:256)
    	//...

## Solution

The service endpoint interface is not annotated with any `@SOAPBinding`, so, it uses the default **document style** to publish it. For human readability, you can rewrite it as following :

    //Service Endpoint Interface
    @WebService
    @SOAPBinding(style = Style.DOCUMENT, use=Use.LITERAL)
    public interface HelloWorld{

    	@WebMethod String getHelloWorldAsString();
    }

In document style, you need to use “**wsgen**” tool to generate all the necessary JAX-WS portable artifacts (mapping classes, wsdl or xsd schema) for the service publication.

## wsgen command

The **wsgen** command is required to read the service endpoint implementation class :

    wsgen -keep -cp . com.mkyong.ws.HelloWorldImpl

It generates two classes for a single `getHelloWorldAsString()` method, under **package.jaxws** folder.

1.  GetHelloWorldAsString.java
2.  GetHelloWorldAsStringResponse.java

Copy those classes to correct folder, in this case, it’s “**com.mkyong.ws.jaxws**“. Try publish it again.

## Reference

1.  [wsgen tool documentation](http://download.oracle.com/javase/6/docs/technotes/tools/share/wsgen.html)

[http://www.mkyong.com/webservices/jax-ws/wrapper-class-package-jaxws-methodname-is-not-found-have-you-run-apt-to-generate-them/](http://www.mkyong.com/webservices/jax-ws/wrapper-class-package-jaxws-methodname-is-not-found-have-you-run-apt-to-generate-them/)
