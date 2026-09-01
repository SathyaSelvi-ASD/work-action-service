package com.vbox.workaction.exception;

public class DuplicateWorkActionException extends RuntimeException {
    public DuplicateWorkActionException(String referenceId) {
        super("Work action already exists for referenceId: " + referenceId);
    }
}
