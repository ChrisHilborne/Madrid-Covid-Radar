package com.chilborne.covidradar.services.dailyrecords.data.event;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;

@Component
public class NewDataEventPublisher {

    private final Logger logger = LoggerFactory.getLogger(NewDataEventPublisher.class);

    @Autowired
    ApplicationEventPublisher applicationEventPublisher;

    public void publishNewDataEvent(final String data) {
        logger.debug("Publishing NewDataEvent with data " + data.hashCode() + " (hashcode)");
        NewDataEvent newDataEvent = new NewDataEvent(this, data);

        applicationEventPublisher.publishEvent(newDataEvent);
    }
}
