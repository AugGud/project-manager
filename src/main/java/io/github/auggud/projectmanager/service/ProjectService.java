package io.github.auggud.projectmanager.service;

import io.github.auggud.projectmanager.dto.ProjectDto;
import io.github.auggud.projectmanager.entity.Project;
import io.github.auggud.projectmanager.repository.ProjectRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class ProjectService {

    private final ProjectRepository projectRepository;

    public ProjectService(ProjectRepository projectRepository) {
        this.projectRepository = projectRepository;
    }

    public ProjectDto createProject(ProjectDto dto) {
        Project project = Project.builder()
                .title(dto.title())
                .description(dto.description())
                .build();

        Project saved = projectRepository.save(project);

        return new ProjectDto(saved.getId(), saved.getTitle(), saved.getDescription());
    }

    public ProjectDto findProjectById(Long requestedId) {
         Project project = projectRepository.findById(requestedId)
                .orElseThrow(() -> new RuntimeException("User not found with id: " + requestedId));

         return new ProjectDto(
                 project.getId(),
                 project.getTitle(),
                 project.getDescription()
         );
    }

    public Page<ProjectDto> findAllProjects(Pageable pageable) {
        Page<Project> projects = projectRepository.findAll(pageable);

        return  projects.map(project ->
                new ProjectDto(
                        project.getId(),
                        project.getTitle(),
                        project.getDescription()
                ));
    }

    public ProjectDto updateProjectById(Long requestedId, ProjectDto dto) {
        Project project = projectRepository.findById(requestedId)
                .orElseThrow(() -> new RuntimeException("Project not found with id: " + requestedId));

        project.setTitle(dto.title());
        project.setDescription(dto.description());

        projectRepository.save(project);

        return new ProjectDto(
                project.getId(),
                project.getTitle(),
                project.getDescription()
        );
    }

    public void deleteProjectById(Long requestedId) {
        if (!projectRepository.existsById(requestedId)) {
            throw new RuntimeException("Project not found with id: " + requestedId);
        }
        projectRepository.deleteById(requestedId);
    }
}
