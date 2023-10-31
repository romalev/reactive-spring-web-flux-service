package com.reactive.service;

import java.util.Objects;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import com.reactive.service.functions.*;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.reactive.server.EntityExchangeResult;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Mono;

/**
 * Main application tester containing service's integration tests.
 * <p>
 * So far it doesn't cover all the "failure" cases that might happen within the service but covers the important ones.
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
        correctApplicationRequestJson = "{'x': 40,'y': 20}";
    }

    @Test
    public void verifyAddOperationOnSuccess() {
        // given
        String expectedResponse = "{\"result\":\"60.0\",\"message\":\"Result has been successfully calculated.\"}";
        // then
        verifyOnSuccess(expectedResponse, correctApplicationRequestJson, "/add");
    }

    @Test
    public void verifySubOperationOnSuccess() {
        // given
        String expectedResponse = "{\"result\":\"20.0\",\"message\":\"Result has been successfully calculated.\"}";
        // then
        verifyOnSuccess(expectedResponse, correctApplicationRequestJson, "/sub");
    }

    @Test
    public void verifyDivideOperationOnSuccess() {
        // given
        String expectedResponse = "{\"result\":\"2.0\",\"message\":\"Result has been successfully calculated.\"}";
        // then
        verifyOnSuccess(expectedResponse, correctApplicationRequestJson, "/divide");
    }

    @Test
    public void verifyMultiplyOperationOnSuccess() {
        // given
        String expectedResponse = "{\"result\":\"800.0\",\"message\":\"Result has been successfully calculated.\"}";
        // given
        verifyOnSuccess(expectedResponse, correctApplicationRequestJson, "/multiply");
    }

    @Test
    public void verifyPowerOf2SubSqrtOperationOnSuccess() {
        // given
        String expectedResponse = "{\"result\":\"1595.5278640450003\",\"message\":\"Result has been successfully calculated.\"}";
        // given
        verifyOnSuccess(expectedResponse, correctApplicationRequestJson, "/inPowerOf2SubSqrt");
    }

    /**
     * Verifies the rest operation on success.
     *
     * @param expectedResponse - represents the response we must receive from rest service.
     * @param requestToBeSent  - message that is sent to rest endpoint.
     * @param operation        - operation type.
     */
    private void verifyOnSuccess(String expectedResponse, String requestToBeSent, String operation) {
        String result = testClient
                .post()
                .uri(ApplicationRouter.CONTEXT_PATH + operation)
                .contentType(MediaType.APPLICATION_JSON)
                .body(Mono.just(requestToBeSent), String.class)
                .exchange()
                .expectStatus().isOk()
                .expectBody(String.class)
                .returnResult().getResponseBody();

        JsonParser parser = new JsonParser();
        JsonElement expected = parser.parse(expectedResponse);
        JsonElement actual = parser.parse(Objects.requireNonNull(result));

        Assert.assertEquals(expected, actual);
    }

    @Test
    public void verifyBadlyFormedJsonRequestBodyOnAdd() {
        // given
        String messageToBeSend = "This is not json at all";
        // then
        verifyOnFailure("", messageToBeSend, "/add");
    }

    @Test
    public void verifyBadlyFormedJsonRequestBodyOnSub() {
        // given
        String messageToBeSend = "{'x': 40asd,'y': 20}";

        // then
        verifyOnFailure("", messageToBeSend, "/sub");
    }

    @Test
    public void verifyBadlyFormedJsonRequestBodyOnDivide() {
        // given
        String messageToBeSend = "";

        // then
        verifyOnFailure("", messageToBeSend, "/divide");
    }

    /**
     * Verifies the rest operation on failure.
     *
     * @param expectedResponse - represents the response we must receive from rest service.
     * @param requestToBeSent  - message that is sent to rest endpoint.
     * @param operation        - operation type.
     */
    private void verifyOnFailure(String expectedResponse, String requestToBeSent, String operation) {
        testClient
                .post()
                .uri(ApplicationRouter.CONTEXT_PATH + operation)
                .contentType(MediaType.APPLICATION_JSON)
                .body(Mono.just(requestToBeSent), String.class)
                .exchange()
                .expectStatus().isBadRequest();
        // we are not verifying the exact content of the message, this might get improved over time.
    }
}
