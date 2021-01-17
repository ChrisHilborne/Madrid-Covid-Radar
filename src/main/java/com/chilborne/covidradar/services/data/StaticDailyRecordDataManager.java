package com.chilborne.covidradar.services.data;

import com.chilborne.covidradar.model.DailyRecord;
import com.chilborne.covidradar.services.data.collection.DailyRecordDataCollector;
import com.chilborne.covidradar.services.data.processing.DataProcessor;
import com.chilborne.covidradar.services.data.processing.DataSaver;
import com.chilborne.covidradar.services.data.verification.DataVerifier;
import com.chilborne.covidradar.util.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;

import java.util.List;
import java.util.Map;

public class StaticDailyRecordDataManager implements DataManager {

    private final DailyRecordDataCollector<String> dataCollector;
    private final DataVerifier<String> dataVerifier;
    private final JsonParser<DailyRecord> dataParser;
    private final DataProcessor<List<DailyRecord>> dataProcessor;
    private final DataSaver<Map<String, List<DailyRecord>>> dataSaver;

    public StaticDailyRecordDataManager(DailyRecordDataCollector<String> collector,
                                        DataVerifier<String> verifier,
                                        JsonParser<DailyRecord> parser,
                                        DataProcessor<List<DailyRecord>> processor,
                                        DataSaver<Map<String, List<DailyRecord>>> saver) {
        this.dataCollector = collector;
        this.dataVerifier = verifier;
        this.dataParser = parser;
        this.dataProcessor = processor;
        this.dataSaver = saver;
    }

    @Override
    public boolean getData() throws JsonProcessingException {
        String data = dataCollector.collectData();

        if (!dataVerifier.verifyData(data)) {
            return false;
        }

        List<DailyRecord> parsedData = dataParser.parse(data);

        List<DailyRecord> processedData = dataProcessor.processData(parsedData);

        Map<String, List<DailyRecord>> mappedData = dataProcessor.mapData(processedData);

        dataSaver.saveData(mappedData);

        return true;
        }

}


