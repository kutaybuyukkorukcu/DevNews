package com.scalx.devnews.utils;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
public class ErrorResponse {

    private int statusCode;
    private LocalDateTime timestamp;
    private StatusResponse status;
    private String message;

    public ErrorResponse(String message, LocalDateTime timestamp) {
        this.message = message;
        this.timestamp = timestamp;
    }

    public ErrorResponse(int statusCode, StatusResponse status, LocalDateTime timestamp) {
        this.statusCode = statusCode;
        this.status = status;
        this.timestamp = timestamp;
    }

    public ErrorResponse(int statusCode, StatusResponse status, String message, LocalDateTime timestamp) {
        this.statusCode = statusCode;
        this.status = status;
        this.message = message;
        this.timestamp = timestamp;
    }
}
