package com.mipt.lysaleksandr.hometask_2.service;

import com.mipt.lysaleksandr.hometask_2.exception.TaskNotFoundException;
import com.mipt.lysaleksandr.hometask_2.model.Task;
import com.mipt.lysaleksandr.hometask_2.repository.TaskRepository;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class TaskService {

    private final TaskRepository taskRepository;

    public TaskService(TaskRepository taskRepository) {
        this.taskRepository = taskRepository;
    }

    public List<Task> findAll() {
        return taskRepository.findAll();
    }

    public Task findById(Long id) {
        return taskRepository.findById(id)
            .orElseThrow(() -> new TaskNotFoundException("Task not found with id: " + id));
    }

    public Task save(Task task) {
        return taskRepository.save(task);
    }

    public void deleteById(Long id) {
        if (!taskRepository.findById(id).isPresent()) {
            throw new TaskNotFoundException("Task not found with id: " + id);
        }
        taskRepository.deleteById(id);
    }

    public long getTotalCount() {
        return taskRepository.count();
    }
}