package com.chilborne.covidradar.config;

import com.chilborne.covidradar.data.pipeline.Pipeline;
import com.chilborne.covidradar.data.steps.WeeklyRecordConverter;
import com.chilborne.covidradar.data.steps.WeeklyRecordMapper;
import com.chilborne.covidradar.data.steps.WeeklyRecordSorter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class WeeklyRecordToHealthWardPipelineConfig {

    private final WeeklyRecordSorter sorter;
    private final WeeklyRecordMapper mapper;
    private final WeeklyRecordConverter converter;

    public WeeklyRecordToHealthWardPipelineConfig(WeeklyRecordSorter sorter,
                                                  WeeklyRecordMapper mapper,
                                                  WeeklyRecordConverter converter) {
        this.sorter = sorter;
        this.mapper = mapper;
        this.converter = converter;
    }

    @Bean("weeklyRecord-to-healthWard-Pipeline")
    public Pipeline pipeline() {
        return new Pipeline(sorter)
                .pipe(mapper)
                .pipe(converter);
    }
}
