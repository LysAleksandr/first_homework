package com.example.gateway.api;

import com.example.gateway.dto.TaskCreateRequest;
import com.example.gateway.dto.TaskDto;
import com.example.gateway.service.TasksGatewayService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/v1/tasks")
public class TasksGatewayController {

    private final TasksGatewayService gatewayService;

    public TasksGatewayController(TasksGatewayService gatewayService) {
        this.gatewayService = gatewayService;
    }

    @PostMapping
    public ResponseEntity<TaskDto> createTask(@RequestBody TaskCreateRequest request) {
        TaskDto created = gatewayService.createTask(request);
        return ResponseEntity.status(201).body(created);
    }

    @GetMapping("/{id}")
    public ResponseEntity<TaskDto> getTask(@PathVariable Long id) {
        return ResponseEntity.ok(gatewayService.getTask(id));
    }

    @GetMapping
    public ResponseEntity<List<TaskDto>> listTasks(
        @RequestParam(required = false) Boolean completed,
        @RequestParam(defaultValue = "10") int limit) {
        return ResponseEntity.ok(gatewayService.listTasks(completed, limit));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTask(@PathVariable Long id) {
        gatewayService.deleteTask(id);
        return ResponseEntity.noContent().build();
    }
}