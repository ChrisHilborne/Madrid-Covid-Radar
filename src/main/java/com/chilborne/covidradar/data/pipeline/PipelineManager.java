package com.chilborne.covidradar.data.pipeline;

public interface PipelineManager<S, V> {

    public V startPipeline(S input);
}
