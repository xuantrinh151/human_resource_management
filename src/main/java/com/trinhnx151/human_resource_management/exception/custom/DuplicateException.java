package com.trinhnx151.human_resource_management.exception.custom;

public class DuplicateException extends RuntimeException {
    public DuplicateException() {
        super();
    }
    public DuplicateException(String message) {
        super(message);
    }
}
