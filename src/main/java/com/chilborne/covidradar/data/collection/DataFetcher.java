package com.chilborne.covidradar.data.collection;

public interface DataFetcher {

    void fetchData(DataFetchAction type) ;

    void publishData(String data);
}
