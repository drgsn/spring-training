package com.training.metrics;

import org.springframework.boot.actuate.health.Health;
import org.springframework.boot.actuate.health.HealthIndicator;
import org.springframework.stereotype.Component;

@Component
public class CustomHealthIndicator implements HealthIndicator {

    @Override
    public Health health() {
        int errorCode = check(); // perform some specific health check
        if (errorCode != 0) {
            return Health.outOfService().withDetail("Error Code", errorCode).build();
        }
        return Health.up().build();
    }

    // this is the actual check
    private int check() {
        return -1;
    }
}
