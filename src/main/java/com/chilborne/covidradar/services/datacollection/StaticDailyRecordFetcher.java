package com.chilborne.covidradar.services.datacollection;

import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileReader;

@Service
public class StaticDailyRecordFetcher implements DailyFigureFetcher {

    private final DataProcessor dataProcessor;

    private File dataFile;
    private FileReader dataReader;
    private String data = "";

    public StaticDailyRecordFetcher(DataProcessor dataProcessor)  {
        this.dataProcessor = dataProcessor;
    }

    @Override
    public void fetch() {
        try {
            dataFile = new File("./src/main/resources/static/testdata.json");
            dataReader = new FileReader(dataFile);
            StringBuilder sb = new StringBuilder();
            char[] readData = new char[10000];

            while (dataReader.ready()) {
                dataReader.read(readData);
                sb.append(String.valueOf(readData));
            }
            data = sb.toString();
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void processData() {
        try {
            dataProcessor.processData(data);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
    }
}
