package com.example.wantedpreonboardingbackend.global.support.auth;

import com.example.wantedpreonboardingbackend.auth.domain.AuthToken;

public interface TokenCreator {

    AuthToken createAuthToken(final Long memberId);

    AuthToken renewAuthToken(final Long outRefreshTokenId);

    Long extractPayload(final String accessToken);
}
