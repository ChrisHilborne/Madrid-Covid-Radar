package com.chilborne.covidradar.data.steps;

import com.chilborne.covidradar.exceptions.PipeLineProcessException;
import com.chilborne.covidradar.model.WeeklyRecord;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class WeeklyRecordDataParserTest {

    ObjectMapper mapper = new ObjectMapper()
            .registerModule(new JavaTimeModule());

    WeeklyRecordParser weeklyRecordParser = new WeeklyRecordParser(mapper);

    private String testString = "{\n" +
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
    void process() throws JsonProcessingException {
        //given
        WeeklyRecord weeklyRecord = new WeeklyRecord();
        weeklyRecord.setGeoCode("079603");
        weeklyRecord.setHealthWard("Madrid-Retiro");
        weeklyRecord.setInfectionRateLastTwoWeeks(23);
        weeklyRecord.setInfectionRateTotal(1417);
        weeklyRecord.setCasesLastTwoWeeks(28);
        weeklyRecord.setTotalCases(1691);
        weeklyRecord.setDateReported(LocalDate.of(2020, 07, 01));

        //when
        List<WeeklyRecord> parsedWeeklyRecords = weeklyRecordParser.process(testString);

        //verify
        assertFalse(parsedWeeklyRecords.isEmpty());
        assertEquals(weeklyRecord, parsedWeeklyRecords.get(0));

    }

    @Test
    void processException() {
        //given
        String fail = " { } ";

        //verify
        Exception exception = assertThrows(PipeLineProcessException.class,
                () -> weeklyRecordParser.process(fail));
        assertEquals("Failed to parse JSON data", exception.getMessage());
    }
}