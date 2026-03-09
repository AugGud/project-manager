package io.github.auggud.projectmanager.service;

import io.github.auggud.projectmanager.dto.TaskDto;
import io.github.auggud.projectmanager.entity.Project;
import io.github.auggud.projectmanager.entity.Task;
import io.github.auggud.projectmanager.repository.ProjectRepository;
import io.github.auggud.projectmanager.repository.TaskRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class TaskService {

    private final TaskRepository taskRepository;
    private final ProjectRepository projectRepository;

    public TaskService(TaskRepository taskRepository,
                       ProjectRepository projectRepository
    ) {
        this.taskRepository = taskRepository;
        this.projectRepository = projectRepository;
    }

    public TaskDto createTask(TaskDto dto, Long projectId) {
        Project project = projectRepository.findById(projectId)
                .orElseThrow(() -> new RuntimeException("Project not found with id: " + projectId));

        Task task = Task.builder()
                .project(project)
                .title(dto.title())
                .description(dto.description())
                .isCompleted(dto.isCompleted())
                .build();

        Task saved = taskRepository.save(task);

        return new TaskDto(
                saved.getId(),
                saved.getTitle(),
                saved.getDescription(),
                saved.isCompleted()
        );
    }

    public TaskDto findTaskById(Long requestedId) {
        Task task = taskRepository.findById(requestedId)
                .orElseThrow(() -> new RuntimeException("Task not found with id: " + requestedId));

        return new TaskDto(
                task.getId(),
                task.getTitle(),
                task.getDescription(),
                task.isCompleted()
        );
    }

    public Page<TaskDto> findAllTasks(Pageable pageable) {
        Page<Task> tasks = taskRepository.findAll(pageable);

        return tasks.map(task ->
                new TaskDto(
                    task.getId(),
                    task.getTitle(),
                    task.getDescription(),
                    task.isCompleted()
                )
        );
    }

    public TaskDto updateTaskById(Long requestedId, TaskDto dto) {
        Task task = taskRepository.findById(requestedId)
                .orElseThrow(() -> new RuntimeException("Task not found with id: " + requestedId));

        task.setTitle(dto.title());
        task.setDescription(dto.description());
        task.setCompleted(dto.isCompleted());

        taskRepository.save(task);

        return new TaskDto(
                task.getId(),
                task.getTitle(),
                task.getDescription(),
                task.isCompleted()
        );
    }

    public void deleteTaskById(Long requestedId) {
        if (!taskRepository.existsById(requestedId)) {
            throw new RuntimeException("Task not found with id: " + requestedId);
        }
        taskRepository.deleteById(requestedId);
    }
}
