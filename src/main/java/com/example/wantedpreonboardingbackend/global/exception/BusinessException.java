package com.example.wantedpreonboardingbackend.global.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class BusinessException extends RuntimeException {

    private final ErrorMessage error;
    private static final HttpStatus httpStatus = HttpStatus.CONFLICT;

    public BusinessException(ErrorMessage message) {
        super(message.getValue());
        this.error = message;
    }
}
