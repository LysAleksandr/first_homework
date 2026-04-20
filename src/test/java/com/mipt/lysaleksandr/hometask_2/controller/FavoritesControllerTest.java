package com.mipt.lysaleksandr.hometask_2.controller;

import com.mipt.lysaleksandr.hometask_2.mapper.TaskMapper;
import com.mipt.lysaleksandr.hometask_2.service.FavoritesService;
import com.mipt.lysaleksandr.hometask_2.service.TaskService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(FavoritesController.class)
class FavoritesControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private FavoritesService favoritesService;

    @MockBean
    private TaskService taskService;   // добавлен

    @MockBean
    private TaskMapper taskMapper;     // добавлен

    @Test
    void addFavorite_shouldReturn200() throws Exception {
        mockMvc.perform(post("/api/v2/favorites/1"))
            .andExpect(status().isOk());
    }
}