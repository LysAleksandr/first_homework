package com.mipt.lysaleksandr.hometask_2.controller;

import com.mipt.lysaleksandr.hometask_2.dto.*;
import com.mipt.lysaleksandr.hometask_2.mapper.TaskMapper;
import com.mipt.lysaleksandr.hometask_2.model.Task;
import com.mipt.lysaleksandr.hometask_2.service.TaskService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v2/tasks")
@Validated
public class TaskController {

    private final TaskService taskService;
    private final TaskMapper taskMapper;

    public TaskController(TaskService taskService, TaskMapper taskMapper) {
        this.taskService = taskService;
        this.taskMapper = taskMapper;
    }

    @Operation(summary = "Create a new task")
    @ApiResponse(responseCode = "201", description = "Task created")
    @PostMapping
    public ResponseEntity<TaskResponseDto> createTask(
        @Validated(OnCreate.class) @RequestBody TaskCreateDto createDto) {
        Task task = taskMapper.toEntity(createDto);
        Task saved = taskService.save(task);
        return ResponseEntity.status(201).body(taskMapper.toResponseDto(saved));
    }

    @Operation(summary = "Get task by ID")
    @GetMapping("/{id}")
    public ResponseEntity<TaskResponseDto> getTask(@PathVariable Long id) {
        Task task = taskService.findById(id);
        return ResponseEntity.ok(taskMapper.toResponseDto(task));
    }

    @Operation(summary = "Get all tasks")
    @GetMapping
    public ResponseEntity<List<TaskResponseDto>> getAllTasks() {
        List<TaskResponseDto> dtos = taskService.findAll().stream()
            .map(taskMapper::toResponseDto)
            .collect(Collectors.toList());
        return ResponseEntity.ok()
            .header("X-Total-Count", String.valueOf(taskService.getTotalCount()))
            .body(dtos);
    }

    @Operation(summary = "Update task partially")
    @PutMapping("/{id}")
    public ResponseEntity<TaskResponseDto> updateTask(@PathVariable Long id,
        @Validated(OnUpdate.class) @RequestBody TaskUpdateDto updateDto) {
        Task existing = taskService.findById(id);
        taskMapper.updateEntity(updateDto, existing);
        if (updateDto.getCompleted() != null) {
            existing.setCompleted(updateDto.getCompleted());
        }
        Task updated = taskService.save(existing);
        return ResponseEntity.ok(taskMapper.toResponseDto(updated));
    }

    @Operation(summary = "Delete task")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTask(@PathVariable Long id) {
        taskService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}