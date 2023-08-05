package com.example.wantedpreonboardingbackend.auth.support.token;

import com.example.wantedpreonboardingbackend.auth.domain.AuthToken;

public interface TokenCreator {

    AuthToken createAuthToken(final Long memberId);

    AuthToken renewAuthToken(final Long outRefreshTokenId);

    Long extractPayload(final String accessToken);
}
