package com.scalx.devnews.utils;

import com.fasterxml.jackson.databind.JsonNode;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class StandardResponse {

    private StatusResponse status;
    private int statusCode;
    private String message;
    private LocalDateTime timestamp;
    private JsonNode data;

    public StandardResponse(LocalDateTime timestamp, StatusResponse status, int statusCode) {
        this.timestamp = timestamp;
        this.status = status;
        this.statusCode = statusCode;
    }

    public StandardResponse(LocalDateTime timestamp, StatusResponse status, int statusCode, String message) {
        this.timestamp = timestamp;
        this.status = status;
        this.statusCode = statusCode;
        this.message = message;
    }

    public StandardResponse(LocalDateTime timestamp, StatusResponse status, int statusCode, String message, JsonNode data) {
        this.timestamp = timestamp;
        this.status = status;
        this.statusCode = statusCode;
        this.message = message;
        this.data = data;
    }
}