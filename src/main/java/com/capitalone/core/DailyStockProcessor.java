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

        int prevMonth = -1;
        DailyStockData prevData = null;
        BigDecimal openSum = BigDecimal.ZERO;
        BigDecimal closeSum = BigDecimal.ZERO;
        int count = 0;

        for (DailyStockData dailyStockData: stockData) {
            // We have a different month, so we should start a new average
            if (dailyStockData.getDate().getMonthOfYear() != prevMonth) {

                if (count > 0) {
                    result.add(new StockSummary(
                            prevData.getDate().toString("yyyy-MM"),
                            openSum.divide(new BigDecimal(count), SCALE, BigDecimal.ROUND_DOWN),
                            closeSum.divide(new BigDecimal(count), SCALE, BigDecimal.ROUND_DOWN))
                    );

                }
                // Start the new average
                prevMonth = dailyStockData.getDate().getMonthOfYear();
                prevData = dailyStockData;
                openSum = dailyStockData.getAdjOpen();
                closeSum = dailyStockData.getAdjClose();
                count = 1;
            }
            else {
                // Increment the existing values since we are on the same month
                prevMonth = dailyStockData.getDate().getMonthOfYear();
                prevData = dailyStockData;
                openSum = openSum.add(dailyStockData.getAdjOpen());
                closeSum = closeSum.add(dailyStockData.getAdjClose());
                count += 1;
            }
        }
        // Finalize the last record
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
