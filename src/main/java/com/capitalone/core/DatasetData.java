package com.capitalone.core;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public class DatasetData {

    private List<String> columnNames;
    private List<List<String>> data;

    @JsonProperty("column_names")
    public List<String> getColumnNames() {
        return columnNames;
    }

    @JsonProperty("data")
    public List<List<String>> getData() {
        return data;
    }
}
