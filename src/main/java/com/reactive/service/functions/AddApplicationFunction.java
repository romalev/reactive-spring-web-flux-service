package com.reactive.service.functions;

/**
 * "Add" function's implementation.
 */
public class AddApplicationFunction implements ApplicationFunction<Double, Double> {

    @Override
    public Double compute(Double firstValue, Double secondValue) {
        return firstValue + secondValue;
    }
}
