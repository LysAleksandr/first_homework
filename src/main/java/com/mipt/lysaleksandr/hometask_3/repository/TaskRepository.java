package com.mipt.lysaleksandr.hometask_3.repository;

import com.mipt.lysaleksandr.hometask_3.model.Priority;
import com.mipt.lysaleksandr.hometask_3.model.Task;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import java.time.LocalDate;
import java.util.List;

public interface TaskRepository extends JpaRepository<Task, Long> {

    List<Task> findByCompletedAndPriority(boolean completed, Priority priority);

    @Query("SELECT t FROM Task t WHERE t.dueDate BETWEEN :today AND :todayPlus7Days")
    List<Task> findTasksDueWithinNext7Days(@Param("today") LocalDate today,
        @Param("todayPlus7Days") LocalDate todayPlus7Days);

    @Query("SELECT DISTINCT t FROM Task t LEFT JOIN FETCH t.attachments")
    List<Task> findAllWithAttachments();
}