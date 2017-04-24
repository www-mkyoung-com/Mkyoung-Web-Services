A guide to show you how to configure Tomcat 6.0 to support SSL or https connection.

## 1\. Generate Keystore

First, uses “`keytool`” command to create a self-signed certificate. During the keystore creation process, you need to assign a password and fill in the certificate’s detail.

    $Tomcat\bin>keytool -genkey -alias mkyong -keyalg RSA -keystore c:\mkyongkeystore
    Enter keystore password:
    Re-enter new password:
    What is your first and last name?
      [Unknown]:  yong mook kim
    What is the name of your organizational unit?
      //omitted to save space
      [no]:  yes

    Enter key password for <mkyong>
            (RETURN if same as keystore password):
    Re-enter new password:

    $Tomcat\bin>

Here, you just created a certificate named “**mkyongkeystore**“, which locate at “**c:\**“.

**Certificate Details**  
You can use same “`keytool`” command to list the existing certificate’s detail

    $Tomcat\bin>keytool -list -keystore c:\mkyongkeystore
    Enter keystore password:

    Keystore type: JKS
    Keystore provider: SUN

    Your keystore contains 1 entry

    mkyong, 14 Disember 2010, PrivateKeyEntry,
    Certificate fingerprint (MD5): C8:DD:A1:AF:9F:55:A0:7F:6E:98:10:DE:8C:63:1B:A5

    $Tomcat\bin>

## 2\. Connector in server.xml

Next, locate your Tomcat’s server configuration file at _$Tomcat\conf\server.xml_, modify it by adding a **connector** element to support for SSL or https connection.

_File : $Tomcat\conf\server.xml_

    //...
    <!-- Define a SSL HTTP/1.1 Connector on port 8443
            This connector uses the JSSE configuration, when using APR, the
            connector should be using the OpenSSL style configuration
            described in the APR documentation -->

    <Connector port="8443" protocol="HTTP/1.1" SSLEnabled="true"
                  maxThreads="150" scheme="https" secure="true"
                  clientAuth="false" sslProtocol="TLS"
    	       keystoreFile="c:\mkyongkeystore"
    	       keystorePass="password" />
     //...

**Note**  
`keystorePass="password"` is the password you assigned to your keystore via “`keytool`” command.

## 3\. Done

Saved it and restart Tomcat, access to _https://localhost:8443/_

![tomcat-ssl-configuration](http://www.mkyong.com/wp-content/uploads/2010/12/tomcat-ssl-configuration.png)

In this example, we are using Google Chrome to access the Tomcat configured SSL site, and you may notice a crossed icon appear before the https protocol :), this is caused by the self-signed certificate and Google chrome just do not trust it.

In production environment, you should consider buy a signed certificate from trusted SSL service provider like [verisign](http://www.verisign.com/ssl/) or sign it with your own CA server

## Reference

1.  [Tomcat 6 : SSL configuration HOW-TO](http://tomcat.apache.org/tomcat-6.0-doc/ssl-howto.html)

[http://www.mkyong.com/tomcat/how-to-configure-tomcat-to-support-ssl-or-https/](http://www.mkyong.com/tomcat/how-to-configure-tomcat-to-support-ssl-or-https/)
