package com.mipt.lysaleksandr.hometask_2.mapper;

import com.mipt.lysaleksandr.hometask_2.dto.TaskCreateDto;
import com.mipt.lysaleksandr.hometask_2.dto.TaskUpdateDto;
import com.mipt.lysaleksandr.hometask_2.model.Priority;
import com.mipt.lysaleksandr.hometask_2.model.Task;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

import java.time.LocalDate;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

class TaskMapperTest {

    private final TaskMapper mapper = Mappers.getMapper(TaskMapper.class);

    @Test
    void toEntity_shouldMapCreateDto() {
        TaskCreateDto dto = new TaskCreateDto();
        dto.setTitle("Test");
        dto.setDescription("Desc");
        dto.setDueDate(LocalDate.now().plusDays(1));
        dto.setPriority(Priority.HIGH);
        dto.setTags(Set.of("tag1"));

        Task task = mapper.toEntity(dto);

        assertNull(task.getId());
        assertFalse(task.isCompleted());
        assertNotNull(task.getCreatedAt());
        assertEquals("Test", task.getTitle());
        assertEquals(Priority.HIGH, task.getPriority());
    }

    @Test
    void updateEntity_shouldUpdateOnlyNonNullFields() {
        Task task = new Task();
        task.setTitle("Old");
        TaskUpdateDto dto = new TaskUpdateDto();
        dto.setTitle("New");

        mapper.updateEntity(dto, task);

        assertEquals("New", task.getTitle());
        assertNull(task.getDescription());
    }
}