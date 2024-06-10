package com.example.demo.bean;

import java.time.LocalDateTime;

public class ErrorDetails {
    String errorMessage;
    LocalDateTime date;
    Object details;

    public ErrorDetails() {
    }

    public ErrorDetails(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    public ErrorDetails(String errorMessage, LocalDateTime dateTime) {
        this.errorMessage = errorMessage;
        this.date = dateTime;
    }

    public ErrorDetails(String errorMessage, LocalDateTime date, Object details) {
        this.errorMessage = errorMessage;
        this.date = date;
        this.details = details;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    public LocalDateTime getDate() {
        return date;
    }

    public void setDate(LocalDateTime date) {
        this.date = date;
    }

    public Object getDetails() {
        return details;
    }

    public void setDetails(Object details) {
        this.details = details;
    }
}
