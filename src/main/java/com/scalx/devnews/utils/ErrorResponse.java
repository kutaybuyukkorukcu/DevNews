package com.scalx.devnews.utils;

import lombok.Data;

@Data
public class ErrorResponse {

    private StatusResponse status;
    private int statusCode;
    private String message;

    public ErrorResponse(StatusResponse status, int statusCode) {
        this.status = status;
        this.statusCode = statusCode;
    }

    public ErrorResponse(StatusResponse status, int statusCode, String message) {
        this.status = status;
        this.statusCode = statusCode;
        this.message = message;
    }
}
