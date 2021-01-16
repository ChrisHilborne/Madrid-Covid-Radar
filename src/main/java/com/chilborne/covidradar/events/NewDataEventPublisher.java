package com.chilborne.covidradar.events;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;

@Component
public class NewDataEventPublisher {

    private final ApplicationEventPublisher eventPublisher;
    private final Logger logger = LoggerFactory.getLogger(NewDataEventPublisher.class);

    public NewDataEventPublisher(ApplicationEventPublisher eventPublisher) {
        this.eventPublisher = eventPublisher;
    }

    public void publishNewDataEvent(final String data) {
        logger.debug("Publishing NewDataEvent with data: " + data.hashCode() + " (hashcode)");
        NewDataEvent newDataEvent = new NewDataEvent(this, data);
        eventPublisher.publishEvent(newDataEvent);
    }
}
