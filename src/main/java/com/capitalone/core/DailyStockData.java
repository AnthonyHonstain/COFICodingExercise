package com.capitalone.core;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.joda.time.DateTime;

import java.math.BigDecimal;
import java.util.Objects;

public class DailyStockData {

    private DateTime date;
    private BigDecimal adjOpen;
    private BigDecimal adjClose;

    public DailyStockData(DateTime date, BigDecimal adjOpen, BigDecimal adjClose) {
        this.date = date;
        this.adjOpen = adjOpen;
        this.adjClose = adjClose;
    }

    @JsonProperty("date")
    public DateTime getDate() {
        return date;
    }

    @JsonProperty("open")
    public BigDecimal getAdjOpen() {
        return adjOpen;
    }

    @JsonProperty("close")
    public BigDecimal getAdjClose() {
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