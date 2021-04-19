package com.chilborne.covidradar.data.collection;

/**
 * Data is fetched from two uris and is given in two different forms - thus we need to define where the data is being fetched from
 */
public enum DataFetchAction {
    INIT,
    UPDATE
}
