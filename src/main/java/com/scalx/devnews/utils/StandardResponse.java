package com.scalx.devnews.utils;

import com.fasterxml.jackson.databind.JsonNode;
import lombok.Data;

@Data
public class StandardResponse {
    private StatusResponse status;
    private int statusCode;
    private String message;
    private JsonNode data;

    public StandardResponse(StatusResponse status, int statusCode) {
        this.status = status;
        this.statusCode = statusCode;
    }

    public StandardResponse(StatusResponse status, int statusCode, String message) {
        this.status = status;
        this.statusCode = statusCode;
        this.message = message;
    }

    public StandardResponse(StatusResponse status, int statusCode, String message, JsonNode data) {
        this.status = status;
        this.statusCode = statusCode;
        this.message = message;
        this.data = data;
    }
}