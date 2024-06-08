package com.uai.lppa.security.exception;

import org.springframework.http.HttpStatus;

public class ApiError {

    private String exception;
    private HttpStatus httpStatus;
    private String message;

    public ApiError(Exception ex, HttpStatus httpStatus) {
        super();
        this.exception = ex.getClass().getCanonicalName();
        this.httpStatus = httpStatus;
        this.message = ex.getMessage();
    }

    public HttpStatus getHttpStatus() {
        return httpStatus;
    }

    public void setHttpStatus(HttpStatus httpStatus) {
        this.httpStatus = httpStatus;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getException() {
        return exception;
    }

    public void setException(String exception) {
        this.exception = exception;
    }
}
