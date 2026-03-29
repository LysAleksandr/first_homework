package com.mipt.lysaleksandr.todolistmanager;

import com.mipt.lysaleksandr.todolistmanager.service.TaskStatisticsService;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

@SpringBootApplication
@EnableAspectJAutoProxy
public class TodoListManagerApplication {

    public static void main(String[] args) {
        ConfigurableApplicationContext context = SpringApplication.run(
            TodoListManagerApplication.class, args);

        TaskStatisticsService statisticsService = context.getBean(TaskStatisticsService.class);
        statisticsService.printStatistics();
    }
}