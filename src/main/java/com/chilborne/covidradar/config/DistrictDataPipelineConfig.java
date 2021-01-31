package com.chilborne.covidradar.config;

import com.chilborne.covidradar.data.pipeline.Pipeline;
import com.chilborne.covidradar.data.steps.DailyRecordConverter;
import com.chilborne.covidradar.data.steps.DailyRecordMapper;
import com.chilborne.covidradar.data.steps.DailyRecordSorter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DistrictDataPipelineConfig {

    private final DailyRecordSorter sorter;
    private final DailyRecordMapper mapper;
    private final DailyRecordConverter converter;

    public DistrictDataPipelineConfig(DailyRecordSorter sorter,
                                      DailyRecordMapper mapper,
                                      DailyRecordConverter converter) {
        this.sorter = sorter;
        this.mapper = mapper;
        this.converter = converter;
    }

    @Bean("districtData-Pipeline")
    public Pipeline pipeline() {
        return new Pipeline(sorter)
                .pipe(mapper)
                .pipe(converter);
    }
}
