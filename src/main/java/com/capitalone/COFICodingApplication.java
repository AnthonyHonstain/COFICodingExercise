package com.capitalone;

import com.capitalone.resources.PricingData;
import io.dropwizard.Application;
import io.dropwizard.client.JerseyClientBuilder;
import io.dropwizard.configuration.EnvironmentVariableSubstitutor;
import io.dropwizard.configuration.SubstitutingSourceProvider;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;

import javax.ws.rs.client.Client;

public class COFICodingApplication extends Application<COFICodingConfiguration> {

    public static void main(final String[] args) throws Exception {
        new COFICodingApplication().run(args);
    }

    @Override
    public String getName() {
        return "COFICoding";
    }

    @Override
    public void initialize(final Bootstrap<COFICodingConfiguration> bootstrap) {
        bootstrap.setConfigurationSourceProvider(
                new SubstitutingSourceProvider(bootstrap.getConfigurationSourceProvider(),
                        new EnvironmentVariableSubstitutor(false))
        );
    }

    @Override
    public void run(final COFICodingConfiguration configuration,
                    final Environment environment) {

        final Client quandlClient = new JerseyClientBuilder(environment)
                .using(configuration.getJerseyClientConfiguration())
                .build(getName());

        environment.jersey().register(new PricingData(quandlClient, configuration.getQuandlAPIKey()));
    }
}
