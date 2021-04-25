package com.chilborne.covidradar.data;

import com.chilborne.covidradar.data.collection.DataFetchAction;
import com.chilborne.covidradar.data.collection.DataFetcher;
import com.chilborne.covidradar.data.collection.update.WeeklyRecordDataUpdater;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class WeeklyRecordDataUpdaterTest {

    @Mock
    DataFetcher dataFetcher;

    @InjectMocks
    WeeklyRecordDataUpdater weeklyRecordDataUpdater;

    @Test
    void updateData() {
        //when
        weeklyRecordDataUpdater.updateData();

        //verify
        verify(dataFetcher, times(1)).fetchData(any());
        verify(dataFetcher, times(1)).fetchData(DataFetchAction.UPDATE);
    }
}