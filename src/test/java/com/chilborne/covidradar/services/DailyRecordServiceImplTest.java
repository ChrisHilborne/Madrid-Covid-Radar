package com.chilborne.covidradar.services;

import com.chilborne.covidradar.exceptions.DataNotFoundException;
import com.chilborne.covidradar.exceptions.DataSaveException;
import com.chilborne.covidradar.model.DailyRecord;
import com.chilborne.covidradar.repository.DailyRecordRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class DailyRecordServiceImplTest {

    @Mock
    DailyRecordRepository repository;

    @InjectMocks
    DailyRecordServiceImpl service;

    @Captor
    ArgumentCaptor<DailyRecord> dailyRecordArgumentCaptor;

    @Captor
    ArgumentCaptor<List<DailyRecord>> listArgumentCaptor;

    LocalDate testDate = LocalDate.now();

    @Test
    void save() {
        //given
        DailyRecord test = new DailyRecord();
        test.setHealthWard("test");
        test.setDateReported(testDate);

        //when
        when(repository.save(test)).thenReturn(test);
        DailyRecord saved = service.save(test);

        //verify
        verify(repository).save(dailyRecordArgumentCaptor.capture());
        assertAll("save",
                () -> assertEquals(test, dailyRecordArgumentCaptor.getValue()),
                () -> assertEquals("test", saved.getHealthWard()),
                () -> assertEquals(testDate, saved.getDateReported())
        );

    }

    @Test
    void save_Exception() {
        //given
        DailyRecord test = new DailyRecord();
        test.setHealthWard("test");
        test.setDateReported(testDate);

        //when
        when(repository.save(test)).thenReturn(new DailyRecord());

        //verify
        Exception e = assertThrows(DataSaveException.class, () -> service.save(test));
        assertEquals("Failed to save DailyRecord id: " + test.getId(), e.getMessage());
    }


    @Test
    void saveList() {
        //given
        DailyRecord test = new DailyRecord();
        test.setHealthWard("test");
        test.setDateReported(testDate);
        List<DailyRecord> toSave = List.of(test);

        //when
        when(repository.save(test)).thenReturn(test);
        List<DailyRecord> returned = service.save(toSave);

        //verify
        verify(repository).save(dailyRecordArgumentCaptor.capture());
        assertEquals(test, dailyRecordArgumentCaptor.getValue());
        assertAll("saveList",
                () -> assertEquals(1, returned.size()),
                () -> assertEquals("test", returned.get(0).getHealthWard()),
                () -> assertEquals(testDate, returned.get(0).getDateReported())
        );
    }

    @Test
    void saveList_Exception() {
        //given
        DailyRecord test = new DailyRecord();
        test.setHealthWard("test");
        test.setDateReported(testDate);
        List<DailyRecord> toSave = List.of(test);

        //when
        when(repository.save(test)).thenReturn(new DailyRecord());

        //verify
        Exception e = assertThrows(DataSaveException.class, () -> service.save(toSave));
        assertEquals("Error when saving dailyRecordList (hashcode " + toSave.hashCode() + ")", e.getMessage());
    }

    @Test
    void getAll() {
        //given
        DailyRecord test = new DailyRecord();
        test.setHealthWard("test");
        test.setDateReported(testDate);
        List<DailyRecord> toGet = List.of(test);

        //when
        when(repository.findAll()).thenReturn(toGet);
        List<DailyRecord> returned = service.getAll();

        //verify
        assertAll("getAll",
                () -> assertEquals(1, returned.size()),
                () -> assertEquals("test", returned.get(0).getHealthWard()),
                () -> assertEquals(testDate, returned.get(0).getDateReported())
        );
    }

    @Test
    void getAll_Exception() {
        //when
        when(repository.findAll()).thenReturn(List.of());

        //verify
        Exception e = assertThrows(DataNotFoundException.class, () -> service.getAll());
        assertEquals("No Daily Records found.", e.getMessage());
    }



    @Test
    void getDailyRecordsByGeoCode() {
        //given
        DailyRecord test = new DailyRecord();
        test.setGeoCode("01");
        test.setHealthWard("test");
        test.setDateReported(testDate);
        List<DailyRecord> toGet = List.of(test);

        //when
        when(repository.findByGeoCode("01")).thenReturn(toGet);
        List<DailyRecord> returned = service.getDailyRecordsByGeoCode("01");

        //verify
        assertAll("getByHealthWard",
                () -> assertEquals(1, returned.size()),
                () -> assertEquals("01", returned.get(0).getGeoCode()),
                () -> assertEquals("test", returned.get(0).getHealthWard()),
                () -> assertEquals(testDate, returned.get(0).getDateReported())
        );
    }

    @Test
    void getDailyRecordByGeoCode_Exception() {
            //when
            when(repository.findByGeoCode("test")).thenReturn(List.of());

            //verify
            Exception e = assertThrows(DataNotFoundException.class, () -> service.getDailyRecordsByGeoCode("test"));
            assertEquals("No data found for geocode: test" , e.getMessage());
    }

    @Test
    void getDailyRecordByGeoCodeAndDate() {
        //given
        DailyRecord test = new DailyRecord();
        test.setGeoCode("01");
        test.setHealthWard("test");
        test.setDateReported(testDate);

        //when
        when(repository.findByGeoCodeAndDateReported("01", testDate)).thenReturn(Optional.of(test));
        DailyRecord returned = service.getDailyRecordByGeoCodeAndDate("01", testDate);

        //verify
        assertAll("getByGeoCodeAndDate",
                () -> assertEquals("01", returned.getGeoCode()),
                () -> assertEquals("test", returned.getHealthWard()),
                () -> assertEquals(testDate, returned.getDateReported())
        );
    }

    @Test
    void getDailyRecordByGeoCodeAndDate_Exception() {
        //when
        when(repository.findByGeoCodeAndDateReported("01", testDate)).thenReturn(Optional.empty());

        //verify
        Exception e = assertThrows(DataNotFoundException.class, () -> service.getDailyRecordByGeoCodeAndDate("01", testDate));
        assertEquals("No data found for geocode: 01 and dateReported: " + testDate.toString(), e.getMessage());
    }
}