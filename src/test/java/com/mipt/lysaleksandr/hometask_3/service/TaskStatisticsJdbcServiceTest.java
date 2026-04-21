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
        taskRepository.saveAll(List.of(
            new Task("T1", "D1", LocalDate.now(), Priority.LOW),
            new Task("T2", "D2", LocalDate.now(), Priority.MEDIUM),
            new Task("T3", "D3", LocalDate.now(), Priority.MEDIUM),
            new Task("T4", "D4", LocalDate.now(), Priority.HIGH)
        ));
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