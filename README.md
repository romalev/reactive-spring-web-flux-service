POC of reactive service based on web-flux
-

Introduction
- 
This is simple proof of concept of non-blocking rest service which is based on web-flux module (backed by netty) from spring. 

*Notes:* 

- there's no tomcat (jetty, wildfly, weblogic or any other servlet or application container) dependency on the classpath.*
- default number of threads for requests handling is determined by spring boot that is using Reactor Netty, which is using Netty's defaults.


Run the application
-
Service's requirements: 
- java 8+;
- gradle 4.x+

In order to run the service execute in shell : `./gradlew bootRun`. You should be able to see the service's log.

Hit `http://localhost:7777/rest-service/hello` endpoint to verify service has been started correctly - you should be able to see the "hello reply message".

Currently the service's default port on which it is going to accepts incoming requests is *7777*. You can change it in application.properties which is located under **resources** folder (look for `server.port` property).

Service's REST API
- 
Given services exposes the following endpoints : 
- 