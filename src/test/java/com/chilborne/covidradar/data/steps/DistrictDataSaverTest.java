package com.chilborne.covidradar.data.steps;

import com.chilborne.covidradar.model.DistrictData;
import com.chilborne.covidradar.services.DistrictDataService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class DistrictDataSaverTest {

    @Mock
    DistrictDataService districtDataService;

    @InjectMocks
    DistrictDataSaver districtDataSaver;

    @Captor
    ArgumentCaptor<List<DistrictData>> argumentCaptor;

    @Test
    void process() {
        DistrictData one = new DistrictData();
        one.setName("one");
        one.setLastUpdated(LocalDate.MIN);

        DistrictData two = new DistrictData();
        two.setName("two");
        two.setLastUpdated(LocalDate.MAX);

        List<DistrictData> toSave = List.of(one, two);

        //when
        when(districtDataService.save(toSave)).thenReturn(toSave);
        districtDataSaver.process(toSave);

        //verify
        verify(districtDataService, times(1)).save(anyList());
        verify(districtDataService, times(1)).save(toSave);

        verify(districtDataService).save(argumentCaptor.capture());
        List<DistrictData> capturedArgument = argumentCaptor.getValue();
        assertEquals(toSave, capturedArgument);

    }
}