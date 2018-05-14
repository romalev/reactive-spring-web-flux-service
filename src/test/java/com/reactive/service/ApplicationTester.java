package com.reactive.service;

import com.google.gson.Gson;
import com.reactive.service.functions.*;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Mono;

/**
 * Main application tester containing service's integration tests.
 */
@RunWith(SpringRunner.class)
@ComponentScan(basePackages = "com.reactive.service")
public class ApplicationTester {

    private WebTestClient testClient;

    private String correctApplicationRequestJson;

    private ApplicationHandler handler;
    private ApplicationRouter router;

    @Before
    public void setUp() {
        // setting up all required dependencies manually.
        // Note : any mock might be injected here - we inject real implementation.
        handler = new ApplicationHandler(new Gson());
        router = new ApplicationRouter(
                new AddApplicationFunction(),
                new SubtractApplicationFunction(),
                new DivideApplicationFunction(),
                new MultiplyApplicationFunction(),
                new PowerOf2SubSqrtApplicationFunction());

        testClient = WebTestClient.bindToRouterFunction(router.route(handler)).build();
        correctApplicationRequestJson = "{'x': 12,'y': 21}";
    }

    @Test
    public void verifyAddOperation() {
        // given
        testClient
                .post()
                .uri(ApplicationRouter.CONTEXT_PATH + "/add")
                .contentType(MediaType.APPLICATION_JSON)
                .body(Mono.just(correctApplicationRequestJson), String.class)
                .exchange()
                .expectStatus().isOk();
    }

}
