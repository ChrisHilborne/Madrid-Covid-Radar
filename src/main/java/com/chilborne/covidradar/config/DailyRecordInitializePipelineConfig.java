package com.chilborne.covidradar.config;

import com.chilborne.covidradar.data.pipeline.Pipeline;
import com.chilborne.covidradar.data.steps.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DailyRecordInitializePipelineConfig {


    private final DailyRecordParser parser;
    private final DailyRecordFilter filter;
    private final DailyRecordFixer trimmer;
    private final DailyRecordMapper mapper;
    private final DailyRecordAggregator aggregator;
    private final DailyRecordSaver saver;


    public DailyRecordInitializePipelineConfig(
            DailyRecordParser parser,
            DailyRecordFilter filter,
            DailyRecordFixer trimmer,
            DailyRecordMapper mapper,
            DailyRecordAggregator aggregator,
            DailyRecordSaver saver) {
        this.parser = parser;
        this.filter = filter;
        this.trimmer = trimmer;
        this.mapper = mapper;
        this.aggregator = aggregator;
        this.saver = saver;
    }
    @Bean("dailyRecord-initialize-pipeline")
    public Pipeline pipeline() {
        return new Pipeline<>(parser)
                .pipe(filter)
                .pipe(trimmer)
                .pipe(mapper)
                .pipe(aggregator)
                .pipe(saver);
    }

}
