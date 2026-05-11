package com.example.gateway.client;

import com.example.gateway.dto.ProblemDetails;
import com.example.gateway.dto.TaskCreateRequest;
import com.example.gateway.dto.TaskDto;
import com.example.gateway.exception.ExternalApiException;
import com.example.gateway.exception.TaskNotFoundException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;
import java.io.IOException;
import java.time.Duration;
import java.util.List;

@Component
public class ExternalTasksClient {

    private static final Logger log = LoggerFactory.getLogger(ExternalTasksClient.class);
    private final RestClient restClient;
    private final ObjectMapper objectMapper;

    public ExternalTasksClient(@Value("${external.api.base-url}") String baseUrl,
        @Value("${external.api.connect-timeout}") Duration connectTimeout,
        @Value("${external.api.read-timeout}") Duration readTimeout,
        ObjectMapper objectMapper) {
        SimpleClientHttpRequestFactory factory = new SimpleClientHttpRequestFactory();
        factory.setConnectTimeout((int) connectTimeout.toMillis());
        factory.setReadTimeout((int) readTimeout.toMillis());

        this.restClient = RestClient.builder()
            .baseUrl(baseUrl)
            .requestFactory(factory)
            .defaultHeader(HttpHeaders.USER_AGENT, "TasksGateway/1.0")
            .defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
            .defaultHeader(HttpHeaders.ACCEPT, MediaType.APPLICATION_JSON_VALUE)
            .build();
        this.objectMapper = objectMapper;
    }

    public TaskDto createTask(TaskCreateRequest request) {
        ResponseEntity<TaskDto> response = restClient.post()
            .uri("/tasks")
            .body(request)
            .retrieve()
            .toEntity(TaskDto.class);
        if (response.getStatusCode().is2xxSuccessful()) {
            return response.getBody();
        }
        throw new ExternalApiException("Failed to create task");
    }

    public TaskDto getTask(Long id) {
        try {
            return restClient.get()
                .uri("/tasks/{id}", id)
                .retrieve()
                .onStatus(HttpStatusCode::is4xxClientError, (req, res) -> {
                    if (res.getStatusCode().value() == 404) {
                        try {
                            String body = new String(res.getBody().readAllBytes());
                            ProblemDetails pd = objectMapper.readValue(body, ProblemDetails.class);
                            throw new TaskNotFoundException(pd.detail());
                        } catch (IOException e) {
                            throw new TaskNotFoundException("Task not found");
                        }
                    }
                })
                .body(TaskDto.class);
        } catch (TaskNotFoundException e) {
            throw e;
        } catch (Exception e) {
            throw new ExternalApiException("External API error", e);
        }
    }

    public List<TaskDto> listTasks(Boolean completed, int limit) {
        return restClient.get()
            .uri(uriBuilder -> {
                var builder = uriBuilder.path("/tasks");
                if (completed != null) {
                    builder.queryParam("completed", completed);
                }
                builder.queryParam("limit", limit);
                return builder.build();
            })
            .retrieve()
            .body(new ParameterizedTypeReference<List<TaskDto>>() {
            });
    }

    public void deleteTask(Long id) {
        restClient.delete()
            .uri("/tasks/{id}", id)
            .retrieve()
            .toBodilessEntity();
    }
}