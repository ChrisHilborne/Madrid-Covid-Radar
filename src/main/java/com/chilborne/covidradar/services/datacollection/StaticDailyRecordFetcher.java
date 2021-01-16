package com.chilborne.covidradar.services.datacollection;

import com.chilborne.covidradar.events.NewDataEventPublisher;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileReader;

@Service
public class StaticDailyRecordFetcher implements DailyRecordFetcher<String> {

    private final NewDataEventPublisher newDataEventPublisher;
    private final File staticDataFile;

    public StaticDailyRecordFetcher(NewDataEventPublisher newDataEventPublisher, File staticDataFile) {
        this.newDataEventPublisher = newDataEventPublisher;
        this.staticDataFile = staticDataFile;
    }

    @Override
    public void fetch() {
        String data = "";
        try (FileReader dataReader = new FileReader(staticDataFile)) {
            StringBuilder sb = new StringBuilder();
            char[] readData = new char[10000];
            while (dataReader.ready()) {
                dataReader.read(readData);
                sb.append(String.valueOf(readData));
            }
            data = sb.toString();
        } catch (Exception e) {
            e.printStackTrace();
        }

        if (isNewData(data)) {
            newDataEvent(data);
        }
    }

    @Override
    public boolean isNewData(String data) {
        return true;
    }

    @Override
    public void newDataEvent(String data) {
        newDataEventPublisher.publishNewDataEvent(data);
    }
}
