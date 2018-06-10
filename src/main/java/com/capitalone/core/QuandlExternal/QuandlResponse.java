package com.capitalone.core.QuandlExternal;

import com.capitalone.core.DailyStockData;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.joda.time.DateTime;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;


public class QuandlResponse {

    private DatasetData datasetData;

    public QuandlResponse() {
        // Jackson deserialization
    }

    public QuandlResponse(DatasetData datasetData) {
        this.datasetData = datasetData;
    }

    @JsonProperty("dataset_data")
    public DatasetData getDatasetData() {
        return datasetData;
    }

    public List<DailyStockData> constructDailyStockData() {
        List<String> columnNames = getDatasetData().getColumnNames();
        List<List<String>> data = getDatasetData().getData();

        int indexOfDate = columnNames.lastIndexOf("Date");
        int indexOfAdjOpen = columnNames.lastIndexOf("Adj. Open");
        int indexOfAdjClose = columnNames.lastIndexOf("Adj. Close");

        List<DailyStockData> dailyStockData = new ArrayList<>();
        for (List<String> rawStockDay: data) {
            dailyStockData.add(new DailyStockData(
                    new DateTime(rawStockDay.get(indexOfDate)),
                    new BigDecimal(rawStockDay.get(indexOfAdjOpen)),
                    new BigDecimal(rawStockDay.get(indexOfAdjClose))
            ));
        }
        return dailyStockData;
    }
}
