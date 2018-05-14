package com.reactive.service.functions;

/**
 * General interface for functions that are executed within the service.
 * <p>
 * Note : so fat it supports only two params that are supplied for computation - this is based on the given requirements.
 * (Given interface might evolve over time of course.)
 *
 * @param <I> - type of an input parameters of function.
 * @param <O> - represents result of executed function.
 */
@FunctionalInterface
public interface ApplicationFunction<I, O> {

    /**
     * Executes the function's computation.
     *
     * @param firstValue  - represents function's first input parameter.
     * @param secondValue - represents function's second input parameter.
     * @return result of function's computation.
     */
    public O compute(I firstValue, I secondValue);
}
