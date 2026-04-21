package com.mipt.lysaleksandr.hometask_3.repository;

import com.mipt.lysaleksandr.hometask_3.model.Priority;
import com.mipt.lysaleksandr.hometask_3.model.Task;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;
import java.time.LocalDate;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@ActiveProfiles("test")
class TaskRepositoryTest {

    @Autowired
    private TaskRepository taskRepository;

    @Test
    void findByCompletedAndPriority_shouldReturnCorrectTasks() {
        Task t1 = new Task("Task1", "Desc", LocalDate.now(), Priority.HIGH);
        t1.setCompleted(true);
        Task t2 = new Task("Task2", "Desc", LocalDate.now(), Priority.HIGH);
        t2.setCompleted(false);
        taskRepository.saveAll(List.of(t1, t2));
        List<Task> completedHigh = taskRepository.findByCompletedAndPriority(true, Priority.HIGH);
        assertThat(completedHigh).hasSize(1);
        assertThat(completedHigh.get(0).getTitle()).isEqualTo("Task1");
    }

    @Test
    void findTasksDueWithinNext7Days_shouldReturnTasks() {
        Task t1 = new Task("Task1", "Desc", LocalDate.now().plusDays(3), Priority.MEDIUM);
        Task t2 = new Task("Task2", "Desc", LocalDate.now().plusDays(10), Priority.MEDIUM);
        taskRepository.saveAll(List.of(t1, t2));
        List<Task> dueNextWeek = taskRepository.findTasksDueWithinNext7Days(LocalDate.now(),
            LocalDate.now().plusDays(7));
        assertThat(dueNextWeek).hasSize(1);
        assertThat(dueNextWeek.get(0).getTitle()).isEqualTo("Task1");
    }
}