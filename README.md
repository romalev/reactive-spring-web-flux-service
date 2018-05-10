#POC of reactive service based on web-flux 

Introduction
- 
This is simple proof of concept of non-blocking rest service which is based on web-flux module (backed by netty) from spring. 

*Important notes:* 

- there's no tomcat (jetty, wildfly, weblogic or any other servlet or application container) dependency on the classpath.*
- default number of threads for requests handling is determined by spring boot that is using Reactor Netty, which is using Netty's defaults.


Run the application
-
TODO