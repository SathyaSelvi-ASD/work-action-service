package com.vbox.workaction.application.port.out;

import com.vbox.workaction.api.dto.WorkActionSearchCriteria;
import com.vbox.workaction.domain.model.WorkAction;
import org.springframework.data.domain.Page;

public interface WorkActionRepositoryPort {
    WorkAction save(WorkAction workAction);

    boolean existsByReferenceId(String referenceId);

    Page<WorkAction> search(WorkActionSearchCriteria criteria);
}
