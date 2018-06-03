# COFICoding
Capital One Investing Coding Test

Requirements:
* Java 8
* Maven 3

This is a Dropwizard Java Service (Java framework for RESTful web services) https://www.dropwizard.io/1.3.2/docs/getting-started.html


Basic Commands
---

```$bash
mvn clean install
mvn clean test
```

How to start the COFICoding application
---

1. Verify you have JDK8 and Maven3 (this was created using Maven 3.5.0 with java 1.8.0_171 on ubuntu 17.10)
1. Run `mvn clean install` to build your application
1. Verify your command line has the required environment variable yet, this is to avoid checking the API key into a public repository
1. Start application with `QUANDL_API_KEY=<SECRETE-API-KEY> java -jar target/COFICoding-1.0-SNAPSHOT.jar server config.yml`
1. To check that your application is running enter url `http://localhost:8080`

Health Check
---

To see your applications health enter url `http://localhost:8081/healthcheck`
