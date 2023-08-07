package com.example.wantedpreonboardingbackend.global.exception;

public enum ErrorMessage {

    // Member
    ERROR_DUPLICATED_EMAIL("이미 가입된 이메일입니다."),
    ERROR_INVALID_EMAIL_OR_PASSWORD("이메일 또는 비밀번호가 잘못된 입력입니다."),
    ERROR_WRONG_PASSWORD("비밀번호가 틀렸습니다."),
    ERROR_MEMBER_NOT_FOUND("회원을 찾을 수 없습니다."),
    ERROR_TOKEN_NOT_FOUND("토큰을 찾을 수 없습니다."),

    // Board
    ERROR_BOARD_NOT_FOUND("게시글을 찾을 수 없습니다."),
    ERROR_ONLY_AUTHOR_CAN_UPDATE("게시글의 작성자만 게시글을 수정할 수 있습니다."),

    // Auth
    ERROR_INVALID_TOKEN("유효하지 않은 토큰입니다."),
    ERROR_AUTH_FAILED("인증에 실패했습니다."),
    ;

    private final String value;

    ErrorMessage(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
