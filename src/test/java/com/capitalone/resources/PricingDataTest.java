package com.capitalone.resources;

import com.capitalone.client.QuandlClient;
import com.capitalone.core.DailyStockData;
import com.capitalone.core.QuandlExternal.DatasetData;
import com.capitalone.core.QuandlExternal.QuandlResponse;
import com.google.common.collect.ImmutableList;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoRule;
import org.mockito.quality.Strictness;

import java.math.BigDecimal;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;


public class PricingDataTest {

    @Mock
    private QuandlClient quandlClient;
    private PricingData pricingData;

    private final List<DailyStockData> dailyStockData = ImmutableList.of(
            new DailyStockData("2017-06-30", new BigDecimal("943.99"), new BigDecimal("929.68")),
            new DailyStockData("2017-06-29", new BigDecimal("951.35"), new BigDecimal("937.82")),
            new DailyStockData("2017-06-28", new BigDecimal("950.66"), new BigDecimal("961.01"))
    );

    @Rule
    public MockitoRule rule = MockitoJUnit.rule().strictness(Strictness.STRICT_STUBS);

    @Before
    public void before() {
         pricingData = new PricingData(quandlClient);
    }

    @Test
    public void getStockDataBasicTest() {
        DatasetData datasetData = new DatasetData(
                ImmutableList.of("Date", "Adj. Open", "Adj. Close"),
                ImmutableList.of(
                        ImmutableList.of("2017-06-30", "943.99", "929.68"),
                        ImmutableList.of("2017-06-29", "951.35", "937.82"),
                        ImmutableList.of("2017-06-28", "950.66", "961.01")
                ));
        QuandlResponse quandlResponse = new QuandlResponse(datasetData);

        when(quandlClient.getStockData(any(String.class))).thenReturn(quandlResponse);

        List<DailyStockData> response = pricingData.prototype();

        assertEquals(dailyStockData, response);
    }

    @Test
    public void calculateAverageAdjustedOpenPriceTest() {
        BigDecimal result = pricingData.calculateAverage(dailyStockData, DailyStockData::getAdjOpen);
        assertEquals(new BigDecimal("948.66"), result);
    }

    @Test
    public void calculateAverageAdjustedClosePriceTest() {
        BigDecimal result = pricingData.calculateAverage(dailyStockData, DailyStockData::getAdjClose);
        assertEquals(new BigDecimal("942.83"), result);
    }
}
