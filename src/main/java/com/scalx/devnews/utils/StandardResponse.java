package com.scalx.devnews.utils;

import com.fasterxml.jackson.databind.JsonNode;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
public class StandardResponse {

    private int statusCode;
    private StatusResponse status;
    private String message;
    private LocalDateTime timestamp;
    private JsonNode data;

    public StandardResponse(String message, LocalDateTime timestamp) {
        this.message = message;
        this.timestamp = timestamp;
    }

    public StandardResponse(int statusCode, StatusResponse status, LocalDateTime timestamp) {
        this.statusCode = statusCode;
        this.status = status;
        this.timestamp = timestamp;
    }

    public StandardResponse(int statusCode, StatusResponse status, String message, LocalDateTime timestamp) {
        this.statusCode = statusCode;
        this.status = status;
        this.message = message;
        this.timestamp = timestamp;
    }

    public StandardResponse(int statusCode, StatusResponse status, String message, LocalDateTime timestamp, JsonNode data) {
        this.statusCode = statusCode;
        this.status = status;
        this.message = message;
        this.timestamp = timestamp;
        this.data = data;
    }
}