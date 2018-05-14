package com.reactive.service;

import com.google.gson.Gson;
import com.reactive.service.functions.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Contains application-specific configuration.
 * We strictly rely on constructor-based injection - this is done to make all the bean's dependencies immutable.
 */
@Configuration
public class ApplicationConfig {

    @Bean
    public ApplicationFunction<Double, Double> addApplicationFunction() {
        return new AddApplicationFunction();
    }

    @Bean
    public ApplicationFunction<Double, Double> subApplicationFunction() {
        return new SubtractApplicationFunction();
    }

    @Bean
    public ApplicationFunction<Double, Double> divideApplicationFunction() {
        return new DivideApplicationFunction();
    }

    @Bean
    public ApplicationFunction<Double, Double> multiplyApplicationFunction() {
        return new MultiplyApplicationFunction();
    }

    @Bean
    public ApplicationFunction<Double, Double> powerOf2SubSqrtApplicationFunction() {
        return new PowerOf2SubSqrtApplicationFunction();
    }

    @Bean
    public Gson getGson() {
        return new Gson();
    }

    @Bean
    public ApplicationHandler applicationHandler() {
        return new ApplicationHandler(getGson());
    }
}
