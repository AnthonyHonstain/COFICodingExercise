package com.capitalone.core;

import com.capitalone.api.StockSummary;
import com.google.common.collect.ImmutableList;
import org.junit.Test;

import java.math.BigDecimal;
import java.util.List;

import static org.junit.Assert.assertEquals;


public class DailyStockProcessorTest {

    @Test
    public void computeMonthlyAveragesEmpty() {
        final List<DailyStockData> dailyStockData = ImmutableList.of();

        List<StockSummary> response = DailyStockProcessor.computeMonthlyAverages(dailyStockData);

        assertEquals(0, response.size());
    }

    @Test
    public void computeMonthlyAveragesSingleMonth() {
        final List<DailyStockData> dailyStockData = ImmutableList.of(
                new DailyStockData("2017-06-30", "943.99", "929.68"),
                new DailyStockData("2017-06-29", "951.35", "937.82"),
                new DailyStockData("2017-06-28", "950.66", "961.01")
        );

        List<StockSummary> response = DailyStockProcessor.computeMonthlyAverages(dailyStockData);

        assertEquals(1, response.size());

        assertEquals("2017-06", response.get(0).getMonth());
        assertEquals(new BigDecimal("948.66"), response.get(0).getAverageOpen());
        assertEquals(new BigDecimal("942.83"), response.get(0).getAverageClose());
    }

    @Test
    public void computeMonthlyAveragesUnexpectedPercision() {
        final List<DailyStockData> dailyStockData = ImmutableList.of(
                new DailyStockData("2017-06-30", "81.633978115072", "82.231154253442"),
                new DailyStockData("2017-06-29", "81.365248852806", "81.086566654901"),
                new DailyStockData("2017-06-28", "81.733507471467", "82.589459936463")
        );

        List<StockSummary> response = DailyStockProcessor.computeMonthlyAverages(dailyStockData);

        assertEquals(1, response.size());

        assertEquals("2017-06", response.get(0).getMonth());
        assertEquals(new BigDecimal("81.57"), response.get(0).getAverageOpen());
        assertEquals(new BigDecimal("81.96"), response.get(0).getAverageClose());
    }

    @Test
    public void computeMonthlyAveragesSingleDayInFirstMonthFromList() {
        final List<DailyStockData> dailyStockData = ImmutableList.of(
                new DailyStockData("2017-07-01", "941.33", "959.31"),
                new DailyStockData("2017-06-30", "943.99", "929.68"),
                new DailyStockData("2017-06-29", "951.35", "937.82"),
                new DailyStockData("2017-06-28", "950.66", "961.01")
        );

        List<StockSummary> response = DailyStockProcessor.computeMonthlyAverages(dailyStockData);

        assertEquals(2, response.size());

        assertEquals("2017-07", response.get(0).getMonth());
        assertEquals(new BigDecimal("941.33"), response.get(0).getAverageOpen());
        assertEquals(new BigDecimal("959.31"), response.get(0).getAverageClose());

        assertEquals("2017-06", response.get(1).getMonth());
        assertEquals(new BigDecimal("948.66"), response.get(1).getAverageOpen());
        assertEquals(new BigDecimal("942.83"), response.get(1).getAverageClose());
    }

    @Test
    public void computeMonthlyAveragesSingleDayInLastMonthFromList() {
        final List<DailyStockData> dailyStockData = ImmutableList.of(
                new DailyStockData("2017-06-30", "943.99", "929.68"),
                new DailyStockData("2017-06-29", "951.35", "937.82"),
                new DailyStockData("2017-06-28", "950.66", "961.01"),
                new DailyStockData("2017-05-31", "941.33", "959.31")
        );

        List<StockSummary> response = DailyStockProcessor.computeMonthlyAverages(dailyStockData);

        assertEquals(2, response.size());

        assertEquals("2017-06", response.get(0).getMonth());
        assertEquals(new BigDecimal("948.66"), response.get(0).getAverageOpen());
        assertEquals(new BigDecimal("942.83"), response.get(0).getAverageClose());

        assertEquals("2017-05", response.get(1).getMonth());
        assertEquals(new BigDecimal("941.33"), response.get(1).getAverageOpen());
        assertEquals(new BigDecimal("959.31"), response.get(1).getAverageClose());
    }

    @Test
    public void computeMonthlyAveragesForThreeMonths() {
        final List<DailyStockData> dailyStockData = ImmutableList.of(
                new DailyStockData("2017-07-01", "941.33", "959.31"),
                new DailyStockData("2017-06-30", "943.99", "929.68"),
                new DailyStockData("2017-06-29", "951.35", "937.82"),
                new DailyStockData("2017-06-28", "950.66", "961.01"),
                new DailyStockData("2017-05-31", "640.33", "950.22"),
                new DailyStockData("2017-05-30", "640.22", "950.11")
        );

        List<StockSummary> response = DailyStockProcessor.computeMonthlyAverages(dailyStockData);

        assertEquals(3, response.size());

        assertEquals("2017-07", response.get(0).getMonth());
        assertEquals(new BigDecimal("941.33"), response.get(0).getAverageOpen());
        assertEquals(new BigDecimal("959.31"), response.get(0).getAverageClose());

        assertEquals("2017-06", response.get(1).getMonth());
        assertEquals(new BigDecimal("948.66"), response.get(1).getAverageOpen());
        assertEquals(new BigDecimal("942.83"), response.get(1).getAverageClose());

        assertEquals("2017-05", response.get(2).getMonth());
        assertEquals(new BigDecimal("640.27"), response.get(2).getAverageOpen());
        assertEquals(new BigDecimal("950.16"), response.get(2).getAverageClose());
    }
}
