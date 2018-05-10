package reactive.spring.webflux;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

/**
 * REST based application controller - handles incoming client's requests.
 */
@RestController
@RequestMapping(produces = MediaType.APPLICATION_JSON_VALUE)
public class ApplicationController {

    private static final String CONTEXT_PATH = "/rest-service";

    @GetMapping(path = CONTEXT_PATH + "/hello")
    public Mono<String> sayHelloToUser() {
        return Mono.just("Hello there!");
    }

}
