package com.chilborne.covidradar.config;

import com.github.benmanes.caffeine.cache.Caffeine;
import org.junit.jupiter.api.Test;
import org.springframework.cache.CacheManager;
import org.springframework.cache.caffeine.CaffeineCacheManager;

import static org.junit.Assert.assertTrue;

class CacheConfigTest {

    CacheConfig config = new CacheConfig();

    @Test
    void cacheManager() {
        //given
        Caffeine caffeine = Caffeine.newBuilder();

        //when
        CacheManager result = config.cacheManager(caffeine);

        //assert
        assertTrue(result instanceof CaffeineCacheManager);
    }
}