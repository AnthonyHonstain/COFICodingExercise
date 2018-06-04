package com.capitalone.client;

import com.capitalone.core.QuandlExternal.QuandlResponse;

import javax.ws.rs.client.Client;


public class QuandlClient {

    final private String QUANDL_API_URL = "https://www.quandl.com/api/v3/datasets/WIKI";
    final private Client jerseyClient;
    final private String quandlAPIKey;

    public QuandlClient(final Client jerseyClient, final String quandlAPIKey) {
        this.jerseyClient = jerseyClient;
        this.quandlAPIKey = quandlAPIKey;
    }

    public QuandlResponse getStockData(String ticker) {
        QuandlResponse response = this.jerseyClient.target(QUANDL_API_URL)
                .path(ticker).path("data.json")
                .queryParam("api_key", quandlAPIKey)
                .queryParam("start_date", "2017-01-01")
                .queryParam("end_date", "2017-06-30")
                .request()
                .get(QuandlResponse.class);
        return response;
    }
}
