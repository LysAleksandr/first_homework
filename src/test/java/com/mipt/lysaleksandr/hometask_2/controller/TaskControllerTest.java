package com.mipt.lysaleksandr.hometask_2.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mipt.lysaleksandr.hometask_2.dto.TaskCreateDto;
import com.mipt.lysaleksandr.hometask_2.dto.TaskResponseDto;
import com.mipt.lysaleksandr.hometask_2.mapper.TaskMapper;
import com.mipt.lysaleksandr.hometask_2.model.Priority;
import com.mipt.lysaleksandr.hometask_2.model.Task;
import com.mipt.lysaleksandr.hometask_2.service.TaskService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(TaskController.class)
class TaskControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private TaskService taskService;

    @MockBean
    private TaskMapper taskMapper;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void createTask_shouldReturn201() throws Exception {
        TaskCreateDto dto = new TaskCreateDto();
        dto.setTitle("Test");
        dto.setPriority(Priority.LOW);
        dto.setDueDate(LocalDate.now().plusDays(1));

        Task entity = new Task();
        Task saved = new Task();
        saved.setId(1L);
        TaskResponseDto responseDto = new TaskResponseDto();
        responseDto.setId(1L);

        when(taskMapper.toEntity(any())).thenReturn(entity);
        when(taskService.save(any())).thenReturn(saved);
        when(taskMapper.toResponseDto(any())).thenReturn(responseDto);

        mockMvc.perform(post("/api/v2/tasks")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(dto)))
            .andExpect(status().isCreated())
            .andExpect(jsonPath("$.id").value(1L));
    }

    @Test
    void createTask_invalidData_shouldReturn400() throws Exception {
        TaskCreateDto invalidDto = new TaskCreateDto();
        mockMvc.perform(post("/api/v2/tasks")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(invalidDto)))
            .andExpect(status().isBadRequest());
    }

    @Test
    void getAllTasks_shouldReturnTotalCountHeader() throws Exception {
        when(taskService.findAll()).thenReturn(List.of());
        when(taskService.getTotalCount()).thenReturn(5L);
        mockMvc.perform(get("/api/v2/tasks"))
            .andExpect(status().isOk())
            .andExpect(header().string("X-Total-Count", "5"));
    }
}