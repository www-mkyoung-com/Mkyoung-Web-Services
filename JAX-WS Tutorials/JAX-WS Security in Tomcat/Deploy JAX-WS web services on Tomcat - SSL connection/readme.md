In this article, we show you how to deploy a JAX-WS web service on Tomcat with TLS / SSL or https secure connection enabled. Actually, the answer is quite simple, just deploys it as a normal web service and configured SSL connection on your Tomcat server properly :)

**Note**  
This article is just a combination of my last few posts on developing web service in SSL connection environment.

## 1\. Configure Tomcat + SSL

For detail, see this guide – [Make Tomcat to support SSL or https connection](http://www.mkyong.com/tomcat/how-to-configure-tomcat-to-support-ssl-or-https/).

Basically, just buy a certificate from trusted certificate provider, or use JDK’s `keytool` command to generate a dummy certificate for localhost testing. And put following portion into your Tomcat `server.xml` file.

_File : $Tomcat\conf\server.xml_

    //...
     <!-- Define a SSL HTTP/1.1 Connector on port 8443
             This connector uses the JSSE configuration, when using APR, the
             connector should be using the OpenSSL style configuration
             described in the APR documentation -->

     <Connector port="8443" protocol="HTTP/1.1" SSLEnabled="true"
                   maxThreads="150" scheme="https" secure="true"
                   clientAuth="false" sslProtocol="TLS"
    	       keystoreFile="c:\your keystore file"
    	       keystorePass="your keystore password" />
      //...

Restart Tomcat, and now, your Tomcat is supported SSL connection, e.g _https://localhost:8443_

## 2\. Deploy Web Service

Deploy it like a normal web service, see this guide – [Deploy JAX-WS web services on Tomcat servlet container](http://www.mkyong.com/webservices/jax-ws/deploy-jax-ws-web-services-on-tomcat/).

## 3\. Test It

The configuration is done; you can access the deployed web service in SSL connection by using a normal web service client.

For example,

    URL url = new URL("https://localhost:8443/HelloWorld/hello?wsdl");
    QName qname = new QName("http://ws.mkyong.com/", "HelloWorldImplService");
    Service service = Service.create(url, qname);

    HelloWorld hello = service.getPort(HelloWorld.class);
    System.out.println(hello.getHelloWorldAsString());

**Note**  
For localhost SSL testing environment, the client will hit following exceptions, please read the problem and solution below :

1.  [java.security.cert.CertificateException: No name matching localhost found](http://www.mkyong.com/webservices/jax-ws/java-security-cert-certificateexception-no-name-matching-localhost-found/)
2.  [SunCertPathBuilderException: unable to find valid certification path to requested target](http://www.mkyong.com/webservices/jax-ws/suncertpathbuilderexception-unable-to-find-valid-certification-path-to-requested-target/)

## 4\. Done

Your web service is in SSL protection, rather simple, no changes on the web service site; just configure your Tomcat to support SSL connection only.

Reference

1.  [Wiki – SSL connection](http://en.wikipedia.org/wiki/SSL)
2.  [JAX-WS hello world example](http://www.mkyong.com/webservices/jax-ws/jax-ws-hello-world-example/)

[http://www.mkyong.com/webservices/jax-ws/deploy-jax-ws-web-services-on-tomcat-ssl-connection/](http://www.mkyong.com/webservices/jax-ws/deploy-jax-ws-web-services-on-tomcat-ssl-connection/)
