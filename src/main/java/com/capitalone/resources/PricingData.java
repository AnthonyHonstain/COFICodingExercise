package com.capitalone.resources;

import com.codahale.metrics.annotation.Timed;

import javax.ws.rs.GET;
import javax.ws.rs.Path;

@Path("/")
public class PricingData {

    @GET
    @Timed
    public String index() {
        return "STUB";
    }
}
