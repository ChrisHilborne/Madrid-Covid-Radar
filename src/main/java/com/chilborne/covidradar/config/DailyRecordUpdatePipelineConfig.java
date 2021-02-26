package com.chilborne.covidradar.config;

import com.chilborne.covidradar.data.pipeline.Pipeline;
import com.chilborne.covidradar.data.steps.DailyRecordFixer;
import com.chilborne.covidradar.data.steps.DailyRecordParser;
import com.chilborne.covidradar.data.steps.DailyRecordSaver;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DailyRecordUpdatePipelineConfig {


    private final DailyRecordParser parser;
    private final DailyRecordFixer trimmer;
    private final DailyRecordSaver saver;


    public DailyRecordUpdatePipelineConfig(
            DailyRecordParser parser,
            DailyRecordFixer trimmer,
            DailyRecordSaver saver) {
        this.parser = parser;
        this.trimmer = trimmer;
        this.saver = saver;
    }

    @Bean("dailyRecord-update-pipeline")
    public Pipeline pipeline() {
        return new Pipeline<>(parser)
                .pipe(trimmer)
                .pipe(saver);
    }

}
