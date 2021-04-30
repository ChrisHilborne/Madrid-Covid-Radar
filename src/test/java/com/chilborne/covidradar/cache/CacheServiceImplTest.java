package com.chilborne.covidradar.cache;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;

import java.util.List;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CacheServiceImplTest {

    @Mock
    CacheManager cacheManager;

    @InjectMocks
    CacheServiceImpl cacheService;

    @Test
    void clearCache() {
        //given
        List<String> cacheNames = List.of("one", "two", "three");
        Cache mockCache = mock(Cache.class);

        //when
        when(cacheManager.getCacheNames()).thenReturn(cacheNames);
        when(cacheManager.getCache(anyString())).thenReturn(mockCache);

        cacheService.clearCache();

        //assert
        verify(mockCache, times(3)).clear();

    }
}