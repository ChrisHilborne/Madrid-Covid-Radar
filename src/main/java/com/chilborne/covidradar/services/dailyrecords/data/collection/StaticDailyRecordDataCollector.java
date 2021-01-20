package com.chilborne.covidradar.services.dailyrecords.data.collection;

import com.chilborne.covidradar.services.dailyrecords.data.event.NewDataEventPublisher;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileReader;

@Service
public class StaticDailyRecordDataCollector implements DailyRecordDataCollector<String> {

    private final NewDataEventPublisher newDataEventPublisher;
    private final File staticDataFile;

    public StaticDailyRecordDataCollector(NewDataEventPublisher newDataEventPublisher,
                                          File staticDataFile)
    {
        this.newDataEventPublisher = newDataEventPublisher;
        this.staticDataFile = staticDataFile;
    }

    @Override
    public void collectData() {
        String data = "";
        try (FileReader dataReader = new FileReader(staticDataFile)) {
            StringBuilder sb = new StringBuilder();
            char[] readData = new char[10000];
            while (dataReader.ready()) {
                dataReader.read(readData);
                sb.append(String.valueOf(readData));
            }
            data = sb.toString().trim();
        } catch (Exception e) {
            e.printStackTrace();
        }
        publish(data);
    }

    @Override
    public void publish(String data) {
        newDataEventPublisher.publishNewDataEvent(data);
    }

}
