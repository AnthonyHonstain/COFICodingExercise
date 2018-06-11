package com.capitalone.core;

import org.joda.time.DateTime;

import java.math.BigDecimal;
import java.util.Objects;


public class DailyStockData {

    private DateTime date;
    private BigDecimal adjOpen;
    private BigDecimal adjClose;
    private BigDecimal adjHigh;
    private BigDecimal adjLow;

    public DailyStockData(DateTime date,
                          BigDecimal adjOpen,
                          BigDecimal adjClose,
                          BigDecimal adjHigh,
                          BigDecimal adjLow) {
        this.date = date;
        this.adjOpen = adjOpen;
        this.adjClose = adjClose;
        this.adjHigh = adjHigh;
        this.adjLow = adjLow;
    }

    public DailyStockData(DateTime date,
                          BigDecimal adjOpen,
                          BigDecimal adjClose) {
        this.date = date;
        this.adjOpen = adjOpen;
        this.adjClose = adjClose;
        this.adjHigh = null;
        this.adjLow = null;
    }

    public DailyStockData(String date,
                          String adjOpen,
                          String adjClose,
                          String adjHigh,
                          String adjLow) {
        this.date = new DateTime(date);
        this.adjOpen = new BigDecimal(adjOpen);
        this.adjClose = new BigDecimal(adjClose);
        this.adjHigh = new BigDecimal(adjHigh);
        this.adjLow = new BigDecimal(adjLow);
    }

    public DailyStockData(String date,
                          String adjOpen,
                          String adjClose) {
        this.date = new DateTime(date);
        this.adjOpen = new BigDecimal(adjOpen);
        this.adjClose = new BigDecimal(adjClose);
        this.adjHigh = null;
        this.adjLow = null;
    }

    public DateTime getDate() {
        return date;
    }

    public BigDecimal getAdjOpen() {
        return adjOpen;
    }

    public BigDecimal getAdjClose() {
        return adjClose;
    }

    public BigDecimal getAdjHigh() {
        return adjHigh;
    }

    public BigDecimal getAdjLow() {
        return adjLow;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        DailyStockData that = (DailyStockData) o;
        return Objects.equals(date, that.date) &&
                Objects.equals(adjOpen, that.adjOpen) &&
                Objects.equals(adjClose, that.adjClose) &&
                Objects.equals(adjHigh, that.adjHigh) &&
                Objects.equals(adjLow, that.adjLow);
    }

    @Override
    public int hashCode() {
        return Objects.hash(date, adjOpen, adjClose, adjHigh, adjLow);
    }
}