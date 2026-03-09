package io.github.auggud.projectmanager.controller;

import io.github.auggud.projectmanager.dto.ProjectDto;
import io.github.auggud.projectmanager.service.ProjectService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/projects")
public class ProjectController {

    private final ProjectService projectService;

    public ProjectController(ProjectService projectService) {
        this.projectService = projectService;
    }

    @PostMapping
    public ResponseEntity<ProjectDto> createProject(@RequestBody ProjectDto dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(projectService.createProject(dto));
    }

    @GetMapping("/{requestedId}")
    public ResponseEntity<ProjectDto> findProjectById(@PathVariable Long requestedId) {
        return ResponseEntity.ok(projectService.findProjectById(requestedId));
    }

    @GetMapping
    public ResponseEntity<Page<ProjectDto>> getAllProjects(Pageable pageable) {
        return ResponseEntity.ok(projectService.findAllProjects(pageable));
    }

    @PutMapping("/{requestedId}")
    public ResponseEntity<ProjectDto> updateProjectById(@PathVariable Long requestedId, @RequestBody ProjectDto dto) {
        return ResponseEntity.ok(projectService.updateProjectById(requestedId, dto));
    }

    @DeleteMapping("/{requestedId}")
    public ResponseEntity<Void> deleteProjectById(@PathVariable Long requestedId) {
        projectService.deleteProjectById(requestedId);
        return ResponseEntity
                .noContent()
                .build();
    }
}
