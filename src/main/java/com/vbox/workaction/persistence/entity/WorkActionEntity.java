package com.vbox.workaction.persistence.entity;

import com.vbox.workaction.domain.model.WorkAction.Priority;
import com.vbox.workaction.domain.model.WorkAction.Status;
import jakarta.persistence.*;

import java.time.Instant;
import java.time.LocalDate;
import java.util.UUID;

@Entity
@Table(name = "work_action", indexes = {@Index(name = "ix_work_action_status", columnList = "status"), @Index(name = "ix_work_action_assigned_to", columnList = "assigned_to")})
public class WorkActionEntity {
    @Id
    private UUID id;
    @Column(name = "reference_id", nullable = false, unique = true, length = 100)
    private String referenceId;
    @Column(nullable = false, length = 200)
    private String title;
    @Column(length = 2000)
    private String description;
    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 30)
    private Status status;
    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private Priority priority;
    @Column(name = "due_date")
    private LocalDate dueDate;
    @Column(name = "assigned_to", length = 150)
    private String assignedTo;
    @Column(name = "created_at", nullable = false)
    private Instant createdAt;
    @Column(name = "updated_at", nullable = false)
    private Instant updatedAt;

    protected WorkActionEntity() {
    }

    public WorkActionEntity(UUID id, String referenceId, String title, String description, Status status, Priority priority, LocalDate dueDate, String assignedTo, Instant createdAt, Instant updatedAt) {
        this.id = id;
        this.referenceId = referenceId;
        this.title = title;
        this.description = description;
        this.status = status;
        this.priority = priority;
        this.dueDate = dueDate;
        this.assignedTo = assignedTo;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    public UUID getId() {
        return id;
    }

    public String getReferenceId() {
        return referenceId;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public Status getStatus() {
        return status;
    }

    public Priority getPriority() {
        return priority;
    }

    public LocalDate getDueDate() {
        return dueDate;
    }

    public String getAssignedTo() {
        return assignedTo;
    }

    public Instant getCreatedAt() {
        return createdAt;
    }

    public Instant getUpdatedAt() {
        return updatedAt;
    }
}
