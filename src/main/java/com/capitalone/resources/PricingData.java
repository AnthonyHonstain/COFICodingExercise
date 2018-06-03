package com.capitalone.resources;

import com.capitalone.core.QuandlResponse;
import com.codahale.metrics.annotation.Timed;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.client.Client;
import javax.ws.rs.core.MediaType;

@Path("/")
@Produces(MediaType.APPLICATION_JSON)
public class PricingData {

    final private Client quandlClient;
    final private String quandlAPIKey;

    public PricingData(Client quandlClient, String quandlAPIKey) {
        this.quandlClient = quandlClient;
        this.quandlAPIKey = quandlAPIKey;
    }

    @GET
    @Timed
    public String index() {
        return "STUB";
    }

    @GET
    @Timed
    @Path("/initialAttempt")
    public String firstAttempt() {
        String result = this.quandlClient.target("https://www.quandl.com/api/v3/datasets/WIKI/GOOGL/data.json")
                .queryParam("api_key", quandlAPIKey)
                .queryParam("start_date","2017-01-01")
                .queryParam("end_date","2017-06-30")
                .request()
                .get(String.class);
        return result;
    }

    @GET
    @Timed
    @Path("/prototype")
    public QuandlResponse prototype() {
        // TODO - pull url to configs
        // TODO - consider improving client slightly
        QuandlResponse result = this.quandlClient.target("https://www.quandl.com/api/v3/datasets/WIKI/GOOGL/data.json")
                .queryParam("api_key",quandlAPIKey)
                .queryParam("start_date","2017-01-01")
                .queryParam("end_date","2017-06-30")
                .request()
                .get(QuandlResponse.class);
        return result;
    }
}
