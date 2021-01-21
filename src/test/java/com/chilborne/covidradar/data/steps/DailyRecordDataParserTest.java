package com.chilborne.covidradar.data.steps;

import com.chilborne.covidradar.model.DailyRecord;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

class DailyRecordDataParserTest {

    ObjectMapper mapper = new ObjectMapper()
            .registerModule(new JavaTimeModule());

    DailyRecordParser dailyRecordDataParser = new DailyRecordParser(mapper);

    private String testString = "{\n" +
            "  \"data\": [\n" +
            "    {\n" +
            "      \"codigo_geometria\": \"079603\",\n" +
            "      \"municipio_distrito\": \"Madrid-Retiro\",\n" +
            "      \"tasa_incidencia_acumulada_ultimos_14dias\": 23.4668991007149,\n" +
            "      \"tasa_incidencia_acumulada_total\": 1417.23308497532,\n" +
            "      \"casos_confirmados_totales\": 1691,\n" +
            "      \"casos_confirmados_ultimos_14dias\": 28,\n" +
            "      \"fecha_informe\": \"2020/07/01 09:00:00\"\n" +
            "    } ] \n" +
            "}";

    @Test
    void parse() throws JsonProcessingException {
        //given
        DailyRecord dailyRecord = new DailyRecord();
        dailyRecord.setGeoCode("079603");
        dailyRecord.setMunicipalDistrict("Madrid-Retiro");
        dailyRecord.setInfectionRateLastTwoWeeks(23.4668991007149);
        dailyRecord.setInfectionRateTotal(1417.23308497532);
        dailyRecord.setCasesLastTwoWeeks(28);
        dailyRecord.setTotalCases(1691);
        dailyRecord.setDateReported(LocalDate.of(2020, 07, 01));

        //when
        List<DailyRecord> parsedDailyRecords = dailyRecordDataParser.process(testString);

        //verify
        assertFalse(parsedDailyRecords.isEmpty());
        assertEquals(dailyRecord, parsedDailyRecords.get(0));

    }
}