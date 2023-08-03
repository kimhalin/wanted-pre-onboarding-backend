package com.example.wantedpreonboardingbackend.global.exception;

import org.springframework.http.HttpStatus;

public class AuthFailedException extends BusinessException {

    public AuthFailedException(ErrorMessage message, HttpStatus status) {
        super(message, status);
    }
}