package com.chilborne.covidradar.services.dailyrecords.data.parsing;

import java.util.List;

public interface JsonParser<T> {

    List<T> parse(String json);

}
