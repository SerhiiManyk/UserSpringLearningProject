package com.manser.pr.exception;

public class TaskAlreadyExistException extends RuntimeException {

    public TaskAlreadyExistException(String message) {
        super(message);
    }

    public TaskAlreadyExistException() {
        super();
    }

    public TaskAlreadyExistException(String message, Throwable cause) {
        super(message, cause);
    }

    public TaskAlreadyExistException(Throwable cause) {
        super(cause);
    }
}
