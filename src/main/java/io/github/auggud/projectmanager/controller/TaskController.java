package io.github.auggud.projectmanager.controller;

import io.github.auggud.projectmanager.dto.TaskDto;
import io.github.auggud.projectmanager.service.TaskService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/projects/{projectId}/tasks")
public class TaskController {

    private final TaskService taskService;

    public TaskController(TaskService taskService) {
        this.taskService = taskService;
    }

    @PostMapping
    public ResponseEntity<TaskDto> createTask(@RequestBody TaskDto dto,
                                              @PathVariable Long projectId) {
        return ResponseEntity.status(HttpStatus.CREATED).body(taskService.createTask(dto, projectId));
    }

    @GetMapping("/{requestedId}")
    public ResponseEntity<TaskDto> findTaskById(@PathVariable Long requestedId) {
        return ResponseEntity.ok(taskService.findTaskById(requestedId));
    }

    @GetMapping
    public ResponseEntity<Page<TaskDto>> findAllTasks(Pageable pageable) {
        return ResponseEntity.ok(taskService.findAllTasks(pageable));
    }

    @PutMapping("/{requestedId}")
    public ResponseEntity<TaskDto> updateTaskById(@PathVariable Long requestedId, @RequestBody TaskDto dto) {
        return ResponseEntity.ok(taskService.updateTaskById(requestedId, dto));
    }

    @DeleteMapping("/{requestedId}")
    public ResponseEntity<Void> deleteTaskById(@PathVariable Long requestedId) {
        taskService.deleteTaskById(requestedId);
        return ResponseEntity.noContent().build();
    }
}
