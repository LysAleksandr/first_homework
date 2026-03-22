package com.mipt.lysaleksandr.todolistmanager.controller;

import com.mipt.lysaleksandr.todolistmanager.config.AppConfig;
import com.mipt.lysaleksandr.todolistmanager.model.Task;
import com.mipt.lysaleksandr.todolistmanager.service.TaskService;
import org.springframework.beans.factory.annotation.Lookup;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/tasks")
public class TaskController {

    private final TaskService taskService;
    private final AppConfig.RequestScopedBean requestScopedBean;

    public TaskController(TaskService taskService,
        AppConfig.RequestScopedBean requestScopedBean) {
        this.taskService = taskService;
        this.requestScopedBean = requestScopedBean;
    }

    @GetMapping
    public List<Task> getAllTasks() {
        System.out.println("Request ID: " + requestScopedBean.getRequestId());
        return taskService.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Task> getTaskById(@PathVariable Long id) {
        return taskService.findById(id)
            .map(ResponseEntity::ok)
            .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Task createTask(@RequestBody Task task) {
        AppConfig.PrototypeScopedBean prototypeBean = getPrototypeScopedBean();
        System.out.println("Generated ID: " + prototypeBean.generateId());
        return taskService.save(task);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Task> updateTask(@PathVariable Long id, @RequestBody Task task) {
        return taskService.findById(id)
            .map(existingTask -> {
                task.setId(id);
                return ResponseEntity.ok(taskService.save(task));
            })
            .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTask(@PathVariable Long id) {
        if (taskService.findById(id).isPresent()) {
            taskService.deleteById(id);
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }

    @Lookup
    public AppConfig.PrototypeScopedBean getPrototypeScopedBean() {
        return null;
    }
}
