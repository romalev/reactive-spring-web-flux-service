POC of reactive service based on spring reactor & web-flux
-

Introduction
- 
This is simple proof of concept of non-blocking rest service which is based on web-flux module (backed by netty) from spring. 

*Notes:* 

- there's no tomcat (jetty, wildfly, weblogic or any other servlet or application container) dependency on the classpath.*
- default number of threads for requests handling is determined by spring boot that is using Reactor Netty, which is using Netty's defaults.
- [gson](https://github.com/google/gson) is used to encode incoming json to service's dto objects and decode dto objects to outgoing json messages. Current implementation simply relies on plain `toJson` and `fromJson` methods without any extra configuration (which might be needed.)    


Run the application
-
Service's requirements: 
- java 8+;
- gradle 4.x+

In order to run the service execute in shell : `./gradlew bootRun`. You should be able to see the service's log messages.

Hit `http://localhost:7777/rest-service/hello` endpoint to verify whether service has started correctly - you should be able to see the "hello reply message".

Currently the service's default port on which it is going to accepts incoming requests is *7777*. You can change it in application.properties which is located under **resources** folder (look for `server.port` property).

Service's REST API
- 

**Given services exposes the following endpoints**
 
 Math operation     | Method        | Content Type      | URL                                                   | Request Body Example                                           | Response Body Example                                                        |
 | ----             | ------------- |-------------    | -----                                                   | -----                                                          |            ----                                                              |
 | *x+y*            | POST          | application/json  |http://localhost:7777/rest-service/add                 |{ <br/> "x": "[x_value]",  <br/>"y": "[y_value]" <br/>}         |{ <br/> "result": "[result]",  <br/>"message": "[additional details]" <br/>} 
 | *x-y*            | POST          | application/json  |http://localhost:7777/rest-service/sub                 |{ <br/> "x": "[x_value]",  <br/>"y": "[y_value]" <br/>}         |{ <br/> "result": "[result]",  <br/>"message": "[additional details]" <br/>}
 | *x/y*            | POST          | application/json  |http://localhost:7777/rest-service/divide              |{ <br/> "x": "[x_value]",  <br/>"y": "[y_value]" <br/>}         |{ <br/> "result": "[result]",  <br/>"message": "[additional details]" <br/>}
 | x*y              | POST          | application/json  |http://localhost:7777/rest-service/multiply            |{ <br/> "x": "[x_value]",  <br/>"y": "[y_value]" <br/>}         |{ <br/> "result": "[result]",  <br/>"message": "[additional details]" <br/>}
 | *x^2 - sqrt(y)*  | POST          | application/json  |http://localhost:7777/rest-service/inPowerOf2SubSqrt   |{ <br/> "x": "[x_value]",  <br/>"y": "[y_value]" <br/>}         |{ <br/> "result": "[result]",  <br/>"message": "[additional details]" <br/>}

 
 
 
 
