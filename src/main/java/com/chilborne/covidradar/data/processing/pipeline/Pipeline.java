package com.chilborne.covidradar.data.processing.pipeline;

import com.chilborne.covidradar.data.processing.steps.Step;
import com.chilborne.covidradar.exceptions.PipeLineProcessException;


public class Pipeline<I, O> {
    private final Step<I, O> current;
    public Pipeline(Step<I, O> current) {
        this.current = current;
    }

    public <NewO> Pipeline<I, NewO> pipe(Step<O, NewO> next) {
        return new Pipeline<>(input -> next.process(current.process(input)));
    }

    public O execute(I input) throws PipeLineProcessException {
        return current.process(input);
    }
}
