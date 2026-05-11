package com.example.gateway.exception;

import com.example.gateway.dto.ProblemDetails;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(TaskNotFoundException.class)
    public ResponseEntity<ProblemDetails> handleNotFound(TaskNotFoundException ex) {
        ProblemDetails pd = new ProblemDetails("about:blank", "Not Found", 404, ex.getMessage(),
            null);
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(pd);
    }

    @ExceptionHandler(ExternalApiException.class)
    public ResponseEntity<ProblemDetails> handleExternalError(ExternalApiException ex) {
        ProblemDetails pd = new ProblemDetails("about:blank", "External Service Error", 502,
            "External API failed: " + ex.getMessage(), null);
        return ResponseEntity.status(HttpStatus.BAD_GATEWAY).body(pd);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ProblemDetails> handleGeneric(Exception ex) {
        ProblemDetails pd = new ProblemDetails("about:blank", "Internal Server Error", 500,
            ex.getMessage(), null);
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(pd);
    }
}