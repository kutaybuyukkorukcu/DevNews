package com.scalx.devnews.utils;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.Date;

@Data
@NoArgsConstructor
public class ErrorResponse {

    private int statusCode;
    private Date date;
    private String message;

    public ErrorResponse(String message, Date date) {
        this.message = message;
        this.date = date;
    }

    public ErrorResponse(int statusCode, Date date) {
        this.statusCode = statusCode;
        this.date = date;
    }

    public ErrorResponse(int statusCode, String message, Date date) {
        this.statusCode = statusCode;
        this.message = message;
        this.date = date;
    }
}
