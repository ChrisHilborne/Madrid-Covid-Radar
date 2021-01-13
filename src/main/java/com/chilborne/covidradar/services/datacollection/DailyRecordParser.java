package com.chilborne.covidradar.services.datacollection;

import com.chilborne.covidradar.model.DailyRecord;
import com.chilborne.covidradar.util.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import java.util.Arrays;
import java.util.List;

@Service
public class DailyRecordParser implements JsonParser<DailyRecord> {

    private final ObjectMapper mapper;
    private final Logger logger = LoggerFactory.getLogger(DailyRecordParser.class);

    public DailyRecordParser(ObjectMapper mapper) {
        this.mapper = mapper;
    }

    @Override
    public List<DailyRecord> parse(String json) throws JsonProcessingException {
            logger.debug("Parsing JSON (hashcode: " + json.hashCode() +")");

            List<DailyRecord> dailyRecordList;
            JsonNode listNode = mapper.readTree(json).path("data");
            dailyRecordList = Arrays.asList(mapper.treeToValue(listNode, DailyRecord[].class));

            logger.debug(dailyRecordList.size() + " Records Parsed.");

            return dailyRecordList;
        }
}

