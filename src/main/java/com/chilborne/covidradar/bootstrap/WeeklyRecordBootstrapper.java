package com.chilborne.covidradar.bootstrap;

import com.chilborne.covidradar.data.collection.initialization.DataInitializer;
import com.chilborne.covidradar.data.collection.update.DataUpdater;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class WeeklyRecordBootstrapper implements Bootstrapper {

    private final DataInitializer initializer;
    private final DataUpdater updater;

    private final Logger logger = LoggerFactory.getLogger(WeeklyRecordBootstrapper.class);

    public WeeklyRecordBootstrapper(DataInitializer initializer,
                                    DataUpdater updater) {
        this.initializer = initializer;
        this.updater = updater;
    }

    @Override
    public void run(String... args) throws Exception {
        bootstrap();
    }

    @Override
    public void bootstrap() {
        logger.debug("Starting to bootstrap data...");
        try {
            initializer.initializeData();
            updater.updateData();
        } catch (Exception e) {
            logger.error("Problem when bootstrapping data" ,e);
        }
        logger.debug("Data successfully bootstrapped.");
    }
}
