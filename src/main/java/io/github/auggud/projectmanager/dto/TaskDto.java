package io.github.auggud.projectmanager.dto;

public record TaskDto(
        Long id,
        String title,
        String description,
        boolean isCompleted
) {
}
