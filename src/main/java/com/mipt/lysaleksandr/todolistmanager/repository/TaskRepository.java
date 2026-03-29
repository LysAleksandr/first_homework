package com.mipt.lysaleksandr.todolistmanager.repository;

import com.mipt.lysaleksandr.todolistmanager.model.Task;
import java.util.List;
import java.util.Optional;

public interface TaskRepository {

    List<Task> findAll();

    Optional<Task> findById(Long id);

    Task save(Task task);

    void deleteById(Long id);
}