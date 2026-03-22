package com.mipt.lysaleksandr.todolistmanager.service;

import com.mipt.lysaleksandr.todolistmanager.model.Task;
import com.mipt.lysaleksandr.todolistmanager.repository.TaskRepository;
import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class TaskService {

    private final TaskRepository taskRepository;
    private final Map<Long, Task> taskCache = new ConcurrentHashMap<>();

    public TaskService(TaskRepository taskRepository) {
        this.taskRepository = taskRepository;
    }

    @PostConstruct
    public void initCache() {
        taskRepository.findAll().forEach(task -> taskCache.put(task.getId(), task));
        System.out.println("Cache initialized with " + taskCache.size() + " tasks");
    }

    @PreDestroy
    public void cleanUp() {
        System.out.println("Cleaning up. Cache size: " + taskCache.size());
        taskCache.clear();
    }

    public List<Task> findAll() {
        return taskRepository.findAll();
    }

    public Optional<Task> findById(Long id) {
        Task cachedTask = taskCache.get(id);
        if (cachedTask != null) {
            return Optional.of(cachedTask);
        }
        Optional<Task> task = taskRepository.findById(id);
        task.ifPresent(t -> taskCache.put(id, t));
        return task;
    }

    public Task save(Task task) {
        Task savedTask = taskRepository.save(task);
        taskCache.put(savedTask.getId(), savedTask);
        return savedTask;
    }

    public void deleteById(Long id) {
        taskRepository.deleteById(id);
        taskCache.remove(id);
    }
}
