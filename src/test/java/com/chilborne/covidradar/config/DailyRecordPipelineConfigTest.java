package com.chilborne.covidradar.config;

import com.chilborne.covidradar.data.pipeline.Pipeline;
import com.chilborne.covidradar.data.steps.DailyRecordParser;
import com.chilborne.covidradar.data.steps.DailyRecordSaver;
import com.chilborne.covidradar.data.steps.DailyRecordTrimmer;
import com.chilborne.covidradar.model.DailyRecord;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(MockitoExtension.class)
class DailyRecordPipelineConfigTest {

    @Mock
    DailyRecordParser parser;

    @Mock
    DailyRecordTrimmer trimmer;

    @Mock
    DailyRecordSaver saver;

    @InjectMocks
    DailyRecordPipelineConfig config;

    private String testJSON = "{\n" +
            "  \"data\": [\n" +
            "    {\n" +
            "      \"codigo_geometria\": \"079603\",\n" +
            "      \"zona_basica_salud\": \"Madrid-Retiro\",\n" +
            "      \"tasa_incidencia_acumulada_ultimos_14dias\": 23.4668991007149,\n" +
            "      \"tasa_incidencia_acumulada_total\": 1417.23308497532,\n" +
            "      \"casos_confirmados_totales\": 1691,\n" +
            "      \"casos_confirmados_ultimos_14dias\": 28,\n" +
            "      \"fecha_informe\": \"2020/07/01 09:00:00\"\n" +
            "    } ] \n" +
            "}";

    @Test
    void pipeline() {
        //given
        Pipeline<String, List<DailyRecord>> testPipeline =
                new Pipeline(parser)
                    .pipe(trimmer)
                    .pipe(saver);

        List<DailyRecord> expectedResults = testPipeline.execute(testJSON);

        //when
        Pipeline<String, List<DailyRecord>> actualPipeline = config.pipeline();
        List<DailyRecord> actualResults = actualPipeline.execute(testJSON);

        //verify
        assertEquals(expectedResults, actualResults);
    }
}