package com.vbox.workaction.exception;

import com.vbox.workaction.api.dto.response.ApiMessage;
import com.vbox.workaction.api.dto.response.ApiResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.List;

@RestControllerAdvice
public class GlobalExceptionHandler {
    private static final Logger log = LoggerFactory.getLogger(GlobalExceptionHandler.class);
    private static final String PERSISTENCE_ERROR_MESSAGE = "Failed to persist disclosure details";
    private static final String DUPLICATE_CODE = "ERR-WORK-ACTION-ALREADY-EXISTS";
    private static final String VALIDATION_CODE = "ERR-WORK-ACTION-VALIDATION";

    @ExceptionHandler(DuplicateWorkActionException.class)
    ResponseEntity<ApiResponse> duplicate(DuplicateWorkActionException e) {
        return build(HttpStatus.CONFLICT, "Duplicate work action",
                List.of(new ApiMessage(DUPLICATE_CODE, e.getMessage())));
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    ResponseEntity<ApiResponse> validation(MethodArgumentNotValidException e) {
        var errors = e.getBindingResult()
                .getFieldErrors()
                .stream()
                .map(x -> new ApiMessage(VALIDATION_CODE, x.getField() + ": " + x.getDefaultMessage()))
                .toList();

        return build(HttpStatus.BAD_REQUEST, "Request validation failed", errors);
    }

    @ExceptionHandler(WorkActionPersistenceException.class)
    ResponseEntity<ApiResponse> handlePersistence(WorkActionPersistenceException ex) {
        log.error("Handling disclosure persistence error code={}", ex.getErrorCode(), ex);
        return build(HttpStatus.INTERNAL_SERVER_ERROR, PERSISTENCE_ERROR_MESSAGE,
                List.of(new ApiMessage(ex.getErrorCode(), PERSISTENCE_ERROR_MESSAGE)));
    }

    private ResponseEntity<ApiResponse> build(HttpStatus status, String message, List<ApiMessage> errors) {
        var response = new ApiResponse("error", status.value(), message, errors, null, null);
        return ResponseEntity.status(status).body(response);
    }
}
