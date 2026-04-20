package com.mipt.lysaleksandr.hometask_2.repository;

import com.mipt.lysaleksandr.hometask_2.model.Priority;
import com.mipt.lysaleksandr.hometask_2.model.Task;
import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Repository;
import java.time.LocalDateTime;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

@Repository
public class InMemoryTaskRepository implements TaskRepository {

    private final Map<Long, Task> storage = new ConcurrentHashMap<>();
    private final AtomicLong idGenerator = new AtomicLong(1);

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
        if (task.getId() == null) {
            task.setId(idGenerator.getAndIncrement());
        }
        storage.put(task.getId(), task);
        return task;
    }

    @Override
    public void deleteById(Long id) {
        storage.remove(id);
    }

    @Override
    public long count() {
        return storage.size();
    }

    @PostConstruct
    public void init() {
        Task t1 = new Task(null, "Task 1", "First task", false,
            LocalDateTime.now(), LocalDateTime.now().toLocalDate().plusDays(1), Priority.LOW,
            Set.of("work"));
        Task t2 = new Task(null, "Task 2", "Second task", false,
            LocalDateTime.now(), LocalDateTime.now().toLocalDate().plusDays(2), Priority.MEDIUM,
            Set.of("personal"));
        save(t1);
        save(t2);
    }
}