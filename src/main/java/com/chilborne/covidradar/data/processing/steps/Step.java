package com.chilborne.covidradar.data.processing.steps;

import com.chilborne.covidradar.exceptions.PipeLineProcessException;

public interface Step<I, O> {

    public O process(I input) throws PipeLineProcessException;

}
