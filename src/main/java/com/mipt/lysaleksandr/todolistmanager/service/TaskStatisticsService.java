package com.mipt.lysaleksandr.todolistmanager.service;

import com.mipt.lysaleksandr.todolistmanager.repository.TaskRepository;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

@Service
public class TaskStatisticsService {

    private final TaskRepository primaryRepository;
    private final TaskRepository stubRepository;

    public TaskStatisticsService(
        TaskRepository primaryRepository,
        @Qualifier("stubTaskRepository") TaskRepository stubRepository) {
        this.primaryRepository = primaryRepository;
        this.stubRepository = stubRepository;
    }

    public void printStatistics() {
        System.out.println("Primary: " + primaryRepository.findAll().size());
        System.out.println("Stub: " + stubRepository.findAll().size());
    }
}