package com.reactive.service.functions;

/**
 * "Subtract" function's implementation.
 */
public class SubtractApplicationFunction implements ApplicationFunction<Double, Double> {

    @Override
    public Double compute(Double firstValue, Double secondValue) {
        return firstValue - secondValue;
    }
}
