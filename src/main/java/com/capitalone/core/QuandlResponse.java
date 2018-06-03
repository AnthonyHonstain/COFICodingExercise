package com.capitalone.core;

import com.fasterxml.jackson.annotation.JsonProperty;

public class QuandlResponse {

    private DatasetData datasetData;

    @JsonProperty("dataset_data")
    public DatasetData getDatasetData() {
        return datasetData;
    }
}
