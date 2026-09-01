package com.vbox.workaction.api.dto.response;

import com.vbox.workaction.api.dto.WorkActionResponse;

import java.util.List;

public record SearchResponseDto(List<WorkActionResponse> items, int page, int pageSize,
                                long totalElements, int totalPages) {
}
