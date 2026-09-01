package com.vbox.workaction.api.dto;

import com.vbox.workaction.domain.model.WorkAction.Priority;
import com.vbox.workaction.domain.model.WorkAction.Status;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;

public record WorkActionSearchCriteria(String referenceId, String title, Status status, Priority priority,
                                       String assignedTo, @Min(0) Integer page, @Min(1) @Max(100) Integer size) {
    public int pageOrDefault() {
        return page == null ? 0 : page;
    }

    public int sizeOrDefault() {
        return size == null ? 20 : size;
    }
}
