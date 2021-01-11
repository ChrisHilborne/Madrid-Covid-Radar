package com.chilborne.covidradar.services.datacollection;

import com.chilborne.covidradar.model.DailyRecord;
import com.chilborne.covidradar.model.DistrictData;
import com.chilborne.covidradar.services.DistrictDataService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class DataProcessorImpl implements DataProcessor {

    private final DistrictDataService districtDataService;
    private static Logger logger = LoggerFactory.getLogger(DataProcessorImpl.class);

    public DataProcessorImpl(DistrictDataService districtDataService) {
        this.districtDataService = districtDataService;
    }

    @Override
    public void processData(String json) throws JsonProcessingException {
        logger.debug("Strarting to Process JSON Data");

        List<DailyRecord> dailyRecordList;
        dailyRecordList = parseJsonDailyFigures(json);
        List<DailyRecord> filteredResults = filterResults(dailyRecordList);
        HashMap<String, List<DailyRecord>> resultsByDistrict = orderResults(filteredResults);
        saveData(resultsByDistrict);

        logger.debug("Finished Processing JSON data");
    }

    private HashMap<String, List<DailyRecord>> orderResults(List<DailyRecord> filteredResults) {
        logger.debug("Ordering Data By District");
        HashMap<String, List<DailyRecord>> orderedResults = new HashMap<>();
        filteredResults.stream()
                .forEachOrdered(dailyRecord -> {
                    if (orderedResults.containsKey(dailyRecord.getMunicipalDistrict())) {
                        orderedResults
                                .get(dailyRecord.getMunicipalDistrict())
                                .add(dailyRecord);
                    }
                    else {
                        List<DailyRecord> dailyRecords = new ArrayList<DailyRecord>();
                        dailyRecords.add(dailyRecord);
                        orderedResults.put(dailyRecord.getMunicipalDistrict(), dailyRecords);
                    }
                });


        logger.debug(orderedResults.size() + " Districts In Total");
        return orderedResults;
    }

    private List<DailyRecord> parseJsonDailyFigures(String json) throws JsonProcessingException {
        logger.debug("Parsing JSON");

        List<DailyRecord> dailyRecordList;
        ObjectMapper mapper = new ObjectMapper()
                .configure(DeserializationFeature.FAIL_ON_UNRESOLVED_OBJECT_IDS, false)
                .registerModule(new JavaTimeModule());
        JsonNode listNode = mapper.readTree(json).path("data");
        dailyRecordList = Arrays.asList(mapper.treeToValue(listNode, DailyRecord[].class));

        logger.debug(dailyRecordList.size() + " Records Parsed.");

        return dailyRecordList;
    }

    private List<DailyRecord> filterResults(List<DailyRecord> dailyRecordList) {
        logger.debug("Filtering Data");

        List<DailyRecord> filteredData = new ArrayList<>();
        for (DailyRecord dailyRecord : dailyRecordList) {
            if (dailyRecord.getMunicipalDistrict().matches("Madrid.*")) {
                dailyRecord.setMunicipalDistrict(dailyRecord.getMunicipalDistrict()
                        .substring(7)
                        .toLowerCase());
                filteredData.add(dailyRecord);
            }
        }
        Collections.reverse(filteredData);
        logger.debug("New Number of Records: " + filteredData.size());
        return filteredData;
    }


    private void saveData(HashMap<String, List<DailyRecord>> resultsByDistrict) {
        logger.debug("Saving Data");
        resultsByDistrict.values()
                .stream()
                .map(DistrictData::new)
                .forEach(districtDataService::save);
    }
}
