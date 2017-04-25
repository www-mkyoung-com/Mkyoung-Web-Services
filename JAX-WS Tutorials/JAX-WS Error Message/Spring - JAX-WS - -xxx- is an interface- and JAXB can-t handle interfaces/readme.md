## Problem

Integrate Spring + JAX-WS, see web service below :

    package com.mkyong.user.ws;
    //imports...

    @WebService()
    public class PGUserWS {

        //DI via Spring
        private UserBo userBo;

        public UserBo getUserBo() {
            return userBo;
        }

        public void setUserBo(UserBo userBo) {
            this.userBo = userBo;
        }

        @WebMethod(operationName = "addUser")
        public boolean addUser(@WebParam(name = "userId")
        String userId, @WebParam(name = "User")
        User user) throws SOAPException {

            userBo.addUser(userId, user);
            return true;
        }

    }

The “**userBo**” is DI via Spring XML bean configuration. But, when the service is generating the service’s files (via **wsgen**) for deployment, it hits “**JAXB can’t handle interfaces**” error message, see below :

    Caused by: java.security.PrivilegedActionException:
    	com.sun.xml.bind.v2.runtime.IllegalAnnotationsException:
                2 counts of IllegalAnnotationExceptions
    	com.mkyong.user.bo.UserBo is an interface, and JAXB can't handle interfaces.
            this problem is related to the following location:
                    at com.mkyong.user.bo.UserBo
                    at private com.mkyong.user.bo.UserBo com.mkyong.user.ws.jaxws.SetUserBo.arg0
                    at com.mkyonguser.ws.jaxws.SetUserBo
    	com.mkyong.user.bo.UserBo does not have a no-arg default constructor.
            this problem is related to the following location:
                    at com.mkyong.user.bo.UserBo
                    at private com.mkyong.user.bo.UserBo com.mkyong.user.ws.jaxws.SetUserBo.arg0
                    at com.mkyong.user.ws.jaxws.SetUserBo

## Solution

See this [unofficial JAXB guide](http://jaxb.java.net/guide/Mapping_interfaces.html) to learn how to map interface in JAXB.

However, your case is different, it just doesn’t make sense to generate web service method for “**userBo**“, which is only require to Di via Spring, not publish to client.

To stop “**wsgen**” to generate web method for “**userBo**“, just annotate it with “`@WebMethod(exclude = true)`“, see below :

    package com.mkyong.user.ws;

    //imports...

    @WebService()
    public class PGUserWS {

        private UserBo userBo;

        @WebMethod(exclude = true)
        public UserBo getUserBo() {
            return userBo;
        }

        @WebMethod(exclude = true)
        public void setUserBo(UserBo userBo) {
            this.userBo = userBo;
        }

        @WebMethod(operationName = "addUser")
        public boolean addUser(@WebParam(name = "userId")
        String userId, @WebParam(name = "User")
        User user) throws SOAPException {

            userBo.addUser(userId, user);
            return true;
        }

    }

Now, “**wsgen**” will ignore the “**userBo**” getter and setter methods.

[http://www.mkyong.com/webservices/jax-ws/spring-jax-ws-xxx-is-an-interface-and-jaxb-cant-handle-interfaces/](http://www.mkyong.com/webservices/jax-ws/spring-jax-ws-xxx-is-an-interface-and-jaxb-cant-handle-interfaces/)
