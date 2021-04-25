package com.chilborne.covidradar.services;

import com.chilborne.covidradar.cache.CacheService;
import com.chilborne.covidradar.exceptions.DataNotFoundException;
import com.chilborne.covidradar.exceptions.DataSaveException;
import com.chilborne.covidradar.model.WeeklyRecord;
import com.chilborne.covidradar.repository.WeeklyRecordRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class WeeklyRecordServiceImplTest {

    @Mock
    WeeklyRecordRepository repository;

    @Mock
    CacheService cacheService;

    @InjectMocks
    WeeklyRecordServiceImpl service;

    @Captor
    ArgumentCaptor<WeeklyRecord> weeklyRecordArgumentCaptor;

    @Captor
    ArgumentCaptor<List<WeeklyRecord>> listArgumentCaptor;

    LocalDate testDate = LocalDate.now();

    @Test
    void save() {
        //given
        WeeklyRecord test = new WeeklyRecord();
        test.setHealthWard("test");
        test.setDateReported(testDate);

        //when
        when(repository.save(test)).thenReturn(test);
        WeeklyRecord saved = service.save(test);

        //verify
        verify(repository).save(weeklyRecordArgumentCaptor.capture());
        assertAll("save",
                () -> assertNotEquals(null, weeklyRecordArgumentCaptor.getValue().getId()),
                () -> assertEquals(test, weeklyRecordArgumentCaptor.getValue()),
                () -> assertEquals("test", saved.getHealthWard()),
                () -> assertEquals(testDate, saved.getDateReported())
        );

    }

    @Test
    void save_Exception() {
        //given
        WeeklyRecord test = new WeeklyRecord();
        test.setHealthWard("test");
        test.setDateReported(testDate);

        //when
        when(repository.save(test)).thenReturn(new WeeklyRecord());

        //verify
        Exception e = assertThrows(DataSaveException.class, () -> service.save(test));
        assertEquals("Failed to save WeeklyRecord id: " + test.getId(), e.getMessage());
    }


    @Test
    void saveList() {
        //given
        WeeklyRecord test = new WeeklyRecord();
        test.setHealthWard("test");
        test.setDateReported(testDate);
        List<WeeklyRecord> toSave = List.of(test);

        //when
        when(repository.save(test)).thenReturn(test);
        List<WeeklyRecord> returned = service.save(toSave);

        //verify
        verify(repository).save(weeklyRecordArgumentCaptor.capture());
        assertEquals(test, weeklyRecordArgumentCaptor.getValue());
        assertAll("saveList",
                () -> assertEquals(1, returned.size()),
                () -> assertEquals("test", returned.get(0).getHealthWard()),
                () -> assertEquals(testDate, returned.get(0).getDateReported())
        );
        verify(cacheService, times(1)).clearCache();
    }

    @Test
    void saveList_Exception() {
        //given
        WeeklyRecord test = new WeeklyRecord();
        test.setHealthWard("test");
        test.setDateReported(testDate);
        List<WeeklyRecord> toSave = List.of(test);

        //when
        when(repository.save(test)).thenReturn(new WeeklyRecord());

        //verify
        Exception e = assertThrows(DataSaveException.class, () -> service.save(toSave));
        assertEquals("Error when saving weeklyRecordList (hashcode " + toSave.hashCode() + ")", e.getMessage());
    }

    @Test
    void getAll() {
        //given
        WeeklyRecord test = new WeeklyRecord();
        test.setHealthWard("test");
        test.setDateReported(testDate);
        List<WeeklyRecord> toGet = List.of(test);

        //when
        when(repository.findAll()).thenReturn(toGet);
        List<WeeklyRecord> returned = service.getAll();

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
        when(dailyRecordRepository.findAll()).thenReturn(List.of());

        //verify
        Exception e = assertThrows(DataNotFoundException.class, () -> service.getAll());
        assertEquals("No Weekly Records found.", e.getMessage());
    }



    @Test
    void getWeeklyRecordsByGeoCode() {
        //given
        WeeklyRecord test = new WeeklyRecord();
        test.setGeoCode("01");
        test.setHealthWard("test");
        test.setDateReported(testDate);
        List<WeeklyRecord> toGet = List.of(test);

        //when
        when(repository.findByGeoCode("01")).thenReturn(toGet);
        List<WeeklyRecord> returned = service.getWeeklyRecordsByGeoCode("01");

        //verify
        assertAll("getByHealthWard",
                () -> assertEquals(1, returned.size()),
                () -> assertEquals("01", returned.get(0).getGeoCode()),
                () -> assertEquals("test", returned.get(0).getHealthWard()),
                () -> assertEquals(testDate, returned.get(0).getDateReported())
        );
    }

    @Test
    void getWeeklyRecordByGeoCode_Exception() {
            //when
            when(dailyRecordRepository.findByGeoCode("test")).thenReturn(List.of());

            //verify
            Exception e = assertThrows(DataNotFoundException.class, () -> service.getWeeklyRecordsByGeoCode("test"));
            assertEquals("No data found for geocode: test" , e.getMessage());
    }

    @Test
    void getWeeklyRecordByGeoCodeAndDate() {
        //given
        WeeklyRecord test = new WeeklyRecord();
        test.setGeoCode("01");
        test.setHealthWard("test");
        test.setDateReported(testDate);

        //when
        when(repository.findByGeoCodeAndDateReported("01", testDate)).thenReturn(Optional.of(test));
        WeeklyRecord returned = service.getWeeklyRecordByGeoCodeAndDate("01", testDate);

        //verify
        assertAll("getByGeoCodeAndDate",
                () -> assertEquals("01", returned.getGeoCode()),
                () -> assertEquals("test", returned.getHealthWard()),
                () -> assertEquals(testDate, returned.getDateReported())
        );
    }

    @Test
    void getWeeklyRecordByGeoCodeAndDate_Exception() {
        //when
        when(dailyRecordRepository.findByGeoCodeAndDateReported("01", testDate)).thenReturn(Optional.empty());

        //verify
        Exception e = assertThrows(DataNotFoundException.class, () -> service.getWeeklyRecordByGeoCodeAndDate("01", testDate));
        assertEquals("No data found for geocode: 01 and dateReported: " + testDate.toString(), e.getMessage());
    }

    @Test
    void getNamesAndGeoCodes() {
        //given
        LocalDate firstDateOfResults = LocalDate.of(2020, 3 ,3);

        DailyRecord one = new DailyRecord();
        one.setGeoCode("01");
        one.setHealthWard("b");
        DailyRecord two = new DailyRecord();
        two.setGeoCode("02");
        two.setHealthWard("a");
        DailyRecord three = new DailyRecord();
        three.setGeoCode("03");
        three.setHealthWard("c");
        List<DailyRecord> dailyRecordList = List.of(one, two, three);

        Map<String, String> expectedResults = new LinkedHashMap<>();
        expectedResults.put("b", "01");
        expectedResults.put("a", "02");
        expectedResults.put("c", "03");

        //when
        when(dailyRecordRepository.findByDateReported(firstDateOfResults)).thenReturn(dailyRecordList);
        Map<String, String> results = dailyRecordService.getNamesAndGeoCodes();

        //verify
        verify(dailyRecordRepository, times(1)).findByDateReported(firstDateOfResults);
        verify(dailyRecordRepository, times(1)).findByDateReported(any());
        assertEquals(expectedResults, results);
    }
}