package com.reactive.service.functions;

/**
 * Computes x^2 - sqrt(y).
 */
public class PowerOf2SubSqrtApplicationFunction implements ApplicationFunction<Double, Double> {

    @Override
    public Double compute(Double firstValue, Double secondValue) {
        return Math.pow(firstValue, 2) - Math.sqrt(secondValue);
    }
}
