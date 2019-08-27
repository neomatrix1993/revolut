package com.swapnil.revolut.health;

import com.codahale.metrics.health.HealthCheck;

public class ServerHealthCheck extends HealthCheck {

    @Override
    protected Result check() {
        return Result.healthy();
    }
}
