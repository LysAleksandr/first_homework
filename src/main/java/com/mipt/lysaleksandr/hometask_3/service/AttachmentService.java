package com.mipt.lysaleksandr.hometask_3.service;

import com.mipt.lysaleksandr.hometask_3.exception.AttachmentNotFoundException;
import com.mipt.lysaleksandr.hometask_3.exception.TaskNotFoundException;
import com.mipt.lysaleksandr.hometask_3.model.Task;
import com.mipt.lysaleksandr.hometask_3.model.TaskAttachment;
import com.mipt.lysaleksandr.hometask_3.repository.TaskAttachmentRepository;
import com.mipt.lysaleksandr.hometask_3.repository.TaskRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.List;
import java.util.UUID;

@Service
public class AttachmentService {

    private final TaskAttachmentRepository attachmentRepository;
    private final TaskRepository taskRepository;
    @Value("${file.upload-dir:uploads}")
    private String uploadDir;

    public AttachmentService(TaskAttachmentRepository attachmentRepository,
        TaskRepository taskRepository) {
        this.attachmentRepository = attachmentRepository;
        this.taskRepository = taskRepository;
    }

    @Transactional
    public TaskAttachment storeAttachment(Long taskId, MultipartFile file) throws IOException {
        Task task = taskRepository.findById(taskId)
            .orElseThrow(() -> new TaskNotFoundException("Task not found: " + taskId));
        Path uploadPath = Paths.get(uploadDir);
        if (!Files.exists(uploadPath)) {
            Files.createDirectories(uploadPath);
        }
        String storedFileName = UUID.randomUUID() + "_" + file.getOriginalFilename();
        Path filePath = uploadPath.resolve(storedFileName);
        try (InputStream is = file.getInputStream()) {
            Files.copy(is, filePath, StandardCopyOption.REPLACE_EXISTING);
        }
        TaskAttachment attachment = new TaskAttachment(task, file.getOriginalFilename(),
            storedFileName, file.getContentType(), file.getSize());
        return attachmentRepository.save(attachment);
    }

    @Transactional(readOnly = true)
    public TaskAttachment getAttachment(Long attachmentId) {
        return attachmentRepository.findById(attachmentId).orElseThrow(
            () -> new AttachmentNotFoundException("Attachment not found: " + attachmentId));
    }

    @Transactional(readOnly = true)
    public Resource loadAsResource(Long attachmentId) throws IOException {
        TaskAttachment attachment = getAttachment(attachmentId);
        Path filePath = Paths.get(uploadDir).resolve(attachment.getStoredFileName());
        Resource resource = new UrlResource(filePath.toUri());
        if (resource.exists() && resource.isReadable()) {
            return resource;
        } else {
            throw new RuntimeException("Could not read file: " + attachment.getStoredFileName());
        }
    }

    @Transactional
    public void deleteAttachment(Long attachmentId) throws IOException {
        TaskAttachment attachment = getAttachment(attachmentId);
        Path filePath = Paths.get(uploadDir).resolve(attachment.getStoredFileName());
        Files.deleteIfExists(filePath);
        attachmentRepository.deleteById(attachmentId);
    }

    @Transactional(readOnly = true)
    public List<TaskAttachment> getAttachmentsByTaskId(Long taskId) {
        return attachmentRepository.findByTaskId(taskId);
    }
}