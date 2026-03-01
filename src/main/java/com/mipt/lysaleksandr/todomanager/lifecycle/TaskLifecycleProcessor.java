package com.mipt.lysaleksandr.todomanager.lifecycle;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.stereotype.Component;

@Component
public class TaskLifecycleProcessor implements BeanPostProcessor {

    @Override
    public Object postProcessBeforeInitialization(Object bean, String beanName)
        throws BeansException {
        if (bean.getClass().getSimpleName().contains("TaskService") ||
            bean.getClass().getSimpleName().contains("TaskRepository")) {
            System.out.println("Creating: " + beanName);
        }
        return bean;
    }

    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName)
        throws BeansException {
        if (bean.getClass().getSimpleName().contains("TaskService") ||
            bean.getClass().getSimpleName().contains("TaskRepository")) {
            System.out.println("Initialized: " + beanName);
        }
        return bean;
    }
}