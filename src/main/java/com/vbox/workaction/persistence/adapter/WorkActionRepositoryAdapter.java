package com.vbox.workaction.persistence.adapter;

import com.vbox.workaction.api.dto.WorkActionSearchCriteria;
import com.vbox.workaction.application.port.out.WorkActionRepositoryPort;
import com.vbox.workaction.domain.model.WorkAction;
import com.vbox.workaction.persistence.entity.WorkActionEntity;
import com.vbox.workaction.persistence.mapper.WorkActionPersistenceMapper;
import com.vbox.workaction.persistence.repository.SpringDataWorkActionRepository;
import jakarta.persistence.criteria.Predicate;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;

@Repository
public class WorkActionRepositoryAdapter implements WorkActionRepositoryPort {
    private final SpringDataWorkActionRepository repository;

    public WorkActionRepositoryAdapter(SpringDataWorkActionRepository repository) {
        this.repository = repository;
    }

    private static boolean has(String v) {
        return v != null && !v.isBlank();
    }

    public WorkAction save(WorkAction w) {
        return WorkActionPersistenceMapper.toDomain(repository.save(WorkActionPersistenceMapper.toEntity(w)));
    }

    public boolean existsByReferenceId(String referenceId) {
        return repository.existsByReferenceId(referenceId);
    }

    public Page<WorkAction> search(WorkActionSearchCriteria c) {
        Specification<WorkActionEntity> spec = (root, q, cb) -> {
            var p = new ArrayList<Predicate>();
            if (has(c.referenceId())) p.add(cb.equal(root.get("referenceId"), c.referenceId()));
            if (has(c.title())) p.add(cb.like(cb.lower(root.get("title")), "%" + c.title().toLowerCase() + "%"));
            if (c.status() != null) p.add(cb.equal(root.get("status"), c.status()));
            if (c.priority() != null) p.add(cb.equal(root.get("priority"), c.priority()));
            if (has(c.assignedTo())) p.add(cb.equal(root.get("assignedTo"), c.assignedTo()));
            return cb.and(p.toArray(Predicate[]::new));
        };
        return repository.findAll(spec, PageRequest.of(c.pageOrDefault(), c.sizeOrDefault(), Sort.by(Sort.Direction.DESC, "createdAt"))).map(WorkActionPersistenceMapper::toDomain);
    }
}
