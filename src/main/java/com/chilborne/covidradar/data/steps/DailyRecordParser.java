package com.chilborne.covidradar.data.steps;

import com.chilborne.covidradar.exceptions.PipeLineProcessException;
import com.chilborne.covidradar.model.DailyRecord;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;

@Component
public class DailyRecordParser implements Step<String, List<DailyRecord>> {

    private final ObjectMapper mapper;
    private final Logger logger = LoggerFactory.getLogger(DailyRecordParser.class);

    public DailyRecordParser(ObjectMapper mapper) {
        this.mapper = mapper;
    }

    @Override
    public List<DailyRecord> process(String input)  {
            logger.debug("Parsing JSON (hashcode: " + input.hashCode() +")");

        List<DailyRecord> dailyRecordList = List.of();

        try {
            JsonNode listNode = mapper.readTree(input).path("data");
            dailyRecordList = Arrays.asList(mapper.treeToValue(listNode, DailyRecord[].class));
        } catch (JsonProcessingException e) {
            logger.error("Failed to parse JSON (hashcode: " + input.hashCode(), e);
            throw new PipeLineProcessException("Failed to parse JSON data", e);
        }

            logger.debug(dailyRecordList.size() + " Records Parsed.");

            return dailyRecordList;
        }
}

