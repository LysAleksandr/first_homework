package com.mipt.lysaleksandr.todolistmanager.repository;

import com.mipt.lysaleksandr.todolistmanager.model.Task;
import org.springframework.stereotype.Repository;
import java.util.*;

@Repository
public class StubTaskRepository implements TaskRepository {

    private final Map<Long, Task> storage = new HashMap<>();

    public StubTaskRepository() {
        storage.put(1L, new Task(1L, "Stub 1", "Stub Description 1", false));
        storage.put(2L, new Task(2L, "Stub 2", "Stub Description 2", true));
    }

    @Override
    public List<Task> findAll() {
        return new ArrayList<>(storage.values());
    }

    @Override
    public Optional<Task> findById(Long id) {
        return Optional.ofNullable(storage.get(id));
    }

    @Override
    public Task save(Task task) {
        return task;
    }

    @Override
    public void deleteById(Long id) {
    }
}