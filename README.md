# COFICoding
Capital One Investing Coding Test

Requirements:
* Java 8
* Maven 3

This is a Dropwizard Java Service (Java framework for RESTful web services) https://www.dropwizard.io/1.3.2/docs/getting-started.html

Helpful References:
* Dropwizard Config https://www.dropwizard.io/1.3.2/docs/manual/configuration.html#man-configuration-clients-http
* Dropwizard Client https://www.dropwizard.io/1.3.2/docs/manual/client.html
* BigDecimal https://docs.oracle.com/javase/8/docs/api/java/math/BigDecimal.html

#### Overview of the project:
* COFICoding/src/main/java/com/capitalone/
  * COFICodingApplication.java - Initializes the Dropwizard service.
  * COFICodingConfiguration.java - Object to marshal the configuration settings needed by COFICodingApplication.
  * resources/
    * PricingData.java - defines the basic REST endpoints the service supports.
  * core/
    * The internal objects and logic to process and calculate the securities information.
  * client/
    * QuandlClient.java - the basic abstraction for calling the Quandl API
  * api/
    * The basic objects that define the JSON the resources/service will return to callers
* COFICoding/src/test/java/com/capitalone/
   * PricingDataIntegrationTest.java - a very basic integration test that starts the service up locally.
   * core/DailyStockProcessorTest.java - unit tests the logic to process daily price information
   * resources/PricingData.java - a light integration/unit test that utilizes a mock of the Quandl client to verify basic endpoint behavior.

#### Current Issues/Opportunities
* Add a logging framework.
* Improve use of Quandl API, at least one java project exists, needs more review. Probably have a bunch of low hanging fruit here.
* Further refactor and cleanup my client code to call Quandl given the endpoints I am calling.

How to start the COFICoding application
---
NOTE - the api key for Quandl must be passed in as an environment variable.

1. Verify you have JDK8 and Maven3 (this was created using Maven 3.5.0 with java 1.8.0_171 on ubuntu 17.10)
1. Run `mvn clean install` to build your application
1. Verify your command line has the required environment variable yet, this is to avoid checking the API key into a public repository
1. Start application with `QUANDL_API_KEY=<SECRETE-API-KEY> java -jar target/COFICoding-1.0-SNAPSHOT.jar server config.yml`
1. To check that your application is running enter url `http://localhost:8080`

Tests can be run using `mvn clean test`

Important Endpoints
---

* localhost:8080 - basic endpoint that displays the currently supported ticket.
* localhost:8080/averageMonthlyPrice - calculates the average monthly open and close prices
* localhost:8080/maxDailyProfit - Identifies the high amount of profit for a security if purchased at the day's low and sold at the day's high

#### Example Results
* localhost:8080/averageMonthlyPrice
```json
{
    "MSFT": [
        {
            "month": "2017-06",
            "average_open": 69.83,
            "average_close": 69.79
        },
        {
            "month": "2017-05",
            "average_open": 67.92,
            "average_close": 68.01
        },
        {
            "month": "2017-04",
            "average_open": 65.18,
            "average_close": 65.12
        },
        {
            "month": "2017-03",
            "average_open": 63.73,
            "average_close": 63.81
        },
        {
            "month": "2017-02",
            "average_open": 62.94,
            "average_close": 62.91
        },
        {
            "month": "2017-01",
            "average_open": 61.81,
            "average_close": 61.81
        }
    ],
    "GOOGL": [
        {
            "month": "2017-06",
            "average_open": 975.78,
            "average_close": 973.37
        },
        {
            "month": "2017-05",
            "average_open": 959.59,
            "average_close": 961.65
        },
        {
            "month": "2017-04",
            "average_open": 860.07,
            "average_close": 861.37
        },
        {
            "month": "2017-03",
            "average_open": 853.85,
            "average_close": 853.78
        },
        {
            "month": "2017-02",
            "average_open": 836.15,
            "average_close": 836.75
        },
        {
            "month": "2017-01",
            "average_open": 829.85,
            "average_close": 830.24
        }
    ],
    "COF": [
        {
            "month": "2017-06",
            "average_open": 79.72,
            "average_close": 79.95
        },
        {
            "month": "2017-05",
            "average_open": 80.12,
            "average_close": 79.98
        },
        {
            "month": "2017-04",
            "average_open": 82.6,
            "average_close": 82.43
        },
        {
            "month": "2017-03",
            "average_open": 88.4,
            "average_close": 88.06
        },
        {
            "month": "2017-02",
            "average_open": 88.86,
            "average_close": 89.2
        },
        {
            "month": "2017-01",
            "average_open": 87.05,
            "average_close": 87.01
        }
    ]
}
```

* localhost:8080/maxDailyProfit 
```json
{
    "MSFT": {
        "date": "2017-06-09",
        "estimatedProfit": 3.45
    },
    "GOOGL": {
        "date": "2017-06-09",
        "estimatedProfit": 52.13
    },
    "COF": {
        "date": "2017-03-21",
        "estimatedProfit": 3.72
    }
}
```

