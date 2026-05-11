package com.example.gateway.service;

import com.example.gateway.client.ExternalTasksClient;
import com.example.gateway.dto.TaskCreateRequest;
import com.example.gateway.dto.TaskDto;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.ratelimiter.annotation.RateLimiter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import java.util.Collections;
import java.util.List;

@Service
public class TasksGatewayService {

    private static final Logger log = LoggerFactory.getLogger(TasksGatewayService.class);
    private final ExternalTasksClient client;

    public TasksGatewayService(ExternalTasksClient client) {
        this.client = client;
    }

    @RateLimiter(name = "externalApi")
    @CircuitBreaker(name = "externalApi", fallbackMethod = "createTaskFallback")
    public TaskDto createTask(TaskCreateRequest request) {
        return client.createTask(request);
    }

    public TaskDto createTaskFallback(TaskCreateRequest request, Throwable t) {
        log.warn("Fallback createTask: {}", t.getMessage());
        return new TaskDto(0L, "fallback-cached-task", true);
    }

    @RateLimiter(name = "externalApi")
    @CircuitBreaker(name = "externalApi", fallbackMethod = "getTaskFallback")
    public TaskDto getTask(Long id) {
        return client.getTask(id);
    }

    public TaskDto getTaskFallback(Long id, Throwable t) {
        log.warn("Fallback getTask: {}", t.getMessage());
        return new TaskDto(id, "cached task (from fallback)", false);
    }

    @RateLimiter(name = "externalApi")
    @CircuitBreaker(name = "externalApi", fallbackMethod = "listTasksFallback")
    public List<TaskDto> listTasks(Boolean completed, int limit) {
        return client.listTasks(completed, limit);
    }

    public List<TaskDto> listTasksFallback(Boolean completed, int limit, Throwable t) {
        log.warn("Fallback listTasks: {}", t.getMessage());
        return Collections.emptyList();
    }

    @RateLimiter(name = "externalApi")
    @CircuitBreaker(name = "externalApi", fallbackMethod = "deleteTaskFallback")
    public void deleteTask(Long id) {
        client.deleteTask(id);
    }

    public void deleteTaskFallback(Long id, Throwable t) {
        log.warn("Fallback deleteTask: {}", t.getMessage());
    }
}