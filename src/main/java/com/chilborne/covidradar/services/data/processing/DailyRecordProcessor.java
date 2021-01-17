package com.chilborne.covidradar.services.data.processing;

import com.chilborne.covidradar.model.DailyRecord;
import com.chilborne.covidradar.services.DistrictDataService;
import com.chilborne.covidradar.util.JsonParser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class DailyRecordProcessor implements DataProcessor<List<DailyRecord>> {

    private final DistrictDataService districtDataService;
    private final Logger logger = LoggerFactory.getLogger(DailyRecordProcessor.class);

    public DailyRecordProcessor(DistrictDataService districtDataService, JsonParser<DailyRecord> jsonParser) {
        this.districtDataService = districtDataService;
    }

    public List<DailyRecord> processData(List<DailyRecord> dailyRecords)  {

        logger.debug("Starting Processing DailyRecord data");

        List<DailyRecord> processedData = dailyRecords;

        processedData = filterData(dailyRecords);

        processedData= transformData(dailyRecords);

        processedData = reverseData(dailyRecords);

        logger.debug("Finished Processing DailyRecord data");

        return processedData;

    }


    private List<DailyRecord> filterData(List<DailyRecord> dailyRecords) {
        logger.debug("Filtering Data");
        List<DailyRecord> filteredData = dailyRecords.stream()
                .filter(dailyRecord -> dailyRecord.getMunicipalDistrict().matches("Madrid.*"))
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

    private List<DailyRecord> reverseData(List<DailyRecord> dailyRecords) {
        List<DailyRecord> sortedResults = dailyRecords;
        Collections.reverse(sortedResults);

        return sortedResults;
    }

    public Map<String, List<DailyRecord>> mapData(List<DailyRecord> dailyRecords) {
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

}
