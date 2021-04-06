package com.chilborne.covidradar.services;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cache.CacheManager;

public class CacheServiceImpl implements CacheService {

    private final CacheManager cacheManager;
    private final Logger logger = LoggerFactory.getLogger(CacheServiceImpl.class);

    public CacheServiceImpl(CacheManager cacheManager) {
        this.cacheManager = cacheManager;
    }


    @Override
    public void clearCache() {
        logger.debug("*** Clearing Cache ***");
        for(String name:cacheManager.getCacheNames()){
            cacheManager.getCache(name).clear();
        }
    }
}
