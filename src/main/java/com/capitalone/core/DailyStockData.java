package com.capitalone.core;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Objects;

public class DailyStockData {

    private String date;
    private String adjOpen;
    private String adjClose;

    public DailyStockData(String date, String adjOpen, String adjClose) {
        this.date = date;
        this.adjOpen = adjOpen;
        this.adjClose = adjClose;
    }

    @JsonProperty("date")
    public String getDate() {
        return date;
    }

    @JsonProperty("open")
    public String getAdjOpen() {
        return adjOpen;
    }

    @JsonProperty("close")
    public String getAdjClose() {
        return adjClose;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        DailyStockData that = (DailyStockData) o;
        return Objects.equals(date, that.date) &&
                Objects.equals(adjOpen, that.adjOpen) &&
                Objects.equals(adjClose, that.adjClose);
    }

    @Override
    public int hashCode() {

        return Objects.hash(date, adjOpen, adjClose);
    }
}