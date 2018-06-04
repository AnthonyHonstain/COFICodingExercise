package com.capitalone;

import io.dropwizard.client.JerseyClientBuilder;
import io.dropwizard.testing.ResourceHelpers;
import io.dropwizard.testing.junit.DropwizardAppRule;
import org.junit.ClassRule;
import org.junit.Test;

import javax.ws.rs.client.Client;
import javax.ws.rs.core.Response;

import static org.junit.Assert.assertEquals;


public class PricingDataIntergrationTest {

    @ClassRule
    public static final DropwizardAppRule<COFICodingConfiguration> RULE =
            new DropwizardAppRule<>(COFICodingApplication.class,
                    ResourceHelpers.resourceFilePath("config.yml"));

    @Test
    public void testServiceEndToEndBasic() {
        Client client = new JerseyClientBuilder(RULE.getEnvironment()).build("test client");

        Response response = client.target(
                String.format("http://localhost:%d/", RULE.getLocalPort()))
                .request().get();

        assertEquals(200, response.getStatus());
    }
}
