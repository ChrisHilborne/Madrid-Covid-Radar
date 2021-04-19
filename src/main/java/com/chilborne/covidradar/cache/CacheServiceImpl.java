package com.chilborne.covidradar.cache;

import com.chilborne.covidradar.events.UpdatedDataEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cache.CacheManager;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;

@Service
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

    @EventListener
    public void clearCache(UpdatedDataEvent updatedDataEvent) {
        logger.debug("*** Clearing Cache ***");
        for(String name:cacheManager.getCacheNames()){
            cacheManager.getCache(name).clear();
        }
    }
}
