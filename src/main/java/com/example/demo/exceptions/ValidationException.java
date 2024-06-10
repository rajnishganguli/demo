package com.example.demo.exceptions;

public class ValidationException extends RuntimeException {

    String message;

    public ValidationException() {
    }

    public ValidationException(String message) {
        super(message);
        this.message = message;
    }

    @Override
    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
