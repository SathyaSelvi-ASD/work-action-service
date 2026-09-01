package com.vbox.workaction.application.service;

import com.vbox.workaction.api.dto.CreateWorkActionRequest;
import com.vbox.workaction.api.dto.WorkActionSearchCriteria;
import com.vbox.workaction.api.dto.response.ApiResponse;
import com.vbox.workaction.application.port.in.CreateWorkActionUseCase;
import com.vbox.workaction.application.port.in.SearchWorkActionUseCase;
import com.vbox.workaction.application.port.out.WorkActionRepositoryPort;
import com.vbox.workaction.config.DisclosureClient;
import com.vbox.workaction.domain.model.WorkAction;
import com.vbox.workaction.exception.DuplicateWorkActionException;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.Map;
import java.util.UUID;

@Service
@Transactional
public class WorkActionService implements CreateWorkActionUseCase, SearchWorkActionUseCase {
    private final WorkActionRepositoryPort repository;
    private final DisclosureClient disclosureClient;

    public WorkActionService(WorkActionRepositoryPort repository, DisclosureClient disclosureClient) {
        this.repository = repository;
        this.disclosureClient = disclosureClient;
    }

    @Override
    public WorkAction create(CreateWorkActionRequest r) {
        if (repository.existsByReferenceId(r.referenceId())) throw new DuplicateWorkActionException(r.referenceId());
        Instant now = Instant.now();
        WorkAction w = new WorkAction(UUID.randomUUID(), r.referenceId(), r.title(), r.description(),
                WorkAction.Status.OPEN, r.priority(), r.dueDate(), r.assignedTo(), now, now);
        return repository.save(w);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<WorkAction> search(WorkActionSearchCriteria criteria) {
        return repository.search(criteria);
    }

    @Override
    @Transactional(readOnly = true)
    public ApiResponse searchDisclosures(Map<String, Object> request) {
        return disclosureClient.search(request);
    }
}
