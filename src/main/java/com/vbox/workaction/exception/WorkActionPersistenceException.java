package com.vbox.workaction.exception;

public class WorkActionPersistenceException extends RuntimeException {
    private final String errorCode;

    public WorkActionPersistenceException(String errorCode, String message) {
        super(message);
        this.errorCode = errorCode;
    }

    public WorkActionPersistenceException(String errorCode, String message, Throwable cause) {
        super(message, cause);
        this.errorCode = errorCode;
    }

    public String getErrorCode() {
        return errorCode;
    }
}
