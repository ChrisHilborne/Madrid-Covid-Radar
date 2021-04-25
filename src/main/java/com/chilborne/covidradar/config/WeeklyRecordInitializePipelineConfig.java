package com.chilborne.covidradar.config;

import com.chilborne.covidradar.data.pipeline.Pipeline;
import com.chilborne.covidradar.data.steps.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class WeeklyRecordInitializePipelineConfig {


    private final WeeklyRecordParser parser;
    private final WeeklyRecordFilter filter;
    private final WeeklyRecordFixer trimmer;
    private final WeeklyRecordMapper mapper;
    private final WeeklyRecordAggregator aggregator;
    private final WeeklyRecordSaver saver;


    public WeeklyRecordInitializePipelineConfig(
            WeeklyRecordParser parser,
            WeeklyRecordFilter filter,
            WeeklyRecordFixer trimmer,
            WeeklyRecordMapper mapper,
            WeeklyRecordAggregator aggregator,
            WeeklyRecordSaver saver) {
        this.parser = parser;
        this.filter = filter;
        this.trimmer = trimmer;
        this.mapper = mapper;
        this.aggregator = aggregator;
        this.saver = saver;
    }
    @Bean("weeklyRecord-initialize-pipeline")
    public Pipeline pipeline() {
        return new Pipeline<>(parser)
                .pipe(filter)
                .pipe(trimmer)
                .pipe(mapper)
                .pipe(aggregator)
                .pipe(saver);
    }

}
