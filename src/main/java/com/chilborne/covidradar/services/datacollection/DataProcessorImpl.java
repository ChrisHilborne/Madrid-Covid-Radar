package com.chilborne.covidradar.services.datacollection;

import com.chilborne.covidradar.model.DailyRecord;
import com.chilborne.covidradar.model.DistrictData;
import com.chilborne.covidradar.services.DistrictDataService;
import com.chilborne.covidradar.util.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class DataProcessorImpl implements DataProcessor {

    private final DistrictDataService districtDataService;
    private final JsonParser<DailyRecord> jsonParser;
    private final Logger logger = LoggerFactory.getLogger(DataProcessorImpl.class);

    public DataProcessorImpl(DistrictDataService districtDataService, JsonParser<DailyRecord> jsonParser) {
        this.districtDataService = districtDataService;
        this.jsonParser = jsonParser;
    }

    @Override
    public void processData(String json) throws JsonProcessingException {
        logger.debug("Starting to Process JSON Data");
        List<DailyRecord> dailyRecords;
        //Parse DailyRecords from JSON
        dailyRecords = jsonParser.parse(json);
        //Filter for DailyRecords for Madrid City only
        dailyRecords = filterData(dailyRecords);
        //Change DailyRecords properties
        dailyRecords = transformData(dailyRecords);
        //Reverse DailyRecords - so [0] is the first day, rather than the last
        dailyRecords = sortData(dailyRecords);
        //Map DailyRecords to each City District
        HashMap<String, List<DailyRecord>> dailyRecordsMappedByDistrict = mapData(dailyRecords);
        //Save each set of DailyRecords as a new DistrictData object
        saveData(dailyRecordsMappedByDistrict);

        logger.debug("Finished Processing JSON data");
    }

    private List<DailyRecord> filterData(List<DailyRecord> dailyRecords) {
        logger.debug("Filtering Data");
        List<DailyRecord> filteredData = dailyRecords.stream()
                .filter(dailyRecord -> dailyRecord.getMunicipalDistrict().matches("Madrid*"))
                .collect(Collectors.toList());

        logger.debug("New Number of Records: " + filteredData.size());
        return filteredData;
    }

    private List<DailyRecord> transformData(List<DailyRecord> dailyRecords) {
        List<DailyRecord> transformedData = dailyRecords;

        transformedData.forEach(dailyRecord ->
        {
            //remove unnecessary characters from District
            dailyRecord.setMunicipalDistrict(dailyRecord.getMunicipalDistrict()
                    .substring(7)
                    .toLowerCase());
            //round infection rates to 2 decimal places
            dailyRecord.setInfectionRateLastTwoWeeks(Math.round( dailyRecord.getInfectionRateLastTwoWeeks() * 100.0) / 100.0 );
            dailyRecord.setInfectionRateTotal(Math.round( dailyRecord.getInfectionRateTotal() * 100.0) / 100.0 );
        });

        return transformedData;
    }

    private List<DailyRecord> sortData(List<DailyRecord> dailyRecords) {
        List<DailyRecord> sortedResults = dailyRecords;
        Collections.reverse(sortedResults);

        return sortedResults;
    }

    private HashMap<String, List<DailyRecord>> mapData(List<DailyRecord> dailyRecords) {
        logger.debug("Mapping DailyRecords By District");
        HashMap<String, List<DailyRecord>> dailyRecordsMappedByDistrict = new HashMap<>();

        dailyRecords
                .forEach(dailyRecord -> {
                    if (dailyRecordsMappedByDistrict.containsKey(dailyRecord.getMunicipalDistrict())) {
                        dailyRecordsMappedByDistrict
                                .get(dailyRecord.getMunicipalDistrict())
                                .add(dailyRecord);
                    }
                    else {
                        List<DailyRecord> districtDailyResults = new ArrayList<DailyRecord>();
                        districtDailyResults.add(dailyRecord);
                        dailyRecordsMappedByDistrict.put(dailyRecord.getMunicipalDistrict(), districtDailyResults);
                    }
                });

        logger.debug("Results Mapped to " + dailyRecordsMappedByDistrict.size() + " Districts In Total");
        return dailyRecordsMappedByDistrict;
    }


    private void saveData(HashMap<String, List<DailyRecord>> dailyRecordsMappedByDistrict) {
        logger.debug("Saving Data");
        dailyRecordsMappedByDistrict.values()
                .stream()
                .map(DistrictData::new)
                .forEach(districtDataService::save);
    }
}
