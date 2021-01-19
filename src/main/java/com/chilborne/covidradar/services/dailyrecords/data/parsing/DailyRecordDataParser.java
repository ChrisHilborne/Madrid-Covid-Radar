package com.chilborne.covidradar.services.dailyrecords.data.parsing;

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
public class DailyRecordDataParser implements JsonParser<DailyRecord> {

    private final ObjectMapper mapper;
    private final Logger logger = LoggerFactory.getLogger(DailyRecordDataParser.class);

    public DailyRecordDataParser(ObjectMapper mapper) {
        this.mapper = mapper;
    }

    @Override
    public List<DailyRecord> parse(String json)  {
            logger.debug("Parsing JSON (hashcode: " + json.hashCode() +")");

        List<DailyRecord> dailyRecordList = List.of();

        try {
            JsonNode listNode = mapper.readTree(json).path("data");
            dailyRecordList = Arrays.asList(mapper.treeToValue(listNode, DailyRecord[].class));
        } catch (JsonProcessingException e) {
            logger.error("Failed to parse JSON (hashcode: " + json.hashCode(), e);
        }

            logger.debug(dailyRecordList.size() + " Records Parsed.");

            return dailyRecordList;
        }
}

