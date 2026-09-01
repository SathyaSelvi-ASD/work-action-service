package com.vbox.workaction.api.controller;

import com.vbox.workaction.api.dto.CreateWorkActionRequest;
import com.vbox.workaction.api.dto.WorkActionResponse;
import com.vbox.workaction.api.dto.WorkActionSearchCriteria;
import com.vbox.workaction.api.dto.response.ApiResponse;
import com.vbox.workaction.application.port.in.CreateWorkActionUseCase;
import com.vbox.workaction.application.port.in.SearchWorkActionUseCase;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/work-actions/v1")
public class WorkActionController {
    private final CreateWorkActionUseCase createUseCase;
    private final SearchWorkActionUseCase searchUseCase;

    public WorkActionController(CreateWorkActionUseCase createUseCase, SearchWorkActionUseCase searchUseCase) {
        this.createUseCase = createUseCase;
        this.searchUseCase = searchUseCase;
    }

    @PostMapping
    public ResponseEntity<ApiResponse> create(@Valid @RequestBody CreateWorkActionRequest request) {
        var result = WorkActionResponse.from(createUseCase.create(request));
        var response = new ApiResponse("success", 200, "Work action created successfully",
                null, null, result);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/search")
    public ResponseEntity<ApiResponse> search(@RequestParam("refId") String refId) {
        var criteria = new WorkActionSearchCriteria(refId, null, null, null, null, null, null);
        var searchResults = searchUseCase.search(criteria).map(WorkActionResponse::from);
        var response = new ApiResponse("success", 200, "Work actions retrieved successfully",
                null, null, searchResults);
        return ResponseEntity.ok(response);
    }
}
