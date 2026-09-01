package com.vbox.workaction.application.port.in;

import com.vbox.workaction.api.dto.WorkActionSearchCriteria;
import com.vbox.workaction.domain.model.WorkAction;
import org.springframework.data.domain.Page;

public interface SearchWorkActionUseCase {
    Page<WorkAction> search(WorkActionSearchCriteria criteria);

    com.vbox.workaction.api.dto.response.ApiResponse searchDisclosures(java.util.Map<String, Object> request);
}
