package com.capitalone.core;

import com.capitalone.api.MaxDailyProfit;
import com.capitalone.api.StockSummary;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;


public class DailyStockProcessor {

    private static final int SCALE = 2;

    public static List<StockSummary> computeMonthlyAverages(final List<DailyStockData> stockData) {

        final List<StockSummary> result = new ArrayList<>();

        if (stockData.size() == 0) {
            return result;
        }

        DailyStockData prevData = null;
        BigDecimal openSum = BigDecimal.ZERO;
        BigDecimal closeSum = BigDecimal.ZERO;
        int count = 0;

        for (DailyStockData dailyStockData: stockData) {
            if (count > 0 && dailyStockData.getDate().getMonthOfYear() != prevData.getDate().getMonthOfYear()) {
                result.add(new StockSummary(
                        prevData.getDate().toString("yyyy-MM"),
                        openSum.divide(new BigDecimal(count), SCALE, BigDecimal.ROUND_DOWN),
                        closeSum.divide(new BigDecimal(count), SCALE, BigDecimal.ROUND_DOWN))
                );
                openSum = BigDecimal.ZERO;
                closeSum = BigDecimal.ZERO;
                count = 0;
            }

            prevData = dailyStockData;
            openSum = openSum.add(dailyStockData.getAdjOpen());
            closeSum = closeSum.add(dailyStockData.getAdjClose());
            count += 1;
        }

        result.add(new StockSummary(
                prevData.getDate().toString("yyyy-MM"),
                openSum.divide(new BigDecimal(count), SCALE, BigDecimal.ROUND_DOWN),
                closeSum.divide(new BigDecimal(count), SCALE, BigDecimal.ROUND_DOWN))
        );

        return result;
    }

    public static MaxDailyProfit computeMaxSingleDayProfit(final List<DailyStockData> stockData) {

        if (stockData.size() == 0) {
            return null;
        }

        MaxDailyProfit currentMax = null;

        for (DailyStockData dailyStockData: stockData) {
            BigDecimal currentDaysMax = dailyStockData.getAdjHigh().subtract(dailyStockData.getAdjLow())
                    .setScale(SCALE,BigDecimal.ROUND_DOWN);
            if (currentMax == null || currentMax.getEstimatedProfit().compareTo(currentDaysMax) < 0) {
                currentMax = new MaxDailyProfit(dailyStockData.getDate().toString("yyyy-MM-dd"), currentDaysMax);
            }
        }
        return currentMax;
    }
}
