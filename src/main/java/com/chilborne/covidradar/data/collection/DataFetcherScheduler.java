package com.chilborne.covidradar.data.collection;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

@Service
public class DataFetcherScheduler {

    private final DataFetcher dataFetcher;
    private final Logger logger = LoggerFactory.getLogger(DataFetcherScheduler.class);

    public DataFetcherScheduler(DataFetcher dataFetcher) {
        this.dataFetcher = dataFetcher;
    }

    @EventListener
    public void initialiseDataOnStartUp(ContextRefreshedEvent event) {
        logger.debug("Initialising data...");
        dataFetcher.fetchData(DataFetchType.INIT);
        dataFetcher.fetchData(DataFetchType.UPDATE);
        logger.debug("Data Initialised");
    }

    @Scheduled(cron = "0 0 6,9,12,15,18,21 ? * *")
    public void updateData() {
        logger.debug("Updating data...");
        dataFetcher.fetchData(DataFetchType.UPDATE);
        logger.debug("Data updated.");
    }



}
