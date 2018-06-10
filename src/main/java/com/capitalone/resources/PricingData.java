package com.capitalone.resources;

import com.capitalone.api.StockSummary;
import com.capitalone.client.QuandlClient;
import com.capitalone.core.DailyStockData;
import com.capitalone.core.DailyStockProcessor;
import com.codahale.metrics.annotation.Timed;
import com.google.common.collect.ImmutableMap;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import java.util.List;
import java.util.Map;


@Path("/")
@Produces(MediaType.APPLICATION_JSON)
public class PricingData {

    final private QuandlClient quandlClient;

    public PricingData(QuandlClient quandlClient) {
        this.quandlClient = quandlClient;
    }

    @GET
    @Timed
    public String index() {
        return "STUB";
    }

    @GET
    @Timed
    @Path("/prototype")
    public Map<String, List<StockSummary>> prototype() {
        final String ticker = "GOOGL";
        final List<DailyStockData> stockData = quandlClient.getStockData(ticker).constructDailyStockData();

        final List<StockSummary> result = DailyStockProcessor.computeMonthlyAverages(stockData);

        return ImmutableMap.of(ticker, result);
    }
}
