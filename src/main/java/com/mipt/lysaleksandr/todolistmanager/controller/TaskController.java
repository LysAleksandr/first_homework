package com.mipt.lysaleksandr.todolistmanager.controller;

import com.mipt.lysaleksandr.todolistmanager.model.Task;
import com.mipt.lysaleksandr.todolistmanager.service.TaskService;
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