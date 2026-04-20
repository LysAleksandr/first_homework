package com.mipt.lysaleksandr.hometask_2.controller;

import com.mipt.lysaleksandr.hometask_2.dto.TaskResponseDto;
import com.mipt.lysaleksandr.hometask_2.mapper.TaskMapper;
import com.mipt.lysaleksandr.hometask_2.service.FavoritesService;
import com.mipt.lysaleksandr.hometask_2.service.TaskService;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.servlet.http.HttpSession;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v2/favorites")
public class FavoritesController {

    private final FavoritesService favoritesService;
    private final TaskService taskService;
    private final TaskMapper taskMapper;

    public FavoritesController(FavoritesService favoritesService, TaskService taskService,
        TaskMapper taskMapper) {
        this.favoritesService = favoritesService;
        this.taskService = taskService;
        this.taskMapper = taskMapper;
    }

    @Operation(summary = "Add task to favorites")
    @PostMapping("/{taskId}")
    public ResponseEntity<Void> addFavorite(@PathVariable Long taskId, HttpSession session) {
        favoritesService.addFavorite(taskId, session);
        return ResponseEntity.ok().build();
    }

    @Operation(summary = "Remove task from favorites")
    @DeleteMapping("/{taskId}")
    public ResponseEntity<Void> removeFavorite(@PathVariable Long taskId, HttpSession session) {
        favoritesService.removeFavorite(taskId, session);
        return ResponseEntity.ok().build();
    }

    @Operation(summary = "Get favorite tasks")
    @GetMapping
    public ResponseEntity<List<TaskResponseDto>> getFavorites(HttpSession session) {
        Set<Long> favoriteIds = favoritesService.getFavoriteIds(session);
        List<TaskResponseDto> favoriteTasks = favoriteIds.stream()
            .map(taskService::findById)
            .map(taskMapper::toResponseDto)
            .collect(Collectors.toList());
        return ResponseEntity.ok(favoriteTasks);
    }
}