package com.capitalone;

import io.dropwizard.Configuration;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.dropwizard.client.JerseyClientConfiguration;

import javax.validation.Valid;
import javax.validation.constraints.*;

public class COFICodingConfiguration extends Configuration {
    @Valid
    @NotNull
    private JerseyClientConfiguration jerseyClient = new JerseyClientConfiguration();

    @JsonProperty("jerseyClient")
    public JerseyClientConfiguration getJerseyClientConfiguration() {
        return jerseyClient;
    }

    @JsonProperty("jerseyClient")
    public void setJerseyClientConfiguration(JerseyClientConfiguration jerseyClient) {
        this.jerseyClient = jerseyClient;
    }

    @Valid
    @NotNull
    private String quandlAPIKey;

    @JsonProperty("quandlAPIKey")
    public String getQuandlAPIKey() {
        return quandlAPIKey;
    }

    @JsonProperty("quandlAPIKey")
    public void setQuandlAPIKey(String quandlAPIKey) {
        this.quandlAPIKey = quandlAPIKey;
    }
}
