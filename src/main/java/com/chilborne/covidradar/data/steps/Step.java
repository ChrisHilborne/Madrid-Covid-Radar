package com.chilborne.covidradar.data.steps;

import com.chilborne.covidradar.exceptions.PipeLineProcessException;

public interface Step<I, O> {

    public O process(I input) throws PipeLineProcessException;

}
