package com.training.client;

import com.training.model.Task;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import javax.annotation.PostConstruct;

@Component
public class TaskClient {

    private static WebClient client = WebClient.create("http://localhost:8080");
    private static final Logger log = LoggerFactory.getLogger(TaskClient.class);

//    @PostConstruct
    public void init() {
        consume();
    }

    public static void consume() {

        Mono<Task> taskMono = client.get()
                .uri("/tasks/{id}", "1")
                .retrieve()
                .bodyToMono(Task.class);

        taskMono.subscribe(task -> log.info("task {}", task));

        Flux<Task> taskFlux = client.get()
                .uri("/tasks")
                .retrieve()
                .bodyToFlux(Task.class);

        taskFlux.subscribe(tasks -> log.info("task flux {}", tasks));
    }
}
