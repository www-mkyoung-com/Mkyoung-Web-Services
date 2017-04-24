## Problem

Configured [Tomcat to support SSL](http://www.mkyong.com/tomcat/how-to-configure-tomcat-to-support-ssl-or-https/) and deployed this [simple hello world web service](http://www.mkyong.com/webservices/jax-ws/jax-ws-hello-world-example/). And use following client connect to the deployed web service over SSL connection :

    package com.mkyong.client;

    import java.net.URL;
    import javax.xml.namespace.QName;
    import javax.xml.ws.Service;

    import com.mkyong.ws.HelloWorld;

    public class HelloWorldClient{

    	public static void main(String[] args) throws Exception {

    	URL url = new URL("https://localhost:8443/HelloWorld/hello?wsdl");
            QName qname = new QName("http://ws.mkyong.com/", "HelloWorldImplService");

            Service service = Service.create(url, qname);
            HelloWorld hello = service.getPort(HelloWorld.class);
            System.out.println(hello.getHelloWorldAsString());

        }
    }

It hits “**No name matching localhost found**” exception :

    Caused by: javax.net.ssl.SSLHandshakeException:
        java.security.cert.CertificateException: No name matching localhost found
    	at com.sun.net.ssl.internal.ssl.Alerts.getSSLException(Alerts.java:174)
    	at com.sun.net.ssl.internal.ssl.SSLSocketImpl.fatal(SSLSocketImpl.java:1611)
    	at com.sun.net.ssl.internal.ssl.Handshaker.fatalSE(Handshaker.java:187)
    	at com.sun.net.ssl.internal.ssl.Handshaker.fatalSE(Handshaker.java:181)
    	......
    Caused by: java.security.cert.CertificateException: No name matching localhost found
    	at sun.security.util.HostnameChecker.matchDNS(HostnameChecker.java:210)
    	at sun.security.util.HostnameChecker.match(HostnameChecker.java:77)
    	......

## Solution

This problem and solution is well explained in this [article](http://docs.sun.com/app/docs/doc/820-1072/6ncp48v40?a=view#ahicy), you can use a Transport Security (SSL) Workaround for your “_localhost_” development environment.

To fix it, add a `javax.net.ssl.HostnameVerifier()` method to override the existing hostname verifier like this :

    package com.mkyong.client;

    import java.net.URL;
    import javax.xml.namespace.QName;
    import javax.xml.ws.Service;

    import com.mkyong.ws.HelloWorld;

    public class HelloWorldClient{

    	static {
    	    //for localhost testing only
    	    javax.net.ssl.HttpsURLConnection.setDefaultHostnameVerifier(
    	    new javax.net.ssl.HostnameVerifier(){

    	        public boolean verify(String hostname,
    	                javax.net.ssl.SSLSession sslSession) {
    	            if (hostname.equals("localhost")) {
    	                return true;
    	            }
    	            return false;
    	        }
    	    });
    	}

    	public static void main(String[] args) throws Exception {

    	URL url = new URL("https://localhost:8443/HelloWorld/hello?wsdl");
            QName qname = new QName("http://ws.mkyong.com/", "HelloWorldImplService");

            Service service = Service.create(url, qname);
            HelloWorld hello = service.getPort(HelloWorld.class);
            System.out.println(hello.getHelloWorldAsString());

        }
    }

_Output_

    Hello World JAX-WS

It’s working fine now.

[http://www.mkyong.com/webservices/jax-ws/java-security-cert-certificateexception-no-name-matching-localhost-found/](http://www.mkyong.com/webservices/jax-ws/java-security-cert-certificateexception-no-name-matching-localhost-found/)
