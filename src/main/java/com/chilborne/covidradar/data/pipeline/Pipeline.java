package com.chilborne.covidradar.data.pipeline;

import com.chilborne.covidradar.data.steps.Step;
import com.chilborne.covidradar.exceptions.PipeLineProcessException;

/**
 * Pipeline is a modular class which takes input data and passes it through a number of transformative steps.
 * This design pattern was chosen because the data we use is fetched from an external service and is not uniform.
 * Likewise we serve the data through our API in a different form and need to perform multiple checks and transformations
 * The pipeline pattern allows us to decouple each step in the process and easily add new or remove steps as is needed.
 */
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
