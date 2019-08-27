package com.swapnil.revolut.resources;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.concurrent.atomic.AtomicLong;

@Path("/hello-world")
@Produces(MediaType.APPLICATION_JSON)
public class HelloWorldResource {
    private final AtomicLong counter;

    public HelloWorldResource() {
        this.counter = new AtomicLong();
    }

    @GET
    public Response sayHello() {
        return Response.ok().entity("Keep hitting it (☞ﾟヮﾟ)☞ : " + counter.incrementAndGet()).build();
    }
}
