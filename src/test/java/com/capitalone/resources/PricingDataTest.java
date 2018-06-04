package com.capitalone.resources;

import com.capitalone.client.QuandlClient;
import com.capitalone.core.DailyStockData;
import com.capitalone.core.QuandlExternal.DatasetData;
import com.capitalone.core.QuandlExternal.QuandlResponse;
import com.google.common.collect.ImmutableList;
import org.junit.Rule;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoRule;
import org.mockito.quality.Strictness;

import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;


public class PricingDataTest {

    @Mock
    private QuandlClient quandlClient;

    @Rule
    public MockitoRule rule = MockitoJUnit.rule().strictness(Strictness.STRICT_STUBS);

    @Test
    public void getStockDataBasicTest() {
        List<DailyStockData> expectedResult = ImmutableList.of(
                new DailyStockData("2017-06-30","943.99", "929.68"),
                new DailyStockData("2017-06-29","951.35", "937.82"),
                new DailyStockData("2017-06-28","950.66", "961.01")
        );

        DatasetData datasetData = new DatasetData(
                ImmutableList.of("Date", "Adj. Open", "Adj. Close"),
                ImmutableList.of(
                        ImmutableList.of("2017-06-30","943.99", "929.68"),
                        ImmutableList.of("2017-06-29","951.35", "937.82"),
                        ImmutableList.of("2017-06-28","950.66", "961.01")
                ));
        QuandlResponse quandlResponse = new QuandlResponse(datasetData);

        when(quandlClient.getStockData(any(String.class))).thenReturn(quandlResponse);
        PricingData pricingData = new PricingData(quandlClient);

        List<DailyStockData> response = pricingData.prototype();

        assertEquals(expectedResult, response);
    }
}
