package com.mipt.lysaleksandr.todolistmanager.repository;

import com.mipt.lysaleksandr.todolistmanager.model.Task;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Repository;
import jakarta.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

@Repository
@Primary
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

    @PostConstruct
    public void init() {
        save(new Task(null, "Task 1", "Description 1", false));
        save(new Task(null, "Task 2", "Description 2", false));
    }
}
