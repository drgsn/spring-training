package com.training.controller;

import io.micrometer.core.instrument.Counter;
import io.micrometer.core.instrument.MeterRegistry;
import io.micrometer.core.instrument.Timer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.stream.IntStream;


@RestController
public class ApiController {

    private static final Logger log = LoggerFactory.getLogger(ApiController.class);

    private final MeterRegistry meterRegistry;
    private Counter greetsCounter;
    private Counter oddCounter;
    private Counter evenCounter;
    private Counter intCounter;
    private Timer timer;

    @Autowired
    public ApiController(MeterRegistry meterRegistry) {
        this.meterRegistry = meterRegistry;
        oddCounter = Counter.builder("greetings").tag("visits", "odd").register(meterRegistry);
        evenCounter = Counter.builder("greetings").tag("visits", "even").register(meterRegistry);
        greetsCounter = Counter.builder("greetings").tag("visits", "greets").register(meterRegistry);

        intCounter = meterRegistry.counter("for-each-counter");
        timer = meterRegistry.timer("greetings-timer");
    }

    @GetMapping("/test")
    public String test() {
        return "Welcome, everything is up";
    }

    @GetMapping("/hello/{message}")
    public String greetings(@PathVariable String message) {
        log.info("message = {}", message);
        greetsCounter.increment();
        return "Hey, " + message;
    }

    @GetMapping("/hello/{message}/{number}")
    public String greetings(@PathVariable String message, @PathVariable Integer number) {
        log.info("message = {}", message);
        if (number != null && number % 2 == 0) {
            evenCounter.increment();
        } else {
            oddCounter.increment();
        }
        return "Hey, " + message;
    }

    @GetMapping("/")
    public String greetings() {
        // http://localhost:8080/actuator/metrics/greetings-timer
        return timer.record(() -> {
            IntStream.range(0, Integer.MAX_VALUE / 2).forEach(value -> {
                // http://localhost:8080/actuator/metrics/for-each-counter
                intCounter.increment();
            });
            return "you waited a little bit";
        });
    }

}
