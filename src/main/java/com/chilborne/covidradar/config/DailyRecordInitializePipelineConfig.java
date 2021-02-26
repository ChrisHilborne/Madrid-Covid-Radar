package com.chilborne.covidradar.config;

import com.chilborne.covidradar.data.pipeline.Pipeline;
import com.chilborne.covidradar.data.steps.DailyRecordAggregator;
import com.chilborne.covidradar.data.steps.DailyRecordFixer;
import com.chilborne.covidradar.data.steps.DailyRecordParser;
import com.chilborne.covidradar.data.steps.DailyRecordSaver;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DailyRecordInitializePipelineConfig {


    private final DailyRecordParser parser;
    private final DailyRecordFixer trimmer;
    private final DailyRecordAggregator aggregator;
    private final DailyRecordSaver saver;


    public DailyRecordInitializePipelineConfig(
            DailyRecordParser parser,
            DailyRecordFixer trimmer,
            DailyRecordAggregator aggregator,
            DailyRecordSaver saver) {
        this.parser = parser;
        this.trimmer = trimmer;
        this.aggregator = aggregator;
        this.saver = saver;
    }
    @Bean("dailyRecord-initialize-pipeline")
    public Pipeline pipeline() {
        return new Pipeline<>(parser)
                .pipe(trimmer)
                .pipe(aggregator)
                .pipe(saver);
    }

}
