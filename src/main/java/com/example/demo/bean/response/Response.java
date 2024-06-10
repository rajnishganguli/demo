package com.example.demo.bean.response;

import java.util.List;

public class Response {
    String status;
    String message;
    String accessToken;
    Object data;
    List<Object> errorDetails;

    public void setStatus(String status) {
        this.status = status;
    }

    public String getStatus() {
        return status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Object getData() {
        return data;
    }

    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

    public void setData(Object data) {
        this.data = data;
    }

    public List<Object> getErrorDetails() {
        return errorDetails;
    }

    public void setErrorDetails(List<Object> errorDetails) {
        this.errorDetails = errorDetails;
    }

    public Response() {
    }

    public Response(String status) {
        this.status = status;
    }

    public Response(String status, String message) {
        this.status = status;
        this.message = message;
    }

    public Response(String status, String message, Object data) {
        this.status = status;
        this.message = message;
        this.data = data;
    }

    public Response(String status, String message, List<Object> errorDetails) {
        this.status = status;
        this.message = message;
        this.errorDetails = errorDetails;
    }

    public Response(String status, String message, String token, Object data) {
        this.status = status;
        this.message = message;
        this.accessToken = token;
        this.data = data;
    }

    public Response(String status, String message, String token, Object data, List<Object> errorDetails) {
        this.status = status;
        this.message = message;
        this.accessToken = token;
        this.data = data;
        this.errorDetails = errorDetails;
    }
}
