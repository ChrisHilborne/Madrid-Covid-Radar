package com.chilborne.covidradar.events;

import com.chilborne.covidradar.data.collection.DataFetchAction;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;

@Component
public class DataEventPublisher {

    private final Logger logger = LoggerFactory.getLogger(DataEventPublisher.class);

    private final ApplicationEventPublisher applicationEventPublisher;

    public DataEventPublisher(ApplicationEventPublisher applicationEventPublisher) {
        this.applicationEventPublisher = applicationEventPublisher;
    }

    public void publishDataEvent(final String data, DataFetchAction eventType) {
        logger.debug("Publishing" + eventType + " DataEvent with data " + data.hashCode() + " (hashcode)");

        if (eventType.equals(DataFetchAction.UPDATE)) {
            UpdatedDataEvent updatedDataEvent = new UpdatedDataEvent(this, data);
            applicationEventPublisher.publishEvent(updatedDataEvent);
        }
        else {
            InitialDataEvent initialDataEvent = new InitialDataEvent(this, data);
            applicationEventPublisher.publishEvent(initialDataEvent);
        }
    }
}
