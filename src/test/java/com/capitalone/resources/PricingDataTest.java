package com.capitalone.resources;

import com.capitalone.api.StockSummary;
import com.capitalone.client.QuandlClient;
import com.capitalone.core.DailyStockData;
import com.capitalone.core.QuandlExternal.DatasetData;
import com.capitalone.core.QuandlExternal.QuandlResponse;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;
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
import java.util.Map;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;


public class PricingDataTest {

    @Mock
    private QuandlClient quandlClient;
    private PricingData pricingData;

    @Rule
    public MockitoRule rule = MockitoJUnit.rule().strictness(Strictness.STRICT_STUBS);

    @Before
    public void before() {
         pricingData = new PricingData(quandlClient);
    }

    @Test
    public void quandlClientGetStockData() {
        // TODO - I don't like the layout of logic this test covers, I also don't like that this is where client test is located - move it.
        DatasetData datasetData = new DatasetData(
                ImmutableList.of("Date", "Adj. Open", "Adj. Close"),
                ImmutableList.of(
                        ImmutableList.of("2017-06-30", "943.99", "929.68"),
                        ImmutableList.of("2017-06-29", "951.35", "937.82"),
                        ImmutableList.of("2017-06-28", "950.66", "961.01")
                ));
        QuandlResponse quandlResponse = new QuandlResponse(datasetData);
        List<DailyStockData> response = quandlResponse.constructDailyStockData();

        List<DailyStockData> expectedResult = ImmutableList.of(
                new DailyStockData(DateTime.parse("2017-06-30"), new BigDecimal("943.99"), new BigDecimal("929.68")),
                new DailyStockData(DateTime.parse("2017-06-29"), new BigDecimal("951.35"), new BigDecimal("937.82")),
                new DailyStockData(DateTime.parse("2017-06-28"), new BigDecimal("950.66"), new BigDecimal("961.01"))
        );
        assertEquals(expectedResult, response);
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

        Map<String, List<StockSummary>> response = pricingData.prototype();

        assertEquals(ImmutableSet.of("COF", "GOOGL", "MSFT"), response.keySet());

        List<StockSummary> googData = response.get("GOOGL");
        assertEquals(1, googData.size());
        assertEquals("2017-06", googData.get(0).getMonth());
        assertEquals(new BigDecimal("948.66"), googData.get(0).getAverageOpen());
        assertEquals(new BigDecimal("942.83"), googData.get(0).getAverageClose());

        List<StockSummary> cofData = response.get("COF");
        assertEquals(1, cofData.size());
        assertEquals("2017-06", cofData.get(0).getMonth());
        assertEquals(new BigDecimal("948.66"), cofData.get(0).getAverageOpen());
        assertEquals(new BigDecimal("942.83"), cofData.get(0).getAverageClose());
    }
}
