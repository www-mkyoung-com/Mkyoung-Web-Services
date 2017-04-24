## Question

Working REST + file upload, using **resteasy 2.2.1.GA** and **Eclipse 3.6**. And the **reseteasy multipart** dependency is declared in maven `pom.xml` file.

In compile mode, class “`MultipartInput`” is able to compile, but Eclipse prompts following error message during deployment or debugging mode?

    java.lang.NoClassDefFoundError: org/jboss/resteasy/plugins/providers/multipart/MultipartInput
    	at java.lang.Class.getDeclaredMethods0(Native Method)
    	at java.lang.Class.privateGetDeclaredMethods(Class.java:2427)
    	at org.apache.catalina.startup.Bootstrap.main(Bootstrap.java:414)

    Caused by: java.lang.ClassNotFoundException:
            org.jboss.resteasy.plugins.providers.multipart.MultipartInput

    	at org.apache.catalina.loader.WebappClassLoader.loadClass(WebappClassLoader.java:1516)
    	at org.apache.catalina.loader.WebappClassLoader.loadClass(WebappClassLoader.java:1361)
    	at java.lang.ClassLoader.loadClassInternal(ClassLoader.java:320)
    	... 29 more

## Solution

In Maven + Eclipse development, double confirm that the “**resteasy-multipart-provider**” is declared in Maven `pom.xml` file properly.

_File : pom.xml_

    <dependency>
    	<groupId>org.jboss.resteasy</groupId>
    	<artifactId>resteasy-multipart-provider</artifactId>
    	<version>2.2.1.GA</version>
    </dependency>

Issue below Maven command to make sure all dependencies are deployed during Eclipse debugging environment.

    mvn eclipse:eclipse -Dwtpversion=2.0

[http://www.mkyong.com/webservices/jax-rs/classnotfoundexception-org-jboss-resteasy-plugins-providers-multipart-multipartinput/](http://www.mkyong.com/webservices/jax-rs/classnotfoundexception-org-jboss-resteasy-plugins-providers-multipart-multipartinput/)
