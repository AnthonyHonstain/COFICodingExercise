package com.capitalone.resources;

import com.capitalone.api.StockSummary;
import com.capitalone.client.QuandlClient;
import com.capitalone.core.DailyStockData;
import com.capitalone.core.QuandlExternal.DatasetData;
import com.capitalone.core.QuandlExternal.QuandlResponse;
import com.google.common.collect.ImmutableList;
import org.joda.time.DateTime;
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
            new DailyStockData(DateTime.parse("2017-06-30"), new BigDecimal("943.99"), new BigDecimal("929.68")),
            new DailyStockData(DateTime.parse("2017-06-29"), new BigDecimal("951.35"), new BigDecimal("937.82")),
            new DailyStockData(DateTime.parse("2017-06-28"), new BigDecimal("950.66"), new BigDecimal("961.01"))
    );

    @Rule
    public MockitoRule rule = MockitoJUnit.rule().strictness(Strictness.STRICT_STUBS);

    @Before
    public void before() {
         pricingData = new PricingData(quandlClient);
    }

    @Test
    public void quandlClientGetStockData() {
        DatasetData datasetData = new DatasetData(
                ImmutableList.of("Date", "Adj. Open", "Adj. Close"),
                ImmutableList.of(
                        ImmutableList.of("2017-06-30", "943.99", "929.68"),
                        ImmutableList.of("2017-06-29", "951.35", "937.82"),
                        ImmutableList.of("2017-06-28", "950.66", "961.01")
                ));
        QuandlResponse quandlResponse = new QuandlResponse(datasetData);
        List<DailyStockData> response = quandlResponse.constructDailyStockData();

        assertEquals(dailyStockData, response);
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

        List<StockSummary> response = pricingData.prototype();

        assertEquals(1, response.size());
        assertEquals("2017-06", response.get(0).getMonth());
        assertEquals(new BigDecimal("948.66"), response.get(0).getAverageOpen());
        assertEquals(new BigDecimal("942.83"), response.get(0).getAverageClose());
    }

    @Test
    public void getStockDataBasicTestSingleDayInFirstMonthFromList() {
        DatasetData datasetData = new DatasetData(
                ImmutableList.of("Date", "Adj. Open", "Adj. Close"),
                ImmutableList.of(
                        ImmutableList.of("2017-07-01", "941.33", "959.31"),
                        ImmutableList.of("2017-06-30", "943.99", "929.68"),
                        ImmutableList.of("2017-06-29", "951.35", "937.82"),
                        ImmutableList.of("2017-06-28", "950.66", "961.01")
                ));
        QuandlResponse quandlResponse = new QuandlResponse(datasetData);

        when(quandlClient.getStockData(any(String.class))).thenReturn(quandlResponse);

        List<StockSummary> response = pricingData.prototype();

        assertEquals(2, response.size());

        assertEquals("2017-07", response.get(0).getMonth());
        assertEquals(new BigDecimal("941.33"), response.get(0).getAverageOpen());
        assertEquals(new BigDecimal("959.31"), response.get(0).getAverageClose());

        assertEquals("2017-06", response.get(1).getMonth());
        assertEquals(new BigDecimal("948.66"), response.get(1).getAverageOpen());
        assertEquals(new BigDecimal("942.83"), response.get(1).getAverageClose());
    }

    @Test
    public void getStockDataBasicTestSingleDayInLastMonthFromList() {
        DatasetData datasetData = new DatasetData(
                ImmutableList.of("Date", "Adj. Open", "Adj. Close"),
                ImmutableList.of(
                        ImmutableList.of("2017-06-30", "943.99", "929.68"),
                        ImmutableList.of("2017-06-29", "951.35", "937.82"),
                        ImmutableList.of("2017-06-28", "950.66", "961.01"),
                        ImmutableList.of("2017-05-31", "941.33", "959.31")
                ));
        QuandlResponse quandlResponse = new QuandlResponse(datasetData);

        when(quandlClient.getStockData(any(String.class))).thenReturn(quandlResponse);

        List<StockSummary> response = pricingData.prototype();

        assertEquals(2, response.size());

        assertEquals("2017-06", response.get(0).getMonth());
        assertEquals(new BigDecimal("948.66"), response.get(0).getAverageOpen());
        assertEquals(new BigDecimal("942.83"), response.get(0).getAverageClose());

        assertEquals("2017-05", response.get(1).getMonth());
        assertEquals(new BigDecimal("941.33"), response.get(1).getAverageOpen());
        assertEquals(new BigDecimal("959.31"), response.get(1).getAverageClose());
    }

    @Test
    public void getStockDataThreeMonths() {
        DatasetData datasetData = new DatasetData(
                ImmutableList.of("Date", "Adj. Open", "Adj. Close"),
                ImmutableList.of(
                        ImmutableList.of("2017-07-01", "941.33", "959.31"),
                        ImmutableList.of("2017-06-30", "943.99", "929.68"),
                        ImmutableList.of("2017-06-29", "951.35", "937.82"),
                        ImmutableList.of("2017-06-28", "950.66", "961.01"),
                        ImmutableList.of("2017-05-31", "640.33", "950.22"),
                        ImmutableList.of("2017-05-30", "640.22", "950.11")
                ));
        QuandlResponse quandlResponse = new QuandlResponse(datasetData);

        when(quandlClient.getStockData(any(String.class))).thenReturn(quandlResponse);

        List<StockSummary> response = pricingData.prototype();

        assertEquals(3, response.size());
        assertEquals("2017-07", response.get(0).getMonth());
        assertEquals(new BigDecimal("941.33"), response.get(0).getAverageOpen());
        assertEquals(new BigDecimal("959.31"), response.get(0).getAverageClose());

        assertEquals("2017-06", response.get(1).getMonth());
        assertEquals(new BigDecimal("948.66"), response.get(1).getAverageOpen());
        assertEquals(new BigDecimal("942.83"), response.get(1).getAverageClose());

        assertEquals("2017-05", response.get(2).getMonth());
        assertEquals(new BigDecimal("640.27"), response.get(2).getAverageOpen());
        assertEquals(new BigDecimal("950.16"), response.get(2).getAverageClose());
    }
}
