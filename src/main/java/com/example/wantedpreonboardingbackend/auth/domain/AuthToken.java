package com.example.wantedpreonboardingbackend.auth.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class AuthToken {
    private String accessToken;
    private Long refreshTokenId;
}
