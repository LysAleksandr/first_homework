package com.mipt.lysaleksandr.hometask_2.controller;

import com.mipt.lysaleksandr.hometask_2.dto.AttachmentResponseDto;
import com.mipt.lysaleksandr.hometask_2.model.TaskAttachment;
import com.mipt.lysaleksandr.hometask_2.service.AttachmentService;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v2")
public class AttachmentController {

    private final AttachmentService attachmentService;

    public AttachmentController(AttachmentService attachmentService) {
        this.attachmentService = attachmentService;
    }

    @Operation(summary = "Upload attachment for task")
    @PostMapping("/tasks/{taskId}/attachments")
    public ResponseEntity<AttachmentResponseDto> uploadAttachment(@PathVariable Long taskId,
        @RequestParam("file") MultipartFile file) throws IOException {
        TaskAttachment attachment = attachmentService.storeAttachment(taskId, file);
        return ResponseEntity.status(201).body(toDto(attachment));
    }

    @Operation(summary = "Download attachment")
    @GetMapping("/attachments/{attachmentId}")
    public ResponseEntity<Resource> downloadAttachment(@PathVariable Long attachmentId)
        throws IOException {
        TaskAttachment attachment = attachmentService.getAttachment(attachmentId);
        Resource resource = attachmentService.loadAsResource(attachmentId);
        return ResponseEntity.ok()
            .contentType(MediaType.parseMediaType(attachment.getContentType()))
            .header(HttpHeaders.CONTENT_DISPOSITION,
                "attachment; filename=\"" + attachment.getFileName() + "\"")
            .body(resource);
    }

    @Operation(summary = "Delete attachment")
    @DeleteMapping("/attachments/{attachmentId}")
    public ResponseEntity<Void> deleteAttachment(@PathVariable Long attachmentId)
        throws IOException {
        attachmentService.deleteAttachment(attachmentId);
        return ResponseEntity.noContent().build();
    }

    @Operation(summary = "List attachments of a task")
    @GetMapping("/tasks/{taskId}/attachments")
    public ResponseEntity<List<AttachmentResponseDto>> getAttachments(@PathVariable Long taskId) {
        List<AttachmentResponseDto> dtos = attachmentService.getAttachmentsByTaskId(taskId).stream()
            .map(this::toDto)
            .collect(Collectors.toList());
        return ResponseEntity.ok(dtos);
    }

    private AttachmentResponseDto toDto(TaskAttachment attachment) {
        AttachmentResponseDto dto = new AttachmentResponseDto();
        dto.setId(attachment.getId());
        dto.setFileName(attachment.getFileName());
        dto.setSize(attachment.getSize());
        dto.setUploadedAt(attachment.getUploadedAt());
        return dto;
    }
}