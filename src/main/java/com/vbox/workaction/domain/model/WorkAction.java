package com.vbox.workaction.domain.model;

import java.time.Instant;
import java.time.LocalDate;
import java.util.UUID;

public record WorkAction(UUID id, String referenceId, String title, String description, Status status,
                         Priority priority, LocalDate dueDate, String assignedTo, Instant createdAt,
                         Instant updatedAt) {
    public enum Status {OPEN, IN_PROGRESS, COMPLETED, CANCELLED}

    public enum Priority {LOW, MEDIUM, HIGH, CRITICAL}
}
