package com.reactive.service.functions;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * "Divide" function's implementation.
 */
public class DivideApplicationFunction implements ApplicationFunction<Double, Double> {

    private static final Logger LOGGER = LoggerFactory.getLogger(DivideApplicationFunction.class);

    @Override
    public Double compute(Double firstValue, Double secondValue) {
        if (secondValue == 0) {
            LOGGER.warn("Dividing {} by 0. User will receive an infinity.", firstValue);
        }
        return firstValue / secondValue;
    }
}
