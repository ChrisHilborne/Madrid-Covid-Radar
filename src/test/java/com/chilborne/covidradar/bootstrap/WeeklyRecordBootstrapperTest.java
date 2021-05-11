package com.chilborne.covidradar.bootstrap;

import com.chilborne.covidradar.data.collection.initialization.DataInitializer;
import com.chilborne.covidradar.data.collection.update.DataUpdater;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class WeeklyRecordBootstrapperTest {

    @Mock
    DataInitializer initializer;

    @Mock
    DataUpdater updater;

    @InjectMocks
    WeeklyRecordBootstrapper bootstrapper;

    @Test

    void bootstrap() throws Exception {
        //when
        bootstrapper.bootstrap();

        //verify
        verify(initializer, times(1)).initializeData();
        verify(updater, times(1)).updateData();
    }
}