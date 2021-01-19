package com.chilborne.covidradar.services.dailyrecords.data;

import com.chilborne.covidradar.model.DailyRecord;
import com.chilborne.covidradar.model.DistrictData;
import com.chilborne.covidradar.services.dailyrecords.data.collection.DailyRecordDataCollector;
import com.chilborne.covidradar.services.dailyrecords.data.parsing.JsonParser;
import com.chilborne.covidradar.services.dailyrecords.data.processing.interfaces.*;
import com.chilborne.covidradar.services.dailyrecords.data.verification.DataVerifier;
import com.chilborne.covidradar.services.districtdata.DistrictDataService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class DailyRecordDataManager implements DataManager {

    private final Logger logger = LoggerFactory.getLogger(DailyRecordDataManager.class);

    private final DailyRecordDataCollector<String> dataCollector;
    private final DataVerifier<String> dataVerifier;
    private final JsonParser<DailyRecord> dataParser;
    private final DataFilter<List<DailyRecord>> dataFilter;
    private final DataTrimmer<List<DailyRecord>> dataTrimmer;
    private final DataSorter<List<DailyRecord>> dataSorter;
    private final DataMapper<List<DailyRecord>> dataMapper;
    private final DataTransformer dataTransformer;
    private final DistrictDataService districtDataService;

    public DailyRecordDataManager(DailyRecordDataCollector<String> collector,
                                  DataVerifier<String> verifier,
                                  JsonParser<DailyRecord> parser,
                                  DataFilter<List<DailyRecord>> dataFilter,
                                  DataTrimmer<List<DailyRecord>> dataTrimmer,
                                  DataSorter<List<DailyRecord>> dataSorter,
                                  DataMapper dataMapper,
                                  DataTransformer dataConverter,
                                  DistrictDataService districtDataService)
    {
        this.dataCollector = collector;
        this.dataVerifier = verifier;
        this.dataParser = parser;
        this.dataFilter = dataFilter;
        this.dataTrimmer = dataTrimmer;
        this.dataSorter = dataSorter;
        this.dataMapper = dataMapper;
        this.dataTransformer = dataConverter;
        this.districtDataService = districtDataService;
    }

    @Override
    public boolean newData()  {

        String data = dataCollector.collectData();

        if (!dataVerifier.verifyData(data)) {
            logger.debug("Data is not new.");
            return false;
        }


        List<DailyRecord> parsedData = dataParser.parse(data);

        if (parsedData.size() == 0) {
            return false;
        }

        List<DailyRecord> filteredData = dataFilter.filter(parsedData);

        List<DailyRecord> trimmedData = dataTrimmer.trim(filteredData);

        List<DailyRecord> sortedData = dataSorter.sort(trimmedData);

        Map<String, List<DailyRecord>> mappedData = dataMapper.map(sortedData);

        List<DistrictData> convertedData = dataTransformer.transform(mappedData);

        districtDataService.save(convertedData);

        return true;
        }

}


