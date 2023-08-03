package com.example.wantedpreonboardingbackend.global.exception;

import lombok.Getter;

@Getter
public class BadRequestException extends BusinessException {
    public BadRequestException(ErrorMessage message) {
        super(message);
    }
}
