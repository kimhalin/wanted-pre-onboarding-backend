package com.example.wantedpreonboardingbackend.global.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class BadRequestException extends BusinessException {
    public BadRequestException(ErrorMessage message, HttpStatus status) {
        super(message, status);
    }
}
