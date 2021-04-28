package com.chilborne.covidradar.data.pipeline;

public interface PipelineManager<S> {

    public void startPipeline(S input);
}
