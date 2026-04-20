package com.mipt.lysaleksandr.hometask_2.controller;

import com.mipt.lysaleksandr.hometask_2.service.AttachmentService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.multipart;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(AttachmentController.class)
class AttachmentControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private AttachmentService attachmentService;

    @Test
    void uploadAttachment_shouldReturn201() throws Exception {
        MockMultipartFile file = new MockMultipartFile("file", "test.txt", "text/plain",
            "hello".getBytes());
        mockMvc.perform(multipart("/api/v2/tasks/1/attachments").file(file))
            .andExpect(status().isCreated());
    }
}