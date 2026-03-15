package com.mipt.lysaleksandr.todomanager;

import com.mipt.lysaleksandr.todomanager.service.TaskStatisticsService;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

@SpringBootApplication
public class TodoManagerApplication {

    public static void main(String[] args) {
        ConfigurableApplicationContext context = SpringApplication.run(TodoManagerApplication.class,
            args);

        TaskStatisticsService statisticsService = context.getBean(TaskStatisticsService.class);
        statisticsService.printStatistics();
    }
}