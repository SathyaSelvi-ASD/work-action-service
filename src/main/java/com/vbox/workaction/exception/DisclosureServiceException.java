package com.vbox.workaction.exception;

public class DisclosureServiceException extends RuntimeException {
    public DisclosureServiceException(String message) {
        super(message);
    }

    public DisclosureServiceException(String message, Throwable cause) {
        super(message, cause);
    }
}
