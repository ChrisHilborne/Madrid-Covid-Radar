package com.chilborne.covidradar.data.collection.initialization;

import com.chilborne.covidradar.data.collection.DataFetchAction;
import com.chilborne.covidradar.data.collection.DataFetcher;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

@Profile("!test")
@Component
public class WeeklyRecordDataInitializer implements DataInitializer {

    private final DataFetcher dataFetcher;
    private final Logger logger = LoggerFactory.getLogger(WeeklyRecordDataInitializer.class);

    public WeeklyRecordDataInitializer(DataFetcher dataFetcher) {
        this.dataFetcher = dataFetcher;
    }

    @Override
    public void run(String... args) throws Exception {
        try {
            initializeData();
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
    }

    @Override
    public void initializeData() throws Exception {
        logger.debug("Initialising WeeklyRecord data...");
        dataFetcher.fetchData(DataFetchAction.INIT);
        dataFetcher.fetchData(DataFetchAction.UPDATE);
        logger.debug("Data successfully initialised.");
    }
}
