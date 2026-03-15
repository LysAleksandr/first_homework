package com.mipt.lysaleksandr.todomanager.controller;

import com.mipt.lysaleksandr.todomanager.model.Task;
import com.mipt.lysaleksandr.todomanager.service.TaskService;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/tasks")
public class TaskController {

    private final TaskService taskService;

    public TaskController(TaskService taskService) {
        this.taskService = taskService;
    }

    @GetMapping
    public List<Task> getAllTasks() {
        return taskService.findAll();
    }
}