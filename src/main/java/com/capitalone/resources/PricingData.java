package com.capitalone.resources;

import com.capitalone.api.MaxDailyProfit;
import com.capitalone.api.StockSummary;
import com.capitalone.client.QuandlClient;
import com.capitalone.core.DailyStockData;
import com.capitalone.core.DailyStockProcessor;
import com.codahale.metrics.annotation.Timed;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Path("/")
@Produces(MediaType.APPLICATION_JSON)
public class PricingData {

    final private List<String> tickerList = ImmutableList.of("COF", "GOOGL", "MSFT");
    final private QuandlClient quandlClient;

    public PricingData(QuandlClient quandlClient) {
        this.quandlClient = quandlClient;
    }

    @GET
    @Timed
    public Map<String, List<String>> index() {
        return ImmutableMap.of("Tickers", tickerList);
    }

    @GET
    @Timed
    @Path("/averageMonthlyPrice")
    public Map<String, List<StockSummary>> averageMonthlyPrice() {
        final HashMap<String, List<StockSummary>> resultMap = new HashMap<>();

        for (String ticker: tickerList) {
            final List<DailyStockData> stockData = quandlClient.getStockData(ticker).constructDailyStockData();

            final List<StockSummary> result = DailyStockProcessor.computeMonthlyAverages(stockData);

            resultMap.put(ticker, result);
        }
        return resultMap;
    }

    @GET
    @Timed
    @Path("/maxDailyProfit")
    public Map<String, MaxDailyProfit> maxDailyProfit() {
        final HashMap<String, MaxDailyProfit> resultMap = new HashMap<>();

        for (String ticker: tickerList) {
            final List<DailyStockData> stockData = quandlClient.getStockData(ticker).constructDailyStockData();

            final MaxDailyProfit maxDailyProfit = DailyStockProcessor.computeMaxSingleDayProfit(stockData);

            resultMap.put(ticker, maxDailyProfit);
        }
        return resultMap;
    }
}
