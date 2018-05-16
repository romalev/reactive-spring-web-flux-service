package com.reactive.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

/**
 * Responsible from booting up the whole app (rest-service which is a spring boot based).
 */
@SpringBootApplication
@Configuration
@ComponentScan(basePackages = "com.reactive.service")
public class ApplicationLauncher {

    @Value("${server.port}")
    private int serverPort;
    @Value("${service.name}")
    private String serviceName;

    private static final Logger LOGGER = LoggerFactory.getLogger(ApplicationLauncher.class);

    public static void main(String[] args) {
        LOGGER.trace("Booting up the reactive spring web-flux REST service...");
        SpringApplication.run(ApplicationLauncher.class);
    }

    @PostConstruct
    public void init() {
        LOGGER.trace("Service: {} is running on port : {}", serviceName, serverPort);
        // other init-like logic needs to be placed here.
    }

    @PreDestroy
    public void cleanUp() {
        LOGGER.trace("Service: {} is about to get shut down.", serviceName);
        // some clean up work before service shutdown needs to be placed here.
    }
}
