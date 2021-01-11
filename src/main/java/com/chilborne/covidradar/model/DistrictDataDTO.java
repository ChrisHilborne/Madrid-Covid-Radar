package com.chilborne.covidradar.model;


import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

public class DistrictDataDTO {

    private String municipalDistrict;
    private int totalConfirmedCases;
    private LocalDate lastReported;
    private List<DailyRecordDTO> dailyReports;

    public DistrictDataDTO(DistrictData districtData) {
        if (districtData == null) {
            throw new RuntimeException("No Data Provided");
            //TODO exception
        }
        this.municipalDistrict = districtData.getName();
        this.totalConfirmedCases = districtData.getTotalCases();
        this.lastReported = districtData.getLastUpdated();
        this.dailyReports = districtData.getDailyRecords().
                stream()
                .map(DailyRecordDTO::new)
                .collect(Collectors.toList());
    }

    public DistrictDataDTO() {
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        DistrictDataDTO that = (DistrictDataDTO) o;

        if (totalConfirmedCases != that.totalConfirmedCases) return false;
        if (municipalDistrict != null ? !municipalDistrict.equals(that.municipalDistrict) : that.municipalDistrict != null)
            return false;
        if (lastReported != null ? !lastReported.equals(that.lastReported) : that.lastReported != null) return false;
        return dailyReports != null ? dailyReports.equals(that.dailyReports) : that.dailyReports == null;
    }

    @Override
    public int hashCode() {
        int result = municipalDistrict != null ? municipalDistrict.hashCode() : 0;
        result = 31 * result + totalConfirmedCases;
        result = 31 * result + (lastReported != null ? lastReported.hashCode() : 0);
        result = 31 * result + (dailyReports != null ? dailyReports.hashCode() : 0);
        return result;
    }

    public String getMunicipalDistrict() {
        return municipalDistrict;
    }

    public void setMunicipalDistrict(String municipalDistrict) {
        this.municipalDistrict = municipalDistrict;
    }

    public int getTotalConfirmedCases() {
        return totalConfirmedCases;
    }

    public void setTotalConfirmedCases(int totalConfirmedCases) {
        this.totalConfirmedCases = totalConfirmedCases;
    }

    public LocalDate getLastReported() {
        return lastReported;
    }

    public void setLastReported(LocalDate lastReported) {
        this.lastReported = lastReported;
    }

    public List<DailyRecordDTO> getDailyReports() {
        return dailyReports;
    }

    public void setDailyReports(List<DailyRecordDTO> dailyReports) {
        this.dailyReports = dailyReports;
    }

    @Override
    public String toString() {
        return "DistrictDataDTO{" +
                "municipalDistrict='" + municipalDistrict + '\'' +
                ", totalConfirmedCases=" + totalConfirmedCases +
                ", lastReported=" + lastReported +
                ", dailyReports=" + dailyReports +
                '}';
    }
}
