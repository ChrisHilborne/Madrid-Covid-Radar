package com.chilborne.covidradar.config;

import com.chilborne.covidradar.data.pipeline.Pipeline;
import com.chilborne.covidradar.data.steps.DailyRecordFixer;
import com.chilborne.covidradar.data.steps.DailyRecordParser;
import com.chilborne.covidradar.data.steps.DailyRecordSaver;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DailyRecordPipelineConfig {


    private final DailyRecordParser parser;
    private final DailyRecordFixer trimmer;
    private final DailyRecordSaver saver;


    public DailyRecordPipelineConfig(
                                        DailyRecordParser parser,
                                        DailyRecordFixer trimmer,
                                        DailyRecordSaver saver) {
        this.parser = parser;
        this.trimmer = trimmer;
        this.saver = saver;
    }


    @Bean("dailyRecord-Pipeline")
    public Pipeline pipeline() {
        return new Pipeline<>(parser)
                .pipe(trimmer)
                .pipe(saver);
    }

}
