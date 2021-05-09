package com.chilborne.covidradar.data.pipeline;

import com.chilborne.covidradar.config.WeeklyUpdatePipelineConfig;
import com.chilborne.covidradar.data.steps.HttpResponseValidator;
import com.chilborne.covidradar.data.steps.WeeklyRecordFixer;
import com.chilborne.covidradar.data.steps.WeeklyRecordParser;
import com.chilborne.covidradar.data.steps.WeeklyRecordSaver;
import com.chilborne.covidradar.model.WeeklyRecord;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.net.http.HttpResponse;
import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@ActiveProfiles("test")
@ExtendWith(MockitoExtension.class)
@SpringBootTest
class WeeklyRecordUpdaterPipelineManagerIT {

    @Mock
    HttpResponseValidator validator;

    @Autowired
    WeeklyRecordParser parser;

    @Autowired
    WeeklyRecordFixer trimmer;

    @Mock
    WeeklyRecordSaver saver;

    WeeklyUpdatePipelineConfig config;

    WeeklyRecordUpdatePipelineManager manager;

    private final String testJSON = "{\n" +
            "  \"data\": [\n" +
            "    {\n" +
            "      \"codigo_geometria\": \"01\",\n" +
            "      \"zona_basica_salud\": \"Test\",\n" +
            "      \"tasa_incidencia_acumulada_ultimos_14dias\": 23.4668991007149,\n" +
            "      \"tasa_incidencia_acumulada_total\": 1417.23308497532,\n" +
            "      \"casos_confirmados_totales\": 1691,\n" +
            "      \"casos_confirmados_ultimos_14dias\": 28,\n" +
            "      \"fecha_informe\": \"2020/07/01 09:00:00\"\n" +
            "    } ] \n" +
            "}";

    @BeforeEach
    void init() {
        config = new WeeklyUpdatePipelineConfig(
                validator,
                parser,
                trimmer,
                saver
        );

        manager = new WeeklyRecordUpdatePipelineManager(
                config.pipeline()
        );
    }

    @Test
    void pipeline() {
        //given
        HttpResponse<String> mockResponse = mock(HttpResponse.class);

        WeeklyRecord expectedWeeklyRecord = new WeeklyRecord();
        expectedWeeklyRecord.setGeoCode("test");
        expectedWeeklyRecord.setHealthWard("Test");
        expectedWeeklyRecord.setInfectionRateLastTwoWeeks(23);
        expectedWeeklyRecord.setInfectionRateTotal(1417);
        expectedWeeklyRecord.setCasesLastTwoWeeks(28);
        expectedWeeklyRecord.setTotalCases(1691);
        expectedWeeklyRecord.setDateReported(LocalDate.of(2020, 07, 01));

        List<WeeklyRecord> expectedResults = List.of(expectedWeeklyRecord);

        //when
        when(validator.process(mockResponse)).thenReturn(testJSON);
        when(saver.process(expectedResults)).thenReturn(expectedResults);

        List<WeeklyRecord> actualResults = manager.startPipeline(mockResponse);

        //verify
        assertEquals(expectedResults, actualResults);
    }


}
