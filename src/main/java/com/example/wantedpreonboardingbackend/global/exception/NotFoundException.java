package com.example.wantedpreonboardingbackend.global.exception;

import org.springframework.http.HttpStatus;

public class NotFoundException extends BusinessException {
    public NotFoundException(ErrorMessage message, HttpStatus httpStatus) {
        super(message, httpStatus);
    }
}
