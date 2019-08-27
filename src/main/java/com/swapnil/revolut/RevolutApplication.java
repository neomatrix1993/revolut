package com.swapnil.revolut;

import com.swapnil.revolut.health.ServerHealthCheck;
import com.swapnil.revolut.resources.AccountResource;
import com.swapnil.revolut.resources.HelloWorldResource;
import com.swapnil.revolut.resources.TransferResource;
import com.swapnil.revolut.services.AccountService;
import com.swapnil.revolut.services.TransferService;
import io.dropwizard.Application;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;

import java.util.ArrayList;
import java.util.List;

public class RevolutApplication extends Application<RevolutApplicationConfiguration> {

    public static void main(final String[] args) throws Exception {
        new RevolutApplication().run(args);
    }

    @Override
    public String getName() {
        return "RevolutApplication";
    }

    @Override
    public void initialize(final Bootstrap<RevolutApplicationConfiguration> bootstrap) {
        // TODO: application initialization
    }

    @Override
    public void run(final RevolutApplicationConfiguration configuration,
                    final Environment environment) {
        List<Object> resources = new ArrayList<>();
        final HelloWorldResource helloWorldResource = new HelloWorldResource();
        resources.add(helloWorldResource);

        final AccountService accountService = new AccountService();
        final TransferService transferService = new TransferService(accountService);
        final TransferResource transferResource = new TransferResource(transferService);
        resources.add(transferResource);

        final AccountResource accountResource = new AccountResource(accountService);
        resources.add(accountResource);


        for (Object resource : resources) {
            environment.jersey().register(resource);
        }

        final ServerHealthCheck healthCheck = new ServerHealthCheck();
        environment.healthChecks().register("server", healthCheck);
    }
}
