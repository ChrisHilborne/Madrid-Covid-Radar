package com.chilborne.covidradar.controllers;

import com.chilborne.covidradar.services.CacheService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin
@RestController
@RequestMapping("/")
public class AppController {

    private final Logger logger = LoggerFactory.getLogger(AppController.class);
    
    private final CacheService cacheService;

    public AppController(CacheService cacheService) {
        this.cacheService = cacheService;
    }

    @GetMapping(value = "healthcheck")
    public ResponseEntity<String> healthCheck() {
        logger.debug("Health check request...");
        return ResponseEntity.ok().build();
    }

    @GetMapping(value = "clear-cache")
    public ResponseEntity<String> clearCache() {
        logger.debug("Clear cache request made");
        cacheService.clearCache();
        return ResponseEntity.ok().build();
    }

}
