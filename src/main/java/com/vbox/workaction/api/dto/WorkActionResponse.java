package com.vbox.workaction.api.dto;

import com.vbox.workaction.domain.model.WorkAction;

import java.time.Instant;
import java.time.LocalDate;
import java.util.UUID;

public record WorkActionResponse(UUID id, String referenceId, String title, String description,
                                 WorkAction.Status status, WorkAction.Priority priority, LocalDate dueDate,
                                 String assignedTo,
                                 Instant createdAt, Instant updatedAt) {
    public static WorkActionResponse from(WorkAction w) {
        return new WorkActionResponse(w.id(), w.referenceId(), w.title(), w.description(), w.status(),
                w.priority(), w.dueDate(), w.assignedTo(), w.createdAt(), w.updatedAt());
    }
}
