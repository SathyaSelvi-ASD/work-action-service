package com.vbox.workaction.application.port.in;

import com.vbox.workaction.api.dto.CreateWorkActionRequest;
import com.vbox.workaction.domain.model.WorkAction;

public interface CreateWorkActionUseCase {
    WorkAction create(CreateWorkActionRequest request);
}
