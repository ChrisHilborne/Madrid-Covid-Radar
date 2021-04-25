package com.chilborne.covidradar.data.steps;

import com.chilborne.covidradar.exceptions.PipeLineProcessException;
import com.chilborne.covidradar.model.WeeklyRecord;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;

@Component
public class WeeklyRecordParser implements Step<String, List<WeeklyRecord>> {

    private final ObjectMapper mapper;
    private final Logger logger = LoggerFactory.getLogger(WeeklyRecordParser.class);

    public WeeklyRecordParser(ObjectMapper mapper) {
        this.mapper = mapper;
    }

    @Override
    public List<WeeklyRecord> process(String input)  {
            logger.debug("Parsing JSON (hashcode: " + input.hashCode() +")");

        List<WeeklyRecord> weeklyRecordList = List.of();

        try {
            JsonNode listNode = mapper.readTree(input).path("data");
            weeklyRecordList = Arrays.asList(mapper.treeToValue(listNode, WeeklyRecord[].class));
        } catch (JsonProcessingException e) {
            logger.error("Failed to parse JSON (hashcode: " + input.hashCode(), e);
            throw new PipeLineProcessException("Failed to parse JSON data", e);
        }

            logger.debug(weeklyRecordList.size() + " Records Parsed.");

        return weeklyRecordList;
        }
}

