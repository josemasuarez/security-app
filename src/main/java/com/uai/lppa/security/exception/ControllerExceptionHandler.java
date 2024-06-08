package com.uai.lppa.security.exception;

import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
@Order(Ordered.HIGHEST_PRECEDENCE)
public class ControllerExceptionHandler {

    @ExceptionHandler(EntityNotFoundException.class)
    protected ResponseEntity<Object> handleEntityNotFoundException(final EntityNotFoundException exception) {
        final ApiError apiError = new ApiError(exception, HttpStatus.NOT_FOUND);
        return new ResponseEntity<>(apiError, new HttpHeaders(), apiError.getHttpStatus());
    }

    @ExceptionHandler(BadRequestException.class)
    protected ResponseEntity<ApiError> handleBadRequestException(final BadRequestException exception) {
        final ApiError apiError = new ApiError(exception, HttpStatus.BAD_REQUEST);
        return new ResponseEntity<>(apiError, new HttpHeaders(), apiError.getHttpStatus());
    }
}
