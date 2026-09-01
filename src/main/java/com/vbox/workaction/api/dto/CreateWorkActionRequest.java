package com.vbox.workaction.api.dto;

import com.vbox.workaction.domain.model.WorkAction.Priority;
import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.time.LocalDate;

public record CreateWorkActionRequest(
        @NotBlank @Size(max = 100) String referenceId,
        @NotBlank @Size(max = 200) String title,
        @Size(max = 2000) String description,
        @NotNull Priority priority,
        @FutureOrPresent LocalDate dueDate,
        @Size(max = 150) String assignedTo) {
}
