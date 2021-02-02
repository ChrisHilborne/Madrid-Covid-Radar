package com.chilborne.covidradar.config;

import com.chilborne.covidradar.data.pipeline.Pipeline;
import com.chilborne.covidradar.data.steps.DailyRecordConverter;
import com.chilborne.covidradar.data.steps.DailyRecordMapper;
import com.chilborne.covidradar.data.steps.DailyRecordSorter;
import com.chilborne.covidradar.model.DailyRecord;
import com.chilborne.covidradar.model.HealthWard;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(MockitoExtension.class)
class DailyRecordToHealthWardPipelineConfigTest {

    @Mock
    DailyRecordSorter sorter;

    @Mock
    DailyRecordMapper mapper;

    @Mock
    DailyRecordConverter converter;

    @InjectMocks
    DailyRecordToHealthWardPipelineConfig config;

    @Test
    void pipeline() {
        LocalDate testDate = LocalDate.now();

        //given
        Pipeline<List<DailyRecord>, List<HealthWard>> testPipeline =
                new Pipeline<>(sorter)
                        .pipe(mapper)
                        .pipe(converter);

        DailyRecord testDailyRecord = new DailyRecord();
        testDailyRecord.setHealthWard("test");
        testDailyRecord.setGeoCode("01");
        testDailyRecord.setDateReported(testDate);
        List<DailyRecord> testInput = List.of(testDailyRecord);

        List<HealthWard> expectedResults = testPipeline.execute(testInput);

        //when
        Pipeline<List<DailyRecord>, List<HealthWard>> actualPipeline = config.pipeline();
        List<HealthWard> actualResults = actualPipeline.execute(testInput);

        //verify
        assertEquals(expectedResults, actualResults);
    }
}