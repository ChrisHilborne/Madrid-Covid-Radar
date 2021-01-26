package com.chilborne.covidradar.config;

import com.chilborne.covidradar.data.pipeline.Pipeline;
import com.chilborne.covidradar.data.steps.DailyRecordParser;
import com.chilborne.covidradar.data.steps.DailyRecordSaver;
import com.chilborne.covidradar.data.steps.DailyRecordSorter;
import com.chilborne.covidradar.data.steps.DailyRecordTrimmer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DailyRecordPipelineConfig {


    private final DailyRecordParser parser;
    private final DailyRecordTrimmer trimmer;
    private final DailyRecordSorter sorter;
    private final DailyRecordSaver saver;


    public DailyRecordPipelineConfig(
                                        DailyRecordParser parser,
                                        DailyRecordTrimmer trimmer,
                                        DailyRecordSorter sorter,
                                        DailyRecordSaver saver) {
        this.parser = parser;
        this.trimmer = trimmer;
        this.sorter = sorter;
        this.saver = saver;
    }


    @Bean("dailyRecord-Pipeline")
    public Pipeline pipeline() {
        return new Pipeline<>(parser)
                .pipe(trimmer)
                .pipe(sorter)
                .pipe(saver);
    }

}
