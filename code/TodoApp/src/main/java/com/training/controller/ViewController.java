package com.training.controller;

import com.training.model.Task;
import com.training.repository.TaskRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.time.LocalDate;

@Controller
public class ViewController {

    @Autowired
    private TaskRepository repository;

    @GetMapping("/")
    public String home(Model model) {
        return homeScreen(model);
    }

    @GetMapping("/tasks")
    public String tasksPage(Model model) {
        return homeScreen(model);
    }

    @GetMapping("/addTask")
    public String addTasksView(Model model) {
        model.addAttribute("task", new Task());
        model.addAttribute("minDate", LocalDate.now().minusDays(2));
        return "add_task";
    }

    private String homeScreen(Model model) {
        model.addAttribute("tasks", repository.findAll());
        return "tasks";
    }

}
