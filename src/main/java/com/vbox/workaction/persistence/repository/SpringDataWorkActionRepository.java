package com.vbox.workaction.persistence.repository;

import com.vbox.workaction.persistence.entity.WorkActionEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.UUID;

public interface SpringDataWorkActionRepository extends JpaRepository<WorkActionEntity, UUID>, JpaSpecificationExecutor<WorkActionEntity> {
    boolean existsByReferenceId(String referenceId);
}
