package com.training.repository;

import com.training.model.Task;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.HashMap;
import java.util.Map;

@Repository
public class TaskRepository  {


    static Map<Long, Task> taskData;

    static
    {
        taskData = new HashMap<>();
        taskData.put(1l,new Task(1l,"Task 1", "Task description 1"));
        taskData.put(2l,new Task(2l,"Task 2", "Task description 2"));
        taskData.put(3l,new Task(3l,"Task 3", "Task description 3"));
        taskData.put(4l,new Task(4l,"Task 4", "Task description 4"));
        taskData.put(5l,new Task(5l,"Task 5", "Task description 5"));
        taskData.put(6l,new Task(6l,"Task 6", "Task description 6"));
        taskData.put(7l,new Task(7l,"Task 7", "Task description 7"));
        taskData.put(8l,new Task(8l,"Task 8", "Task description 8"));
        taskData.put(9l,new Task(9l,"Task 9", "Task description 9"));
        taskData.put(10l,new Task(10l,"Task 10", "Task description 10"));
    }

    public Mono<Task> findTaskById(Long id)
    {
        return Mono.just(taskData.get(Long.valueOf(id)));
    }

    public Flux<Task> findAllTasks()
    {
        return Flux.fromIterable(taskData.values());
    }

    public Mono<Task> updateTask(Task task)
    {
        Task existingTask= taskData.get(task.getId());
        if(existingTask!=null)
        {
            existingTask.setTitle(task.getTitle());
            existingTask.setDescription(task.getDescription());
        }
        return Mono.just(existingTask);
    }
}
