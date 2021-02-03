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
class DailyRecordDataUpdaterTest {

    @Mock
    DataFetcher dataFetcher;

    @InjectMocks
    DailyRecordDataUpdater dailyRecordDataUpdater;

    @Test
    void updateData() {
        //when
        dailyRecordDataUpdater.updateData();

        //verify
        verify(dataFetcher, times(1)).fetchData(any());
        verify(dataFetcher, times(1)).fetchData(DataFetchAction.UPDATE);
    }
}