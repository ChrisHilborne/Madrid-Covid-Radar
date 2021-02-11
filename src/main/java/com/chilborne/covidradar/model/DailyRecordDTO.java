package com.chilborne.covidradar.model;

import javax.validation.constraints.Past;
import javax.validation.constraints.PositiveOrZero;
import java.time.LocalDate;
import java.util.Objects;

public class DailyRecordDTO {

    @PositiveOrZero
    private int twoWeekRate;
    @PositiveOrZero
    private int totalRate;
    @PositiveOrZero
    private int twoWeekCases;
    @PositiveOrZero
    private int totalCases;
    @Past
    private LocalDate date;

    public DailyRecordDTO(DailyRecord dailyRecord) {
        if (dailyRecord == null) {
            throw new RuntimeException("No Data Provided");
        }
        this.totalCases = dailyRecord.getTotalCases();
        this.totalRate = dailyRecord.getInfectionRateTotal();
        this.twoWeekCases = dailyRecord.getCasesLastTwoWeeks();
        this.twoWeekRate = dailyRecord.getInfectionRateLastTwoWeeks();
        this.date = dailyRecord.getDateReported();
    }
    public DailyRecordDTO() {
    }

    public double getTwoWeekRate() {
        return twoWeekRate;
    }

    public void setTwoWeekRate(int twoWeekRate) {
        this.twoWeekRate = twoWeekRate;
    }

    public double getTotalRate() {
        return totalRate;
    }

    public void setTotalRate(int totalRate) {
        this.totalRate = totalRate;
    }

    public int getTwoWeekCases() {
        return twoWeekCases;
    }

    public void setTwoWeekCases(int twoWeekCases) {
        this.twoWeekCases = twoWeekCases;
    }

    public int getTotalCases() {
        return totalCases;
    }

    public void setTotalCases(int totalCases) {
        this.totalCases = totalCases;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        DailyRecordDTO that = (DailyRecordDTO) o;

        if (Double.compare(that.twoWeekRate, twoWeekRate) != 0) return false;
        if (Double.compare(that.totalRate, totalRate) != 0) return false;
        if (twoWeekCases != that.twoWeekCases) return false;
        if (totalCases != that.totalCases) return false;
        return Objects.equals(date, that.date);
    }

    @Override
    public int hashCode() {
        int result;
        long temp;
        temp = Double.doubleToLongBits(twoWeekRate);
        result = (int) (temp ^ (temp >>> 32));
        temp = Double.doubleToLongBits(totalRate);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        result = 31 * result + twoWeekCases;
        result = 31 * result + totalCases;
        result = 31 * result + (date != null ? date.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "DailyFigureDTO{" +
                ", twoWeekRate=" + twoWeekRate +
                ", totalRate=" + totalRate +
                ", twoWeekCases=" + twoWeekCases +
                ", totalCases=" + totalCases +
                ", date=" + date +
                '}';
    }
}
