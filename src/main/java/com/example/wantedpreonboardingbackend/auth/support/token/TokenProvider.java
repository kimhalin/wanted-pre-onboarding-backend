package com.example.wantedpreonboardingbackend.auth.support.token;

import com.example.wantedpreonboardingbackend.auth.domain.AuthInfo;

public interface TokenProvider {

    String createAccessToken(final Long memberId);

    String createRefreshToken(final Long memberId);

    String getPayload(final String token);

    AuthInfo getParsedClaims(String token);

    void validateToken(final String token);
}
