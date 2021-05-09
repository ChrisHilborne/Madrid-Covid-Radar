package com.chilborne.covidradar.data.pipeline;

import com.chilborne.covidradar.config.WeeklyRecordInitializePipelineConfig;
import com.chilborne.covidradar.data.steps.*;
import com.chilborne.covidradar.model.WeeklyRecord;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@SpringBootTest
@ActiveProfiles("test")
@ExtendWith(MockitoExtension.class)
public class WeeklyRecordInitializePipelineIT {

    @Autowired
    WeeklyRecordParser parser;

    @Autowired
    WeeklyRecordFilter filter;

    @Autowired
    WeeklyRecordFixer trimmer;

    @Autowired
    WeeklyRecordMapper mapper;

    @Autowired
    WeeklyRecordAggregator aggregator;

    @Mock
    WeeklyRecordSaver saver;

    WeeklyRecordInitializePipelineConfig config;

    @Captor
    ArgumentCaptor<List<WeeklyRecord>> weeklyRecordCaptor;

    private final String testJSON = "{\n" +
            "  \"data\": [\n" +
            "    {\n" +
            "      \"codigo_geometria\": \"01\",\n" +
            "      \"zona_basica_salud\": \"Test\",\n" +
            "      \"tasa_incidencia_acumulada_ultimos_14dias\": 23.4668991007149,\n" +
            "      \"tasa_incidencia_acumulada_total\": 1417.23308497532,\n" +
            "      \"casos_confirmados_totales\": 1691,\n" +
            "      \"casos_confirmados_ultimos_14dias\": 28,\n" +
            "      \"fecha_informe\": \"2020/05/01 09:00:00\"\n" +
            "    } ] \n" +
            "}";

    @BeforeEach
    void init() {
        config = new WeeklyRecordInitializePipelineConfig(
                parser,
                filter,
                trimmer,
                mapper,
                aggregator,
                saver
        );

    }

    @Test
    void pipeline() {
        //given
        WeeklyRecord expectedWeeklyRecord = new WeeklyRecord();
        expectedWeeklyRecord.setGeoCode("test");
        expectedWeeklyRecord.setHealthWard("Test");
        expectedWeeklyRecord.setInfectionRateLastTwoWeeks(23);
        expectedWeeklyRecord.setInfectionRateTotal(1417);
        expectedWeeklyRecord.setCasesLastTwoWeeks(28);
        expectedWeeklyRecord.setTotalCases(1691);
        expectedWeeklyRecord.setDateReported(LocalDate.of(2020, 5, 1));

        List<WeeklyRecord> expectedResults = List.of(expectedWeeklyRecord);

        //when
        Pipeline<String, List<WeeklyRecord>> pipeline = config.pipeline();
        when(saver.process(any())).thenReturn(expectedResults);

        List<WeeklyRecord> actualResults = pipeline.execute(testJSON);

        //verify
        verify(saver).process(weeklyRecordCaptor.capture());
        assertEquals(expectedResults, weeklyRecordCaptor.getValue());
        assertEquals(expectedResults, actualResults);
    }


}
