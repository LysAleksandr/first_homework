package com.example.gateway.dto;

public record ProblemDetails(String type, String title, int status, String detail,
                             String instance) {

}