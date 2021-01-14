package com.chilborne.covidradar.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDate;
import java.util.List;
import java.util.Objects;

@Document(collection = "districts")
public class DistrictData {

    @Id
    @Indexed(unique = true)
    private String geoCode;
    private String name;
    private int totalCases;
    private LocalDate lastUpdated;
    private List<DailyRecord> dailyRecords;

    public DistrictData(List<DailyRecord> dailyRecords) {
        DailyRecord lastRecord = dailyRecords.get(dailyRecords.size() - 1);
        this.geoCode = lastRecord.getGeoCode();
        this.name = lastRecord.getMunicipalDistrict();
        this.totalCases = lastRecord.getTotalCases();
        this.lastUpdated = lastRecord.getDateReported();
        this.dailyRecords = dailyRecords;
    }

    public DistrictData() {
    }

    public String getName() { return name; }

    public String getGeoCode() {
        return geoCode;
    }

    public void setGeoCode(String geoCode) {
        this.geoCode = geoCode;
    }

    public List<DailyRecord> getDailyRecords() { return dailyRecords; }

    public void setDailyRecords(List<DailyRecord> dailyRecords) { this.dailyRecords = dailyRecords; }

    public void setName(String name) {
        this.name = name;
    }

    public int getTotalCases() {
        return totalCases;
    }

    public void setTotalCases(int totalCases) {
        this.totalCases = totalCases;
    }

    public LocalDate getLastUpdated() {
        return lastUpdated;
    }

    public void setLastUpdated(LocalDate lastUpdated) {
        this.lastUpdated = lastUpdated;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        DistrictData that = (DistrictData) o;

        if (totalCases != that.totalCases) return false;
        if (!Objects.equals(geoCode, that.geoCode)) return false;
        if (!Objects.equals(name, that.name)) return false;
        if (!Objects.equals(lastUpdated, that.lastUpdated)) return false;
        return Objects.equals(dailyRecords, that.dailyRecords);
    }

    @Override
    public int hashCode() {
        int result = geoCode != null ? geoCode.hashCode() : 0;
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + totalCases;
        result = 31 * result + (lastUpdated != null ? lastUpdated.hashCode() : 0);
        result = 31 * result + (dailyRecords != null ? dailyRecords.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "DistrictData{" +
                "geoCode='" + geoCode + '\'' +
                ", name='" + name + '\'' +
                ", totalCases=" + totalCases +
                ", lastUpdated=" + lastUpdated +
                ", dailyRecords=" + dailyRecords +
                '}';
    }
}
