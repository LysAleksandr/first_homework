package com.mipt.lysaleksandr.hometask_3.service;

import com.mipt.lysaleksandr.hometask_3.exception.TaskNotFoundException;
import com.mipt.lysaleksandr.hometask_3.model.Priority;
import com.mipt.lysaleksandr.hometask_3.model.Task;
import com.mipt.lysaleksandr.hometask_3.repository.TaskRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.time.LocalDate;
import java.util.List;

@Service
public class TaskService {

    private final TaskRepository taskRepository;

    public TaskService(TaskRepository taskRepository) {
        this.taskRepository = taskRepository;
    }

    @Transactional
    public Task createTask(Task task) {
        return taskRepository.save(task);
    }

    @Transactional(readOnly = true)
    public Task getTask(Long id) {
        return taskRepository.findById(id)
            .orElseThrow(() -> new TaskNotFoundException("Task not found with id: " + id));
    }

    @Transactional(readOnly = true)
    public List<Task> getAllTasks() {
        return taskRepository.findAll();
    }

    @Transactional(readOnly = true)
    public List<Task> getAllTasksWithAttachments() {
        return taskRepository.findAllWithAttachments();
    }

    @Transactional
    public Task updateTask(Task task) {
        if (!taskRepository.existsById(task.getId())) {
            throw new TaskNotFoundException("Task not found with id: " + task.getId());
        }
        return taskRepository.save(task);
    }

    @Transactional
    public void deleteTask(Long id) {
        if (!taskRepository.existsById(id)) {
            throw new TaskNotFoundException("Task not found with id: " + id);
        }
        taskRepository.deleteById(id);
    }

    @Transactional(readOnly = true)
    public List<Task> getTasksByCompletedAndPriority(boolean completed, String priority) {
        return taskRepository.findByCompletedAndPriority(completed, Priority.valueOf(priority));
    }

    @Transactional(readOnly = true)
    public List<Task> getTasksDueWithinNext7Days() {
        LocalDate today = LocalDate.now();
        return taskRepository.findTasksDueWithinNext7Days(today, today.plusDays(7));
    }

    @Transactional(rollbackFor = Exception.class)
    public void bulkCompleteTasks(List<Long> ids) {
        List<Task> tasks = taskRepository.findAllById(ids);
        if (tasks.size() != ids.size()) {
            throw new TaskNotFoundException(
                "One or more tasks not found. Transaction rolled back.");
        }
        for (Task task : tasks) {
            task.setCompleted(true);
            taskRepository.save(task);
        }
    }

    @Transactional(readOnly = true)
    public long getTotalCount() {
        return taskRepository.count();
    }
}