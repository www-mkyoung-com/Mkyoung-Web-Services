The `wsimport` tool is used to parse an existing Web Services Description Language (WSDL) file and generate required files (JAX-WS portable artifacts) for web service client to access the published web services. This `wsimport` tool is available in the `$JDK/bin` folder.

## Use Case

An common use case of this `wsimport` tool.

## 1\. Server – Published web service – WSDL file.

The CompA has published a web service along with a WSDL file at URL : _http://compA.com/ws/server?wsdl_

## 2\. Client – Access the published service.

For CompB, to develop a web service client to access the CompA published web service, they can use `wsimport` tool to parse CompA’s WSDL file and generate files (JAX-WS portable artifacts) to access CompA’s published service.

_Command : wsimport command to parse CompA WSDL file_

    C:\>wsimport -keep -verbose http://compA.com/ws/server?wsdl
    parsing WSDL...

    generating code...
    com\mkyong\ws\ServerInfo.java
    com\mkyong\ws\ServerInfoImplService.java

**Note**  
For complete example, please visit this [JAX-WS hello world example](http://www.mkyong.com/webservices/jax-ws/jax-ws-hello-world-example/) article, refer to the section “_2\. Java Web Service Client via wsimport tool_“.

For other use cases or usages, please visit reference links below.

## References

1.  [Metro : wsimport tool](http://jax-ws.java.net/jax-ws-ea3/docs/wsimport.html)
2.  [IBM – wsimport command for JAX-WS applications.](http://publib.boulder.ibm.com/infocenter/wasinfo/v6r1/index.jsp?topic=/com.ibm.websphere.wsfep.multiplatform.doc/info/ae/ae/rwbs_wsimport.html)

[http://www.mkyong.com/webservices/jax-ws/jax-ws-wsimport-tool-example/](http://www.mkyong.com/webservices/jax-ws/jax-ws-wsimport-tool-example/)
