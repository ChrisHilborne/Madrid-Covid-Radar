package com.chilborne.covidradar.data.pipeline;

import com.chilborne.covidradar.config.DailyRecordPipelineConfig;
import com.chilborne.covidradar.data.steps.DailyRecordParser;
import com.chilborne.covidradar.data.steps.DailyRecordSaver;
import com.chilborne.covidradar.data.steps.DailyRecordTrimmer;
import com.chilborne.covidradar.model.DailyRecord;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@ActiveProfiles("test")
@ExtendWith(MockitoExtension.class)
@SpringBootTest
class DailyRecordPipelineIT {

    @Autowired
    DailyRecordParser parser;

    @Autowired
    DailyRecordTrimmer trimmer;

    @Mock
    DailyRecordSaver saver;

    DailyRecordPipelineConfig config;

    private final String testJSON = "{\n" +
            "  \"data\": [\n" +
            "    {\n" +
            "      \"codigo_geometria\": \"01\",\n" +
            "      \"zona_basica_salud\": \"test\",\n" +
            "      \"tasa_incidencia_acumulada_ultimos_14dias\": 23.4668991007149,\n" +
            "      \"tasa_incidencia_acumulada_total\": 1417.23308497532,\n" +
            "      \"casos_confirmados_totales\": 1691,\n" +
            "      \"casos_confirmados_ultimos_14dias\": 28,\n" +
            "      \"fecha_informe\": \"2020/07/01 09:00:00\"\n" +
            "    } ] \n" +
            "}";

    @BeforeEach
    void init() {
        config = new DailyRecordPipelineConfig(
                parser,
                trimmer,
                saver
        );
    }

    @Test
    void pipeline() {
        DailyRecord expectedDailyRecord = new DailyRecord();
        expectedDailyRecord.setGeoCode("01");
        expectedDailyRecord.setHealthWard("test");
        expectedDailyRecord.setInfectionRateLastTwoWeeks(23.47);
        expectedDailyRecord.setInfectionRateTotal(1417.23);
        expectedDailyRecord.setCasesLastTwoWeeks(28);
        expectedDailyRecord.setTotalCases(1691);
        expectedDailyRecord.setDateReported(LocalDate.of(2020, 07, 01));

        List<DailyRecord> expectedResults = List.of(expectedDailyRecord);

        //when
        Pipeline<String, List<DailyRecord>> actualPipeline = config.pipeline();
        when(saver.process(expectedResults)).thenReturn(expectedResults);

        List<DailyRecord> actualResults = actualPipeline.execute(testJSON);

        //verify
        assertEquals(expectedResults, actualResults);
    }


}
