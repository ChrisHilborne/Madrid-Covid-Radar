package com.chilborne.covidradar.model;


import java.time.LocalDate;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class DistrictDataDTO {

    private String geoCode;
    private String municipalDistrict;
    private int totalConfirmedCases;
    private LocalDate lastReported;
    private List<DailyRecordDTO> dailyReports;

    public DistrictDataDTO(DistrictData districtData) {
        if (districtData == null) {
            throw new RuntimeException("No Data Provided");
            //TODO exception
        }
        this.geoCode = districtData.getGeoCode();
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

    public String getGeoCode() {
        return geoCode;
    }

    public void setGeoCode(String geoCode) {
        this.geoCode = geoCode;
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
                "geoCode='" + geoCode + '\'' +
                "municipalDistrict='" + municipalDistrict + '\'' +
                ", totalConfirmedCases=" + totalConfirmedCases +
                ", lastReported=" + lastReported +
                ", dailyReports=" + dailyReports +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        DistrictDataDTO that = (DistrictDataDTO) o;

        if (totalConfirmedCases != that.totalConfirmedCases) return false;
        if (!Objects.equals(municipalDistrict, that.municipalDistrict))
            return false;
        if (!Objects.equals(lastReported, that.lastReported)) return false;
        return Objects.equals(dailyReports, that.dailyReports);
    }

    @Override
    public int hashCode() {
        int result = municipalDistrict != null ? municipalDistrict.hashCode() : 0;
        result = 31 * result + totalConfirmedCases;
        result = 31 * result + (lastReported != null ? lastReported.hashCode() : 0);
        result = 31 * result + (dailyReports != null ? dailyReports.hashCode() : 0);
        return result;
    }
}
