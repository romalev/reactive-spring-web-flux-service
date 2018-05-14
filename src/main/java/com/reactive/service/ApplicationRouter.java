package com.reactive.service;

import com.reactive.service.functions.ApplicationFunction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.RequestPredicates;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;

/**
 * Dedication application router - responsible for routing requests to appropriate handlers.
 */
@Component
public class ApplicationRouter {

    public static final String CONTEXT_PATH = "/rest-service";

    private ApplicationFunction<Double, Double> addApplicationFunction;
    private ApplicationFunction<Double, Double> subApplicationFunction;
    private ApplicationFunction<Double, Double> divideApplicationFunction;
    private ApplicationFunction<Double, Double> multiplyApplicationFunction;
    private ApplicationFunction<Double, Double> powerOf2SubSqrtApplicationFunction;

    @Autowired
    public ApplicationRouter(ApplicationFunction<Double, Double> addApplicationFunction, ApplicationFunction<Double, Double> subApplicationFunction, ApplicationFunction<Double, Double> divideApplicationFunction, ApplicationFunction<Double, Double> multiplyApplicationFunction, ApplicationFunction<Double, Double> powerOf2SubSqrtApplicationFunction) {
        this.addApplicationFunction = addApplicationFunction;
        this.subApplicationFunction = subApplicationFunction;
        this.divideApplicationFunction = divideApplicationFunction;
        this.multiplyApplicationFunction = multiplyApplicationFunction;
        this.powerOf2SubSqrtApplicationFunction = powerOf2SubSqrtApplicationFunction;
    }

    /**
     * Defines service routing - forwards specific user's request to appropriate handlers.
     */
    @Bean
    public RouterFunction<?> route(ApplicationHandler applicationHandler) {
        return RouterFunctions
                .route(
                        RequestPredicates.GET(CONTEXT_PATH + "/hello").and(RequestPredicates.accept(MediaType.TEXT_PLAIN)), applicationHandler::tellHelloToUser)
                .andRoute(RequestPredicates.POST(CONTEXT_PATH + "/add").and(RequestPredicates.accept(MediaType.APPLICATION_JSON)),
                        request -> applicationHandler.handle(request, addApplicationFunction))
                .andRoute(RequestPredicates.POST(CONTEXT_PATH + "/sub").and(RequestPredicates.accept(MediaType.APPLICATION_JSON)),
                        request -> applicationHandler.handle(request, subApplicationFunction))
                .andRoute(RequestPredicates.POST(CONTEXT_PATH + "/divide").and(RequestPredicates.accept(MediaType.APPLICATION_JSON)),
                        request -> applicationHandler.handle(request, divideApplicationFunction))
                .andRoute(RequestPredicates.POST(CONTEXT_PATH + "/multiply").and(RequestPredicates.accept(MediaType.APPLICATION_JSON)),
                        request -> applicationHandler.handle(request, multiplyApplicationFunction))
                .andRoute(RequestPredicates.POST(CONTEXT_PATH + "/inPowerOf2SubSqrt").and(RequestPredicates.accept(MediaType.APPLICATION_JSON)),
                        request -> applicationHandler.handle(request, powerOf2SubSqrtApplicationFunction))
                .andOther(RouterFunctions.route(RequestPredicates.accept(MediaType.APPLICATION_JSON), applicationHandler::handleOnMethodNotFound));
    }
}
