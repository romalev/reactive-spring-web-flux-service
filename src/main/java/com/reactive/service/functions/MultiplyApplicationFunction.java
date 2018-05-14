package com.reactive.service.functions;

/**
 * "Multiply" function's implementation.
 */
public class MultiplyApplicationFunction implements ApplicationFunction<Double, Double> {

    @Override
    public Double compute(Double firstValue, Double secondValue) {
        return firstValue * secondValue;
    }
}
