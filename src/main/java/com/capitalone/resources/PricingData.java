package com.capitalone.resources;

import com.capitalone.client.QuandlClient;
import com.capitalone.core.DailyStockData;
import com.codahale.metrics.annotation.Timed;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import java.math.BigDecimal;
import java.util.List;
import java.util.function.Function;


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
    public List<DailyStockData> prototype() {
        final List<DailyStockData> stockData = quandlClient.getStockData("GOOGL").constructDailyStockData();

        calculateAverage(stockData, DailyStockData::getAdjClose);
        return stockData;
    }

    public BigDecimal calculateAverage(final List<DailyStockData> stockData,
                                       Function<DailyStockData, BigDecimal> mapFunction) {
        BigDecimal result = stockData.stream()
                .map(mapFunction)
                .reduce(BigDecimal::add).get();
        return result.divide(new BigDecimal(stockData.size()), BigDecimal.ROUND_DOWN);
    }
}
