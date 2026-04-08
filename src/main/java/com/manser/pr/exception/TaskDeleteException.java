package com.manser.pr.exception;

public class TaskDeleteException extends RuntimeException {
    public TaskDeleteException(String message) {
        super(message);
    }

    public TaskDeleteException(String message, Throwable cause) {
        super(message, cause);
    }
}
