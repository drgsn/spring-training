package com.training.controller;

import com.training.model.Task;
import com.training.repository.TaskRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.util.List;

@Controller
public class CrudController {

    @Autowired
    private TaskRepository repository;

    @GetMapping("/task/{id}")
    public List<Task> getUserTasks(@PathVariable @NotNull Long id) {
        return repository.getUserTasks(id);
    }

    @GetMapping("/tasks/all")
    public @ResponseBody Iterable<Task> getAllTasks() {
        return repository.findAll();
    }

    @PostMapping("/task")
    public String addTasks(@ModelAttribute @Valid Task task, Errors errors) {
        if (null != errors && errors.getErrorCount() > 0) {
            return "add_task";
        }

        if (task.getDueDate() == null) {
            task.setDueDate(LocalDate.now().plusDays(1));
        }
        if (task.getUserId() == null) {
            task.setUserId(1l);
        }
        repository.save(task);
        return "redirect:/tasks";
    }

    @PostMapping("/delete")
    public String delete(@ModelAttribute("taskId") @NotNull String id) {
        repository.deleteById(Long.valueOf(id));
        return "redirect:/tasks";
    }
}
