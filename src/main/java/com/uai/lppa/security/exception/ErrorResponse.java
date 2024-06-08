package com.uai.lppa.security.exception;

import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonIgnore;

import java.util.HashMap;
import java.util.Map;


public class ErrorResponse {
    private int status;
    private String message;
    private Map<String, Object> extras;

    public ErrorResponse(int status, String message, Map<String, Object> extras) {
        this.status = status;
        this.message = message;
        this.extras = extras;
    }

    public ErrorResponse(int status, String message) {
        this(status, message, new HashMap<>());
    }

    public ErrorResponse() {
    }

    public int getStatus() {
        return status;
    }

    public String getMessage() {
        return message;
    }

    @JsonAnyGetter
    public Map<String, Object> getExtras() {
        return extras;
    }

    @JsonIgnore
    public ErrorResponse putExtra(String key, Object value) {
        extras.put(key, value);
        return this;
    }
}
