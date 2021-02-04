package com.chilborne.covidradar.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.IndexDirection;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Past;
import javax.validation.constraints.PositiveOrZero;
import java.time.LocalDate;

/**
 * @Author Chris Hilborne
 */

@JsonIgnoreProperties(ignoreUnknown = true)
@Document(collection = "daily_records")
public class DailyRecord {

    @Id
    @Indexed(name = "_id", unique = true, direction = IndexDirection.ASCENDING)
    private String id;
    @NotBlank
    @JsonProperty("codigo_geometria")
    @Field("geocode")
    @Indexed(name = "geocode")
    private String geoCode;
    @NotBlank
    @JsonProperty("zona_basica_salud")
    @Field("healthward")
    private String healthWard;
    @PositiveOrZero
    @JsonProperty("tasa_incidencia_acumulada_ultimos_14dias")
    @Field("infectionRate2Weeks")
    private double infectionRateLastTwoWeeks;
    @PositiveOrZero
    @JsonProperty("tasa_incidencia_acumulada_total")
    @Field("infectionRateTotal")
    private double infectionRateTotal;
    @PositiveOrZero
    @JsonProperty("casos_confirmados_totales")
    @Field("casesTotal")
    private int totalCases;
    @PositiveOrZero
    @JsonProperty("casos_confirmados_ultimos_14dias")
    @Field("cases2Weeks")
    private int casesLastTwoWeeks;
    @Past
    @JsonProperty("fecha_informe")
    @JsonFormat(pattern = "yyyy/MM/dd HH:mm:ss")
    @Field("date")
    private LocalDate dateReported;


    public DailyRecord() {}

    public void generateId() {
        this.id = healthWard + "::" + dateReported.toString();
    }

    public String getId() {
        return id;
    }

    public String getGeoCode() {
        return geoCode;
    }

    public void setGeoCode(String geoCode) {
        this.geoCode = geoCode;
    }

    public LocalDate getDateReported() {
        return dateReported;
    }

    public void setDateReported(LocalDate dateReported) {
        this.dateReported = dateReported;
    }

    public String getHealthWard() {
        return healthWard;
    }

    public void setHealthWard(String healthWard) {
        this.healthWard = healthWard;
    }

    public double getInfectionRateLastTwoWeeks() {
        return infectionRateLastTwoWeeks;
    }

    public void setInfectionRateLastTwoWeeks(double infectionRateLastTwoWeeks) {
        this.infectionRateLastTwoWeeks = infectionRateLastTwoWeeks;
    }

    public double getInfectionRateTotal() {
        return infectionRateTotal;
    }

    public void setInfectionRateTotal(double infectionRateTotal) {
        this.infectionRateTotal = infectionRateTotal;
    }

    public int getTotalCases() {
        return totalCases;
    }

    public void setTotalCases(int totalCases) {
        this.totalCases = totalCases;
    }

    public int getCasesLastTwoWeeks() {
        return casesLastTwoWeeks;
    }

    public void setCasesLastTwoWeeks(int casesLastTwoWeeks) {
        this.casesLastTwoWeeks = casesLastTwoWeeks;
    }

    public void setId(String id) {
        this.id = id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        DailyRecord that = (DailyRecord) o;

        if (Double.compare(that.infectionRateLastTwoWeeks, infectionRateLastTwoWeeks) != 0) return false;
        if (Double.compare(that.infectionRateTotal, infectionRateTotal) != 0) return false;
        if (totalCases != that.totalCases) return false;
        if (casesLastTwoWeeks != that.casesLastTwoWeeks) return false;
        if (geoCode != null ? !geoCode.equals(that.geoCode) : that.geoCode != null) return false;
        if (healthWard != null ? !healthWard.equals(that.healthWard) : that.healthWard != null)
            return false;
        return dateReported != null ? dateReported.equals(that.dateReported) : that.dateReported == null;
    }

    @Override
    public int hashCode() {
        int result;
        long temp;
        result = geoCode != null ? geoCode.hashCode() : 0;
        result = 31 * result + (healthWard != null ? healthWard.hashCode() : 0);
        temp = Double.doubleToLongBits(infectionRateLastTwoWeeks);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        temp = Double.doubleToLongBits(infectionRateTotal);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        result = 31 * result + totalCases;
        result = 31 * result + casesLastTwoWeeks;
        result = 31 * result + (dateReported != null ? dateReported.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "DailyRecord{" +
                "geoCode='" + geoCode + '\'' +
                ", municipalDistrict='" + healthWard + '\'' +
                ", infectionRateLastTwoWeeks=" + infectionRateLastTwoWeeks +
                ", infectionRateTotal=" + infectionRateTotal +
                ", totalCases=" + totalCases +
                ", casesLastTwoWeeks=" + casesLastTwoWeeks +
                ", dateReported=" + dateReported +
                '}';
    }


}
