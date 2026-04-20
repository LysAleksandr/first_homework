package com.mipt.lysaleksandr.hometask_2.exception;

public class AttachmentNotFoundException extends RuntimeException {

    public AttachmentNotFoundException(String message) {
        super(message);
    }
}