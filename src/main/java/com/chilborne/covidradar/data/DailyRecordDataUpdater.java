package com.chilborne.covidradar.data;

import com.chilborne.covidradar.data.collection.DataFetchAction;
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

    @Override
    @Scheduled(cron = "0 0 6,9,12,15,18,21 ? * *")
    public void updateData() {
        logger.debug("Updating DailyRecord data...");
        try {
            dataFetcher.fetchData(DataFetchAction.UPDATE);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
    }
}
