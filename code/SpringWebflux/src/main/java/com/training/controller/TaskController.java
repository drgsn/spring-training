package com.training.controller;

import com.training.model.Task;
import com.training.repository.TaskRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/tasks")
public class TaskController {

    private final TaskRepository taskRepository;

    @Autowired
    public TaskController(TaskRepository taskRepository) {
        this.taskRepository = taskRepository;
    }

    // single value
    @GetMapping("/{id}")
    private Mono<Task> getTaskById(@PathVariable(name = "id") Long id) {
        return taskRepository.findTaskById(id);
    }

    // multiple values
    @GetMapping
    private Flux<Task> getAllTasks() {
        return taskRepository.findAllTasks();
    }

    // streams all events to client
    @GetMapping(value = "/tasks/live", produces = "application/stream+json")
    private Flux<Task> getAllTasksLive() {
        return taskRepository.findAllTasks();
    }

    @PostMapping("/update")
    private Mono<Task> updateEmployee(@RequestBody Task task) {
        return taskRepository.updateTask(task);
    }
}
