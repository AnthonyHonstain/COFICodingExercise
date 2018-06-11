package com.capitalone.api;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.math.BigDecimal;
import java.util.Objects;


public class MaxDailyProfit {

    private final String date;
    private final BigDecimal estimatedProfit;

    public MaxDailyProfit(String date, BigDecimal estimatedProfit) {
        this.date = date;
        this.estimatedProfit = estimatedProfit;
    }

    @JsonProperty("date")
    public String getDate() {
        return date;
    }

    @JsonProperty("estimatedProfit")
    public BigDecimal getEstimatedProfit() {
        return estimatedProfit;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        MaxDailyProfit that = (MaxDailyProfit) o;
        return Objects.equals(date, that.date) &&
                Objects.equals(estimatedProfit, that.estimatedProfit);
    }

    @Override
    public int hashCode() {
        return Objects.hash(date, estimatedProfit);
    }
}
