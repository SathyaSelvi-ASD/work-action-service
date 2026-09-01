package com.vbox.workaction.exception;

import com.vbox.workaction.api.dto.response.ApiResponse;
import org.junit.jupiter.api.Test;
import org.springframework.core.MethodParameter;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;

import java.lang.reflect.Method;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

class GlobalExceptionHandlerTest {

    @Test
    void handlesDisclosurePersistenceException() {
        var handler = new GlobalExceptionHandler();
        var ex = new WorkActionPersistenceException("ERR-DISCLOSURE-PERSISTENCE", "Database write failed");

        var response = handler.handlePersistence(ex);
        ApiResponse body = response.getBody();

        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
        assertNotNull(body);
        assertEquals("error", body.status());
        assertEquals(500, body.statusCode());
        assertEquals("Failed to persist disclosure details", body.message());
        assertNotNull(body.errors());
        assertEquals(1, body.errors().size());
        assertEquals("ERR-DISCLOSURE-PERSISTENCE", body.errors().getFirst().code());
        assertEquals("Failed to persist disclosure details", body.errors().getFirst().message());
        assertNull(body.warnings());
        assertNull(body.data());
    }

    @Test
    void handlesDuplicateWorkActionExceptionAsApiResponseOnly() {
        var handler = new GlobalExceptionHandler();
        var referenceId = "REF-1001";
        var ex = new DuplicateWorkActionException(referenceId);

        var response = handler.duplicate(ex);
        ApiResponse body = response.getBody();

        assertEquals(HttpStatus.CONFLICT, response.getStatusCode());
        assertNotNull(body);
        assertEquals("error", body.status());
        assertEquals(409, body.statusCode());
        assertEquals("Duplicate work action", body.message());
        assertNotNull(body.errors());
        assertEquals(1, body.errors().size());
        assertEquals("ERR-WORK-ACTION-ALREADY-EXISTS", body.errors().getFirst().code());
        assertEquals("Work action already exists for referenceId: " + referenceId, body.errors().getFirst().message());
        assertNull(body.warnings());
        assertNull(body.data());
    }

    @Test
    void handlesValidationExceptionAsApiResponseOnly() throws NoSuchMethodException {
        var handler = new GlobalExceptionHandler();
        var bindingResult = new BeanPropertyBindingResult(new ValidationTarget(), "validationTarget");
        bindingResult.addError(new FieldError("validationTarget", "employeeId", "must not be blank"));
        bindingResult.addError(new FieldError("validationTarget", "countryCode", "must be 2 letters"));

        Method method = ValidationTarget.class.getDeclaredMethod("setEmployeeId", String.class);
        var parameter = new MethodParameter(method, 0);
        var ex = new MethodArgumentNotValidException(parameter, bindingResult);

        var response = handler.validation(ex);
        ApiResponse body = response.getBody();

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertNotNull(body);
        assertEquals("error", body.status());
        assertEquals(400, body.statusCode());
        assertEquals("Request validation failed", body.message());
        assertNotNull(body.errors());
        assertEquals(2, body.errors().size());
        assertEquals("ERR-WORK-ACTION-VALIDATION", body.errors().get(0).code());
        assertEquals("employeeId: must not be blank", body.errors().get(0).message());
        assertEquals("ERR-WORK-ACTION-VALIDATION", body.errors().get(1).code());
        assertEquals("countryCode: must be 2 letters", body.errors().get(1).message());
        assertNull(body.warnings());
        assertNull(body.data());
    }

    static class ValidationTarget {
        void setEmployeeId(String employeeId) {
        }
    }
}
