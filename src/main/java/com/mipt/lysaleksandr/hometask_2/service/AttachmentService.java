package com.mipt.lysaleksandr.hometask_2.service;

import com.mipt.lysaleksandr.hometask_2.exception.AttachmentNotFoundException;
import com.mipt.lysaleksandr.hometask_2.model.TaskAttachment;
import com.mipt.lysaleksandr.hometask_2.repository.TaskAttachmentRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Service
public class AttachmentService {

    private final TaskAttachmentRepository attachmentRepository;

    @Value("${file.upload-dir:uploads}")
    private String uploadDir;

    public AttachmentService(TaskAttachmentRepository attachmentRepository) {
        this.attachmentRepository = attachmentRepository;
    }

    public TaskAttachment storeAttachment(Long taskId, MultipartFile file) throws IOException {
        Path uploadPath = Paths.get(uploadDir);
        if (!Files.exists(uploadPath)) {
            Files.createDirectories(uploadPath);
        }

        String originalFilename = file.getOriginalFilename();
        String storedFileName = UUID.randomUUID() + "_" + originalFilename;
        Path filePath = uploadPath.resolve(storedFileName);

        try (InputStream inputStream = file.getInputStream()) {
            Files.copy(inputStream, filePath, StandardCopyOption.REPLACE_EXISTING);
        }

        TaskAttachment attachment = new TaskAttachment();
        attachment.setTaskId(taskId);
        attachment.setFileName(originalFilename);
        attachment.setStoredFileName(storedFileName);
        attachment.setContentType(file.getContentType());
        attachment.setSize(file.getSize());
        attachment.setUploadedAt(LocalDateTime.now());

        return attachmentRepository.save(attachment);
    }

    public TaskAttachment getAttachment(Long attachmentId) {
        return attachmentRepository.findById(attachmentId)
            .orElseThrow(() -> new AttachmentNotFoundException(
                "Attachment not found with id: " + attachmentId));
    }

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

    public void deleteAttachment(Long attachmentId) throws IOException {
        TaskAttachment attachment = getAttachment(attachmentId);
        Path filePath = Paths.get(uploadDir).resolve(attachment.getStoredFileName());
        Files.deleteIfExists(filePath);
        attachmentRepository.deleteById(attachmentId);
    }

    public List<TaskAttachment> getAttachmentsByTaskId(Long taskId) {
        return attachmentRepository.findByTaskId(taskId);
    }
}