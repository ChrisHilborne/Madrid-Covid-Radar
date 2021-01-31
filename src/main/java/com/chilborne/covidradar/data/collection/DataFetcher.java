package com.chilborne.covidradar.data.collection;

public interface DataFetcher {

    void fetchData(DataFetchType type) ;

    void publishData(String data);
}
