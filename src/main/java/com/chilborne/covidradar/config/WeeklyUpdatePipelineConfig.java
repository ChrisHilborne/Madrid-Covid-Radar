package com.chilborne.covidradar.config;

import com.chilborne.covidradar.data.pipeline.Pipeline;
import com.chilborne.covidradar.data.steps.WeeklyRecordFixer;
import com.chilborne.covidradar.data.steps.WeeklyRecordParser;
import com.chilborne.covidradar.data.steps.WeeklyRecordSaver;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class WeeklyUpdatePipelineConfig {


    private final WeeklyRecordParser parser;
    private final WeeklyRecordFixer trimmer;
    private final WeeklyRecordSaver saver;


    public WeeklyUpdatePipelineConfig(
            WeeklyRecordParser parser,
            WeeklyRecordFixer trimmer,
            WeeklyRecordSaver saver) {
        this.parser = parser;
        this.trimmer = trimmer;
        this.saver = saver;
    }

    @Bean("weeklyRecord-update-pipeline")
    public Pipeline pipeline() {
        return new Pipeline<>(parser)
                .pipe(trimmer)
                .pipe(saver);
    }

}
