package com.chilborne.covidradar.config;

import com.chilborne.covidradar.model.DistrictData;
import com.chilborne.covidradar.data.processing.pipeline.Pipeline;
import com.chilborne.covidradar.data.processing.steps.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
public class DailyRecordDataPipelineConfig {


    private final StaticDataVerifier verifier;
    private final DailyRecordParser parser;
    private final DailyRecordFilter filter;
    private final DailyRecordTrimmer trimmer;
    private final DailyRecordSorter sorter;
    private final DailyRecordMapper mapper;
    private final DailyRecordConverter converter;
    private final DistrictDataSaver saver;


    public DailyRecordDataPipelineConfig(
                                        StaticDataVerifier verifier,
                                        DailyRecordParser parser,
                                        DailyRecordFilter filter,
                                        DailyRecordTrimmer trimmer,
                                        DailyRecordSorter sorter,
                                        DailyRecordMapper mapper,
                                        DailyRecordConverter converter,
                                        DistrictDataSaver saver) {
        this.verifier = verifier;
        this.parser = parser;
        this.filter = filter;
        this.trimmer = trimmer;
        this.sorter = sorter;
        this.mapper = mapper;
        this.converter = converter;
        this.saver = saver;
    }


    @Bean
    public Pipeline<String, List<DistrictData>> pipeline() {
        return new Pipeline<>(verifier)
                .pipe(parser)
                .pipe(filter)
                .pipe(trimmer)
                .pipe(sorter)
                .pipe(mapper)
                .pipe(converter)
                .pipe(saver);
    }

}
