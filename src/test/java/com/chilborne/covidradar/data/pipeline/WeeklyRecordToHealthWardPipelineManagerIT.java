package com.chilborne.covidradar.data.pipeline;
import com.chilborne.covidradar.config.WeeklyRecordToHealthWardPipelineConfig;
import com.chilborne.covidradar.model.WeeklyRecord;
import com.chilborne.covidradar.model.HealthWard;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ActiveProfiles("test")
@SpringBootTest
class WeeklyRecordToHealthWardPipelineManagerIT {

    @Autowired
    WeeklyRecordToHealthWardPipelineConfig config;

    WeeklyRecordsToHealthWardPipelineManager manager;

    @BeforeEach
    void init() {
        manager = new WeeklyRecordsToHealthWardPipelineManager(
                config.pipeline()
        );
    }

    @Test
    void pipeline() {
        //given
        WeeklyRecord one = new WeeklyRecord();
        one.setHealthWard("b");
        one.setGeoCode("01");
        one.setDateReported(LocalDate.of(2020, 01, 01));
        WeeklyRecord two = new WeeklyRecord();
        two.setHealthWard("a");
        two.setGeoCode("02");
        two.setDateReported(LocalDate.of(2020, 02, 01));

        List<WeeklyRecord> testInput = List.of(one, two);
        List<HealthWard> expectedResults = List.of(new HealthWard(List.of(two)), new HealthWard(List.of(one)));

        //when
        List<HealthWard> actualResults = manager.startPipeline(testInput);

        //verify
        assertEquals(expectedResults, actualResults);
    }
}
