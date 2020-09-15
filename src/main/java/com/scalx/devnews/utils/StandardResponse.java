package com.scalx.devnews.utils;

import com.fasterxml.jackson.databind.JsonNode;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.Date;

@Data
@NoArgsConstructor
public class StandardResponse {

    private int statusCode;
    private String message;
    private Date date;
    private JsonNode data;

    public StandardResponse(String message, Date date) {
        this.message = message;
        this.date = date;
    }

    public StandardResponse(int statusCode, Date date) {
        this.statusCode = statusCode;
        this.date = date;
    }

    public StandardResponse(int statusCode, String message, Date date) {
        this.statusCode = statusCode;
        this.message = message;
        this.date = date;
    }

    public StandardResponse(int statusCode, String message, Date date, JsonNode data) {
        this.statusCode = statusCode;
        this.message = message;
        this.date = date;
        this.data = data;
    }
}