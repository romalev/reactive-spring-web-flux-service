package reactive.spring.webflux;

import com.google.gson.Gson;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactive.spring.webflux.dto.ApplicationRequest;
import reactive.spring.webflux.dto.ApplicationResponse;
import reactive.spring.webflux.functions.ApplicationFunction;
import reactor.core.publisher.Mono;

import javax.annotation.PostConstruct;

/**
 * Dedicated handler to handle incoming requests and correspondent responses.
 */
@Component
public class ApplicationHandler {

    private Gson gson;

    @PostConstruct
    private void init() {
        gson = new Gson();
    }

    private static final Logger LOGGER = LoggerFactory.getLogger(ApplicationHandler.class);

    /**
     * Handles incoming request in an async, non-blocking way based on reactive streams specification.
     * (Reactor - implementation from pivotal folks of reactive streams - direct competitor of Netflix's RxJava.)
     * <p>
     * We take advantage of a rx-based stream (where so far only one item flows through it which is a user's request)
     * with deferred emitter which is being executed lazily once reactor subscribes to the stream.
     * <p>
     * (We don't really need to worry about try - catch scenarios since we delegate handling an user's requests to rx-based executable pipeline.)
     *
     * @param request - holds incoming request's details.
     * @return response item from the mono - based stream.
     */
    public Mono<ServerResponse> handle(ServerRequest request, ApplicationFunction<Double, Double> applicationFunction) {
        return Mono.create(monoSink -> request
                .bodyToMono(String.class)
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
}
