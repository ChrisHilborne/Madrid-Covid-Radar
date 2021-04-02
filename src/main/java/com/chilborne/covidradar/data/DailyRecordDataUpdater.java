package com.chilborne.covidradar.data;

import com.chilborne.covidradar.data.collection.DataFetcher;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

@Service
public class DailyRecordDataUpdater implements DataUpdater {

    private final DataFetcher dataFetcher;
    private final Logger logger = LoggerFactory.getLogger(DailyRecordDataUpdater.class);

    public DailyRecordDataUpdater(DataFetcher dataFetcher) {
        this.dataFetcher = dataFetcher;
    }


    @Overrides
    @Scheduled(cron = "0 0 6,9,12,15,18,21 ? * *")
    public void updateData() {
        logger.debug("Updating DailyRecord data...");
        //dataFetcher.fetchData(DataFetchAction.UPDATE);
    }
}
