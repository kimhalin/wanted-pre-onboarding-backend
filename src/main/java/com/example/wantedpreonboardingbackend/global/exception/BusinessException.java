package com.example.wantedpreonboardingbackend.global.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class BusinessException extends RuntimeException {

    private final ErrorMessage error;
    private final HttpStatus httpStatus;

    public BusinessException(ErrorMessage message, HttpStatus status) {
        super(message.getValue());
        this.error = message;
        this.httpStatus = status;
    }
}
