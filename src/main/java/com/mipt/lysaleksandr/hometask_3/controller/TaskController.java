package com.mipt.lysaleksandr.hometask_3.controller;

import com.mipt.lysaleksandr.hometask_3.dto.TaskResponseDto;
import com.mipt.lysaleksandr.hometask_3.mapper.TaskMapper;
import com.mipt.lysaleksandr.hometask_3.model.Task;
import com.mipt.lysaleksandr.hometask_3.service.TaskService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v3/tasks")
public class TaskController {

    private final TaskService taskService;
    private final TaskMapper taskMapper;

    public TaskController(TaskService taskService, TaskMapper taskMapper) {
        this.taskService = taskService;
        this.taskMapper = taskMapper;
    }

    @GetMapping("/with-attachments")
    public ResponseEntity<List<TaskResponseDto>> getAllTasksWithAttachments() {
        List<TaskResponseDto> dtos = taskService.getAllTasksWithAttachments().stream()
            .map(taskMapper::toResponseDto).collect(Collectors.toList());
        return ResponseEntity.ok()
            .header("X-Total-Count", String.valueOf(taskService.getTotalCount())).body(dtos);
    }

    @GetMapping("/due-next-week")
    public ResponseEntity<List<TaskResponseDto>> getTasksDueWithinNext7Days() {
        List<TaskResponseDto> dtos = taskService.getTasksDueWithinNext7Days().stream()
            .map(taskMapper::toResponseDto).collect(Collectors.toList());
        return ResponseEntity.ok(dtos);
    }

    @PostMapping("/bulk-complete")
    public ResponseEntity<Void> bulkCompleteTasks(@RequestBody List<Long> ids) {
        taskService.bulkCompleteTasks(ids);
        return ResponseEntity.ok().build();
    }
}