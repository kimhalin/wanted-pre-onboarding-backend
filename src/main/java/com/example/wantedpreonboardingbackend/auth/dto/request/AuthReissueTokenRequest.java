package com.example.wantedpreonboardingbackend.auth.dto.request;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;

@Getter
public class AuthReissueTokenRequest {

    @NotNull
    private Long refreshTokenId;
}
