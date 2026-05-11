package com.example.gateway.api;

import com.example.gateway.dto.ProblemDetails;
import com.example.gateway.dto.TaskCreateRequest;
import com.example.gateway.dto.TaskDto;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.net.URI;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/external/v1")
public class ExternalApiController {

    private final ConcurrentHashMap<Long, TaskDto> storage = new ConcurrentHashMap<>();
    private final AtomicLong idGenerator = new AtomicLong(1);

    @PostMapping("/tasks")
    public ResponseEntity<TaskDto> createTask(@RequestBody TaskCreateRequest request,
        HttpServletResponse response) {
        long id = idGenerator.getAndIncrement();
        TaskDto task = new TaskDto(id, request.title(), request.completed());
        storage.put(id, task);
        return ResponseEntity.created(URI.create("/external/v1/tasks/" + id)).body(task);
    }

    @GetMapping("/tasks/{id}")
    public ResponseEntity<TaskDto> getTask(@PathVariable Long id) {
        TaskDto task = storage.get(id);
        if (task == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        return ResponseEntity.ok(task);
    }

    @GetMapping("/tasks")
    public List<TaskDto> listTasks(@RequestParam(required = false) Boolean completed,
        @RequestParam(defaultValue = "10") int limit) {
        return storage.values().stream()
            .filter(t -> completed == null || t.completed() == completed)
            .limit(limit)
            .collect(Collectors.toList());
    }

    @DeleteMapping("/tasks/{id}")
    public ResponseEntity<Void> deleteTask(@PathVariable Long id) {
        if (storage.remove(id) == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/unstable")
    public ResponseEntity<String> unstable(@RequestParam String mode, HttpServletResponse response)
        throws InterruptedException {
        switch (mode) {
            case "timeout":
                Thread.sleep(5000);
                return ResponseEntity.ok("OK");
            case "500":
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Server Error");
            case "429":
                return ResponseEntity.status(HttpStatus.TOO_MANY_REQUESTS)
                    .header("Retry-After", "10")
                    .body("Too Many Requests");
            case "html":
                return ResponseEntity.status(HttpStatus.BAD_GATEWAY)
                    .header("Content-Type", "text/html")
                    .body("<html>Error</html>");
            default:
                return ResponseEntity.badRequest().body("Unknown mode");
        }
    }
}