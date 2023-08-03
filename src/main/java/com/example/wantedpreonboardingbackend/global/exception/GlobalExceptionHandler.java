package com.example.wantedpreonboardingbackend.global.exception;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler
    public ResponseEntity<ErrorResponse> handleException(BusinessException e) {
        return ResponseEntity.status(e.getHttpStatus())
                .body(new ErrorResponse(e.getError(), e.getMessage()));
    }
}
