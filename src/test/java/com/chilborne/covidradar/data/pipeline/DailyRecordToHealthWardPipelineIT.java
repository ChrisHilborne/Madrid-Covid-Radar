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
        DailyRecord one = new DailyRecord();
        one.setHealthWard("b");
        one.setGeoCode("01");
        one.setDateReported(LocalDate.of(2020, 01, 01));
        DailyRecord two = new DailyRecord();
        two.setHealthWard("a");
        two.setGeoCode("02");
        two.setDateReported(LocalDate.of(2020, 02, 01));

        List<DailyRecord> testInput = List.of(one, two);
        List<HealthWard> expectedResults = List.of(new HealthWard(List.of(two)), new HealthWard(List.of(one)));

        //when
        Pipeline<List<DailyRecord>, List<HealthWard>> actualPipeline = config.pipeline();
        List<HealthWard> actualResults = actualPipeline.execute(testInput);

        //verify
        assertEquals(expectedResults, actualResults);
    }
}
