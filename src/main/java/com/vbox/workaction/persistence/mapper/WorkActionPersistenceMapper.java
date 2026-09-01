package com.vbox.workaction.persistence.mapper;

import com.vbox.workaction.domain.model.WorkAction;
import com.vbox.workaction.persistence.entity.WorkActionEntity;

public final class WorkActionPersistenceMapper {
    private WorkActionPersistenceMapper() {
    }

    public static WorkActionEntity toEntity(WorkAction w) {
        return new WorkActionEntity(w.id(), w.referenceId(), w.title(), w.description(), w.status(), w.priority(), w.dueDate(), w.assignedTo(), w.createdAt(), w.updatedAt());
    }

    public static WorkAction toDomain(WorkActionEntity e) {
        return new WorkAction(e.getId(), e.getReferenceId(), e.getTitle(), e.getDescription(), e.getStatus(), e.getPriority(), e.getDueDate(), e.getAssignedTo(), e.getCreatedAt(), e.getUpdatedAt());
    }
}
