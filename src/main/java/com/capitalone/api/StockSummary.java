package com.capitalone.api;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.math.BigDecimal;
import java.util.Objects;


public class StockSummary {

    private final String month;
    private final BigDecimal averageOpen;
    private final BigDecimal averageClose;

    public StockSummary(String month, BigDecimal averageOpen, BigDecimal averageClose) {
        this.month = month;
        this.averageOpen = averageOpen;
        this.averageClose = averageClose;
    }

    @JsonProperty("month")
    public String getMonth() {
        return month;
    }

    @JsonProperty("average_open")
    public BigDecimal getAverageOpen() {
        return averageOpen;
    }

    @JsonProperty("average_close")
    public BigDecimal getAverageClose() {
        return averageClose;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        StockSummary that = (StockSummary) o;
        return Objects.equals(month, that.month) &&
                Objects.equals(averageOpen, that.averageOpen) &&
                Objects.equals(averageClose, that.averageClose);
    }

    @Override
    public int hashCode() {
        return Objects.hash(month, averageOpen, averageClose);
    }
}
