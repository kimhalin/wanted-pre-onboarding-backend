package com.example.wantedpreonboardingbackend.global.exception;

public enum ErrorMessage {

    // Member
    ERROR_DUPLICATED_EMAIL("이미 가입된 이메일입니다."),
    ERROR_INVALID_EMAIL_OR_PASSWORD("이메일 또는 비밀번호가 잘못된 입력입니다.")
    ;

    private final String value;

    ErrorMessage(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
