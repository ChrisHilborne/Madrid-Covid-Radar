package com.chilborne.covidradar.events;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.context.ApplicationEventPublisher;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class NewDataEventPublisherTest {

    @Mock
    ApplicationEventPublisher applicationEventPublisher;

    @InjectMocks
    NewDataEventPublisher newDataEventPublisher;

    @Captor
    ArgumentCaptor<NewDataEvent> argumentCaptor;

    @Test
    void publishNewDataEvent() {
        //given
        String testData = "test data";

        //when
        newDataEventPublisher.publishNewDataEvent(testData);

        //verify
        verify(applicationEventPublisher).publishEvent(argumentCaptor.capture());
        NewDataEvent newDataEvent = argumentCaptor.getValue();

        assertEquals(testData, newDataEvent.getData());
        verify(applicationEventPublisher, times(1)).publishEvent(any());
        verify(applicationEventPublisher, times(1)).publishEvent(newDataEvent);

    }
}