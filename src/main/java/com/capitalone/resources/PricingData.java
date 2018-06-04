package com.capitalone.resources;

import com.capitalone.client.QuandlClient;
import com.capitalone.core.DailyStockData;
import com.codahale.metrics.annotation.Timed;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import java.util.List;


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
        return quandlClient.getStockData("GOOGL").constructDailyStockData();
    }
}
