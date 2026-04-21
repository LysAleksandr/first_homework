package com.mipt.lysaleksandr.hometask_3.service;

import com.mipt.lysaleksandr.hometask_3.dto.PriorityCountDto;
import com.mipt.lysaleksandr.hometask_3.model.Priority;
import com.mipt.lysaleksandr.hometask_3.model.Task;
import com.mipt.lysaleksandr.hometask_3.repository.TaskRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@ActiveProfiles("test")
class TaskStatisticsJdbcServiceTest {

    @Autowired
    private TaskStatisticsJdbcService statisticsService;
    @Autowired
    private TaskRepository taskRepository;

    @Test
    void getTasksCountByPriority_shouldReturnCorrectCounts() {
        Task t1 = new Task("T1", "D1", LocalDate.now(), Priority.LOW);
        t1.setCreatedAt(LocalDateTime.now());
        t1.setLastModifiedAt(LocalDateTime.now());

        Task t2 = new Task("T2", "D2", LocalDate.now(), Priority.MEDIUM);
        t2.setCreatedAt(LocalDateTime.now());
        t2.setLastModifiedAt(LocalDateTime.now());

        Task t3 = new Task("T3", "D3", LocalDate.now(), Priority.MEDIUM);
        t3.setCreatedAt(LocalDateTime.now());
        t3.setLastModifiedAt(LocalDateTime.now());

        Task t4 = new Task("T4", "D4", LocalDate.now(), Priority.HIGH);
        t4.setCreatedAt(LocalDateTime.now());
        t4.setLastModifiedAt(LocalDateTime.now());

        taskRepository.saveAll(List.of(t1, t2, t3, t4));

        List<PriorityCountDto> counts = statisticsService.getTasksCountByPriority();
        assertThat(counts).hasSize(3);
        assertThat(counts.stream().filter(dto -> dto.getPriority().equals("LOW")).findFirst().get()
            .getCount()).isEqualTo(1);
        assertThat(
            counts.stream().filter(dto -> dto.getPriority().equals("MEDIUM")).findFirst().get()
                .getCount()).isEqualTo(2);
        assertThat(counts.stream().filter(dto -> dto.getPriority().equals("HIGH")).findFirst().get()
            .getCount()).isEqualTo(1);
    }
}