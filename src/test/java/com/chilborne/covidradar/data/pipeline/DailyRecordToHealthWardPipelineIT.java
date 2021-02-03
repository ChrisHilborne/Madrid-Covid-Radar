package com.chilborne.covidradar.data.pipeline;
import com.chilborne.covidradar.config.DailyRecordToHealthWardPipelineConfig;
import com.chilborne.covidradar.model.DailyRecord;
import com.chilborne.covidradar.model.HealthWard;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ActiveProfiles("test")
@SpringBootTest
class DailyRecordToHealthWardPipelineIT {

    @Autowired
    DailyRecordToHealthWardPipelineConfig config;

    @Test
    void pipeline() {
        //given
        DailyRecord testDailyRecord = new DailyRecord();
        testDailyRecord.setHealthWard("test");
        testDailyRecord.setGeoCode("01");
        testDailyRecord.setDateReported(LocalDate.now());
        List<DailyRecord> testInput = List.of(testDailyRecord);

        List<HealthWard> expectedResults = List.of(new HealthWard((testInput)));

        //when
        Pipeline<List<DailyRecord>, List<HealthWard>> actualPipeline = config.pipeline();
        List<HealthWard> actualResults = actualPipeline.execute(testInput);

        //verify
        assertEquals(expectedResults, actualResults);
    }
}
