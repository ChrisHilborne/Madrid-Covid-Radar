package com.chilborne.covidradar.data.steps;

import com.chilborne.covidradar.exceptions.PipeLineProcessException;

/**
 * Defines a single step in a pipeline process. Each step takes an defined input, performs one transformation and returns the result.
 */
public interface Step<I, O> {

    public O process(I input) throws PipeLineProcessException;

}
