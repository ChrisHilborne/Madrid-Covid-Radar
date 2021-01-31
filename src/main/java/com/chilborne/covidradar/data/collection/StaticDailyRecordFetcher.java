package com.chilborne.covidradar.data.collection;

import com.chilborne.covidradar.events.NewDataEventPublisher;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileReader;

@Service
@Profile(value = "static")
public class StaticDailyRecordFetcher implements DataFetcher {

    private final NewDataEventPublisher newDataEventPublisher;
    private final File staticDataFile;

    public StaticDailyRecordFetcher(NewDataEventPublisher newDataEventPublisher,
                                    File staticDataFile)
    {
        this.newDataEventPublisher = newDataEventPublisher;
        this.staticDataFile = staticDataFile;
    }

    @Override
    public void fetchData(DataFetchType type) {
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
        publishData(data);
    }

    @Override
    public void publishData(String data) {
        newDataEventPublisher.publishNewDataEvent(data);
    }

}
