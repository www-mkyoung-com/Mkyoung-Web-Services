## Problem

Developed a SOAP web service via **Metro** 2.0.1 (`webservices-rt.jar`), integrate with **Spring** via `jaxws-spring-1.8.jar`and deployed on **WebSphere Application Server** (WAS) version 7.0.0.13 . See web service below :

_File : UserWS.java_

    package com.mkyong.user.ws;
    //import...
    @WebService()
    public class UserWS {

        private UserBo userBo;

        @WebMethod(exclude = true)
        public UserBo getUserBo() {
            return userBo;
        }

        @WebMethod(exclude = true)
        public void setUserBo(UserBo userBo) {
            this.userBo = userBo;
        }

        @WebMethod(operationName = "listUser")
        public List<User> listUser() throws SOAPException {

           return userBo.listUser();

        }
    }

If everything is fine, the service is work as expected, but, when “`userBo.listUser()`” hit error and throwing a `SOAPException`back to the web service’s client :

Client get this :

    Exception in thread "main"
    	javax.xml.ws.WebServiceException: No Content-type in the header!

WAS log file showing this :

    Caused by: java.lang.ClassCastException: com.ibm.xml.xlxp2.jaxb.JAXBContextImpl
    	incompatible with com.sun.xml.bind.api.JAXBRIContext
    	at com.sun.xml.ws.fault.SOAPFaultBuilder.<clinit>(SOAPFaultBuilder.java:561)
    	at java.lang.J9VMInternals.initializeImpl(Native Method)
    	at java.lang.J9VMInternals.initialize(J9VMInternals.java:200)
    	... 34 more

_P.S This web service is working on Tomcat, but failed on WebSphere only_

## Solution

The `com.ibm.xml.xlxp2.jaxb.JAXBContextImpl` is located at **WAS_APPSERVER_PROFILE\plugins\com.ibm.ws.prereq.xlxp.jar**, and Metro library, **webservices-rt.jar**, also contain one of the instance `com.sun.xml.bind.v2.runtime.JAXBContextImpl` . When exception raised, WAS is using two different instance to perform a same JAXB binding task, and raise an “java.lang.ClassCastException” incompatible exception.

To fix it, just make sure WAS is using same instance always.

1.  In WAS configuration screen, set module class loader to “**parent-first**“.
2.  In WAS folder, create a “**classes**” folder under WAS root folder and put `webservices-rt.jar` inside, for example, `WAS_APPSERVER_PROFILE\classes\webservices-rt.jar`.

Restart WAS instance, now WAS will load your `webservices-rt.jar` always. A dirty way, but it work :).

[http://www.mkyong.com/webservices/jax-ws/metro-on-websphere-7-com-ibm-xml-xlxp2-jaxb-jaxbcontextimpl-incompatible-exception/](http://www.mkyong.com/webservices/jax-ws/metro-on-websphere-7-com-ibm-xml-xlxp2-jaxb-jaxbcontextimpl-incompatible-exception/)
