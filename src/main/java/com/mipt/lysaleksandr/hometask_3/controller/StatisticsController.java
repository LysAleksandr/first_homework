package com.mipt.lysaleksandr.hometask_3.controller;

import com.mipt.lysaleksandr.hometask_3.dto.PriorityCountDto;
import com.mipt.lysaleksandr.hometask_3.service.TaskStatisticsJdbcService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.List;

@RestController
@RequestMapping("/api/v3/statistics")
public class StatisticsController {

    private final TaskStatisticsJdbcService statisticsService;

    public StatisticsController(TaskStatisticsJdbcService statisticsService) {
        this.statisticsService = statisticsService;
    }

    @GetMapping("/priority-count")
    public ResponseEntity<List<PriorityCountDto>> getPriorityCount() {
        return ResponseEntity.ok(statisticsService.getTasksCountByPriority());
    }
}