## Problem

Configured [Tomcat to support SSL](http://www.mkyong.com/tomcat/how-to-configure-tomcat-to-support-ssl-or-https/) and deployed this [web service](http://www.mkyong.com/webservices/jax-ws/jax-ws-hello-world-example/) on a development Tomcat server. While connect to the deployed web service over SSL connection via this URL : “**https://localhost:8443/HelloWorld/hello?wsdl**“, it hits

    javax.net.ssl.SSLHandshakeException:
       sun.security.validator.ValidatorException: PKIX path building failed:
       sun.security.provider.certpath.SunCertPathBuilderException:
       unable to find valid certification path to requested target

    Caused by: sun.security.validator.ValidatorException:
       PKIX path building failed: sun.security.provider.certpath.SunCertPathBuilderException:
       unable to find valid certification path to requested target

    Caused by: sun.security.provider.certpath.SunCertPathBuilderException:
       unable to find valid certification path to requested target

## Solution

The caused of the problem and solution are both well explain in this [article](http://blogs.sun.com/andreas/entry/no_more_unable_to_find). Below is just the same solution, but demonstrate in my development environment :)

## 1\. Get InstallCert.java

Get a `InstallCert.java` file from [http://blogs.sun.com/andreas/resource/InstallCert.java](http://blogs.sun.com/andreas/resource/InstallCert.java)

## 2\. Add Trusted Keystore

Run `InstallCert.java`, with your hostname and https port, and press “**1**” when ask for input. It will add your “localhost” as a trusted keystore, and generate a file named “**jssecacerts**“.

    C:\>java InstallCert localhost:8443
    Loading KeyStore C:\Program Files\Java\jre6\lib\security\cacerts...
    Opening connection to localhost:8443...
    Starting SSL handshake...

    javax.net.ssl.SSLHandshakeException: sun.security.validator.ValidatorException: PKIX path building failed: sun.security.
    provider.certpath.SunCertPathBuilderException: unable to find valid certification path to requested target
            at com.sun.net.ssl.internal.ssl.Alerts.getSSLException(Unknown Source)
            at com.sun.net.ssl.internal.ssl.SSLSocketImpl.fatal(Unknown Source)
            at com.sun.net.ssl.internal.ssl.Handshaker.fatalSE(Unknown Source)
            at com.sun.net.ssl.internal.ssl.Handshaker.fatalSE(Unknown Source)
            at com.sun.net.ssl.internal.ssl.ClientHandshaker.serverCertificate(Unknown Source)
            at com.sun.net.ssl.internal.ssl.ClientHandshaker.processMessage(Unknown Source)
            at com.sun.net.ssl.internal.ssl.Handshaker.processLoop(Unknown Source)
            at com.sun.net.ssl.internal.ssl.Handshaker.process_record(Unknown Source)
            at com.sun.net.ssl.internal.ssl.SSLSocketImpl.readRecord(Unknown Source)
            at com.sun.net.ssl.internal.ssl.SSLSocketImpl.performInitialHandshake(Unknown Source)
            at com.sun.net.ssl.internal.ssl.SSLSocketImpl.startHandshake(Unknown Source)
            at com.sun.net.ssl.internal.ssl.SSLSocketImpl.startHandshake(Unknown Source)
            at InstallCert.main(InstallCert.java:87)
    Caused by: sun.security.validator.ValidatorException: PKIX path building failed: sun.security.provider.certpath.SunCertP
    athBuilderException: unable to find valid certification path to requested target
            at sun.security.validator.PKIXValidator.doBuild(Unknown Source)
            at sun.security.validator.PKIXValidator.engineValidate(Unknown Source)
            at sun.security.validator.Validator.validate(Unknown Source)
            at com.sun.net.ssl.internal.ssl.X509TrustManagerImpl.validate(Unknown Source)
            at com.sun.net.ssl.internal.ssl.X509TrustManagerImpl.checkServerTrusted(Unknown Source)
            at InstallCert$SavingTrustManager.checkServerTrusted(InstallCert.java:182)
            ... 9 more
    Caused by: sun.security.provider.certpath.SunCertPathBuilderException: unable to find valid certification path to reques
    ted target
            at sun.security.provider.certpath.SunCertPathBuilder.engineBuild(Unknown Source)
            at java.security.cert.CertPathBuilder.build(Unknown Source)
            ... 15 more

    Server sent 1 certificate(s):

     1 Subject CN=yong mook kim, OU=mkyong, O=mkyong, L=puchong, ST=PJ, C=my
       Issuer  CN=yong mook kim, OU=mkyong, O=mkyong, L=puchong, ST=PJ, C=my
       sha1    32 3e 15 42 96 ba e9 4d 9c 5d e7 5e 6b 0f 30 23 b4 e3 f4 98
       md5     c8 dd a1 af 9f 55 a0 7f 6e 98 10 de 8c 63 1b a5

    Enter certificate to add to trusted keystore or 'q' to quit: [1]
    1

    [
    [
      Version: V3
      Subject: CN=yong mook kim, OU=mkyong, O=mkyong, L=puchong, ST=PJ, C=my
      Signature Algorithm: SHA1withRSA, OID = 1.2.840.113549.1.1.5

      Key:  Sun RSA public key, 1024 bits
      modulus: 1129473579651954554552730664834664064459539051598864058082387115962631728819634110255367718769683451438528187
    923246533854744470790959477657386037636238098777089479256059697784394926741427654735994678054030193662669088404706890444
    59364523220747231216704221781747262219695262340353839314222273672957748320603247
      public exponent: 65537
      Validity: [From: Tue Dec 14 15:13:51 SGT 2010,
                   To: Mon Mar 14 15:13:51 SGT 2011]
      Issuer: CN=yong mook kim, OU=mkyong, O=mkyong, L=puchong, ST=PJ, C=my
      SerialNumber: [    4d07192f]

    ]
      Algorithm: [SHA1withRSA]
      Signature:
    0000: 38 E4 F4 D9 51 B1 5F C1   01 13 32 79 DE 97 26 58  8...Q._...2y..&X
    0010: 13 08 F1 A0 33 DB B9 90   AF EE 9E AE B9 9B 68 7D  ....3.........h.
    0020: DF E8 7D 79 9D 92 24 4A   76 C9 4C 28 DA 68 B0 62  ...y..$Jv.L(.h.b
    0030: FF AB 27 03 5C DD 1F C8   77 A2 25 18 DF 0C DC FD  ..'.\...w.%.....
    0040: D3 39 5D 18 B4 BA 4B 36   8C FD C5 80 FF F2 E3 4D  .9]...K6.......M
    0050: 0A 28 57 B9 04 D8 25 F6   FB CA DA 13 0C 36 FB 02  .(W...%......6..
    0060: 9A B3 B1 28 46 D1 8E C7   D9 1A 5B CE BB A6 6F FD  ...(F.....[...o.
    0070: 6D F2 35 D9 95 43 6E 38   2A 56 E7 31 21 D9 F0 90  m.5..Cn8*V.1!...

    ]

    Added certificate to keystore 'jssecacerts' using alias 'localhost-1'

## 3\. Verify Trusted Keystore

Try run the `InstallCert` command again, the connection should be ok now.

    C:\>java InstallCert localhost:8443
    Loading KeyStore jssecacerts...
    Opening connection to localhost:8443...
    Starting SSL handshake...

    No errors, certificate is already trusted

    Server sent 1 certificate(s):

     1 Subject CN=yong mook kim, OU=mkyong, O=mkyong, L=puchong, ST=PJ, C=my
       Issuer  CN=yong mook kim, OU=mkyong, O=mkyong, L=puchong, ST=PJ, C=my
       sha1    32 3e 15 42 96 ba e9 4d 9c 5d e7 5e 6b 0f 30 23 b4 e3 f4 98
       md5     c8 dd a1 af 9f 55 a0 7f 6e 98 10 de 8c 63 1b a5

    Enter certificate to add to trusted keystore or 'q' to quit: [1]
    q
    KeyStore not changed

    C:\>

## 4.Copy jssecacerts

Copy the generated “**jssecacerts**” file to your “**$JAVA_HOME\jre\lib\security**” folder.

## 5\. Done

Run your web service client again, it should be working now.

[http://www.mkyong.com/webservices/jax-ws/suncertpathbuilderexception-unable-to-find-valid-certification-path-to-requested-target/](http://www.mkyong.com/webservices/jax-ws/suncertpathbuilderexception-unable-to-find-valid-certification-path-to-requested-target/)
