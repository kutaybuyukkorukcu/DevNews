package com.scalx.devnews.utils;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class ErrorResponse {

    private LocalDateTime timestamp;
    private StatusResponse status;
    private int statusCode;
    private String message;

    public ErrorResponse(LocalDateTime timestamp, StatusResponse status, int statusCode) {
        this.timestamp = timestamp;
        this.status = status;
        this.statusCode = statusCode;
    }

    public ErrorResponse(LocalDateTime timestamp, StatusResponse status, int statusCode, String message) {
        this.timestamp = timestamp;
        this.status = status;
        this.statusCode = statusCode;
        this.message = message;
    }
}
