package com.chilborne.covidradar.data.collection;

import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public interface DataFetcher<D> {

    HttpResponse<D> fetchData(HttpRequest httpRequest) ;

}

