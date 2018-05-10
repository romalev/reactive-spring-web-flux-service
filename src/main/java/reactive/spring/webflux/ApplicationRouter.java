package reactive.spring.webflux;

import org.springframework.context.annotation.Bean;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.server.RequestPredicates;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerResponse;

/**
 * Dedication application router - responsible for routing requests to appropriate handlers.
 */
@Component
public class ApplicationRouter {
    private static final String CONTEXT_PATH = "/rest-service";

    @Bean
    public RouterFunction<ServerResponse> route(ApplicationHandler applicationHandler) {
        return RouterFunctions
                .route(RequestPredicates.GET(CONTEXT_PATH + "/hello").and(RequestPredicates.accept(MediaType.TEXT_PLAIN)),
                        request ->
                                ServerResponse.ok().contentType(MediaType.TEXT_PLAIN)
                                        .body(BodyInserters.fromObject("Hello there!")))
                .andRoute(RequestPredicates.POST(CONTEXT_PATH + "/add").and(RequestPredicates.accept(MediaType.APPLICATION_JSON)), applicationHandler::handle);
    }
}
