package com.mipt.lysaleksandr.hometask_2.exception;

public class TaskNotFoundException extends RuntimeException {

    public TaskNotFoundException(String message) {
        super(message);
    }
}