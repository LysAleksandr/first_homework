package com.mipt.lysaleksandr.hometask_3.service;

import com.mipt.lysaleksandr.hometask_3.exception.TaskNotFoundException;
import com.mipt.lysaleksandr.hometask_3.model.Priority;
import com.mipt.lysaleksandr.hometask_3.model.Task;
import com.mipt.lysaleksandr.hometask_3.repository.TaskRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;
import java.time.LocalDate;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
@ActiveProfiles("test")
@Transactional
class TaskServiceTest {

    @Autowired
    private TaskService taskService;
    @Autowired
    private TaskRepository taskRepository;

    @Test
    void bulkCompleteTasks_shouldRollbackOnMissingId() {
        Task task = taskService.createTask(new Task("Test", "Desc", LocalDate.now(), Priority.LOW));
        List<Long> ids = List.of(task.getId(), 999L);
        assertThrows(TaskNotFoundException.class, () -> taskService.bulkCompleteTasks(ids));
        Task unchanged = taskRepository.findById(task.getId()).orElseThrow();
        assertThat(unchanged.isCompleted()).isFalse();
    }

    @Test
    void bulkCompleteTasks_shouldCompleteAllWhenValid() {
        Task t1 = taskService.createTask(new Task("T1", "D1", LocalDate.now(), Priority.LOW));
        Task t2 = taskService.createTask(new Task("T2", "D2", LocalDate.now(), Priority.LOW));
        taskService.bulkCompleteTasks(List.of(t1.getId(), t2.getId()));
        assertThat(taskRepository.findById(t1.getId()).get().isCompleted()).isTrue();
        assertThat(taskRepository.findById(t2.getId()).get().isCompleted()).isTrue();
    }
}