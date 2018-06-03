package com.capitalone;

import com.capitalone.resources.PricingData;
import io.dropwizard.Application;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;

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
        // TODO: application initialization
    }

    @Override
    public void run(final COFICodingConfiguration configuration,
                    final Environment environment) {

        environment.jersey().register(new PricingData());
    }
}
