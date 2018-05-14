package com.reactive.service;

import com.google.gson.Gson;
import com.reactive.service.dto.ApplicationRequest;
import com.reactive.service.dto.ApplicationResponse;
import com.reactive.service.functions.ApplicationFunction;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

/**
 * Dedicated handler to handle incoming requests and correspondent responses.
 */
@Component
public class ApplicationHandler {

    private final Gson gson;

    public ApplicationHandler(Gson gson) {
        this.gson = gson;
    }

    private static final Logger LOGGER = LoggerFactory.getLogger(ApplicationHandler.class);

    /**
     * Handles incoming request in an async, non-blocking way based on reactive streams specification.
     * (Reactor - implementation from pivotal folks of reactive streams - direct competitor of Netflix's RxJava.)
     * <p>
     * We take advantage of a rx-based stream (where so far only one item flows through it which is a user's request)
     * with deferred emitter which is being executed lazily once reactor subscriber (the one that calls this method) subscribes to the stream.
     * <p>
     * (We don't really need to worry about try - catch scenarios since we delegate handling an user's requests to reactor-based executable pipeline.)
     *
     * @param request - holds incoming request's details.
     * @return http response to the user.
     */
    public Mono<ServerResponse> handle(ServerRequest request, ApplicationFunction<Double, Double> applicationFunction) {
        return Mono.create(monoSink -> request
                .bodyToMono(String.class)
                .switchIfEmpty(Mono.just(""))
                .doOnNext(jsonBodyAsString -> LOGGER.debug("{} receives : {} for computation.", applicationFunction.getClass().getSimpleName(), jsonBodyAsString))
                // trying to encode incoming json to application dto object
                .map(jsonBodyAsString -> gson.fromJson(jsonBodyAsString, ApplicationRequest.class))
                // trying to perform requested operation
                .map(applicationRequest -> new ApplicationResponse(String.valueOf(
                        applicationFunction.compute(applicationRequest.getX(), applicationRequest.getY())),
                        "Result has been successfully calculated."))
                .subscribe(
                        // handling happy case scenario :)
                        applicationResponse -> {
                            LOGGER.debug("{} has been computed successfully.", applicationFunction.getClass().getSimpleName());
                            ServerResponse
                                    .ok()
                                    .contentType(MediaType.APPLICATION_JSON)
                                    .body(BodyInserters.fromObject(applicationResponse))
                                    .subscribe(monoSink::success);

                        },
                        // handling any sort of failure :(
                        throwable -> {
                            // note : the only bad thing that might shows up right now is the problem of encoding incoming json.
                            // in that case bad request (400) is responded back to client.
                            final String failedMessage = "Operation failed to execute. Most likely the request body's json is not properly formed. Details : " + throwable.getClass() + ": " + throwable.getMessage();
                            LOGGER.error("{} failed. {}", applicationFunction.getClass().getSimpleName(), failedMessage);
                            ServerResponse
                                    .badRequest()
                                    .contentType(MediaType.APPLICATION_JSON)
                                    .body(BodyInserters.fromObject(new ApplicationResponse("", failedMessage)))
                                    .subscribe(monoSink::success);
                        }));
    }

    /**
     * Handles the scenario where user request non-existent operation.
     *
     * @param request - represents user's request details.
     * @return response to the user.
     */
    public Mono<ServerResponse> handleOnMethodNotFound(ServerRequest request) {
        LOGGER.warn("User has requested an operation that doesn't exist in the service.");
        return ServerResponse
                .status(HttpStatus.NOT_FOUND)
                .body(BodyInserters.fromObject(new ApplicationResponse("", "The requested operation is not found.")));
    }

    /**
     * Dummy implementation of simple health check user might perform on the service itself to verify service started properly.
     *
     * @param request - represents user's request details.
     * @return hello message to user.
     */
    public Mono<ServerResponse> tellHelloToUser(ServerRequest request) {
        return ServerResponse
                .ok()
                .contentType(MediaType.TEXT_PLAIN)
                .body(BodyInserters.fromObject("Hello there!"));
    }


}
