package com.mipt.lysaleksandr.hometask_2.repository;

import com.mipt.lysaleksandr.hometask_2.model.Task;
import java.util.List;
import java.util.Optional;

public interface TaskRepository {

    List<Task> findAll();

    Optional<Task> findById(Long id);

    Task save(Task task);

    void deleteById(Long id);

    long count();
}