package com.chilborne.covidradar.data;

import com.chilborne.covidradar.data.collection.DataFetchAction;
import com.chilborne.covidradar.data.collection.DataFetcher;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class DailyRecordDataInitializerTest {

    @Mock
    DataFetcher dataFetcher;

    @InjectMocks
    DailyRecordDataInitializer dailyRecordDataInitializer;

    @Test
    void initializeData() {
        //when
        try {
            dailyRecordDataInitializer.initializeData();
        } catch (Exception e) { }

        //verify
        verify(dataFetcher,times(2)).fetchData(any());
        verify(dataFetcher,times(1)).fetchData(DataFetchAction.INIT);
        verify(dataFetcher,times(1)).fetchData(DataFetchAction.UPDATE);
    }

}