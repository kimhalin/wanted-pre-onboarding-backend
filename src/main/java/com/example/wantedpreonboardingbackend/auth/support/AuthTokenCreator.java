package com.example.wantedpreonboardingbackend.auth.support;

import com.example.wantedpreonboardingbackend.auth.domain.AuthToken;
import com.example.wantedpreonboardingbackend.auth.domain.RefreshToken;
import com.example.wantedpreonboardingbackend.auth.domain.RefreshTokenRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class AuthTokenCreator implements TokenCreator {

    private final TokenProvider tokenProvider;
    private final RefreshTokenRepository tokenRepository;


    public AuthToken createAuthToken(final Long memberId) {
        String accessToken = tokenProvider.createAccessToken(String.valueOf(memberId));
        RefreshToken refreshToken = this.createRefreshToken(memberId);
        return new AuthToken(accessToken, refreshToken.getId());
    }

    private RefreshToken createRefreshToken(final Long memberId) {

        // 이미 refresh token 존재할 경우
        if (tokenRepository.existsByMemberId(memberId)) {
            return tokenRepository.getByMemberId(memberId);
        }

        String token = tokenProvider.createRefreshToken(String.valueOf(memberId));
        RefreshToken refreshToken = RefreshToken.builder().token(token).memberId(memberId).build();
        return tokenRepository.save(refreshToken);

    }

    public AuthToken renewAuthToken(final Long refreshTokenId) {
        RefreshToken refreshToken = this.tokenRepository.getById(refreshTokenId);
        tokenProvider.validateToken(refreshToken.getToken());

        String accessTokenForRenew = tokenProvider.createAccessToken(String.valueOf(refreshToken.getMemberId()));

        return new AuthToken(accessTokenForRenew, refreshToken.getId());
    }

    public Long extractPayload(final String accessToken) {
        tokenProvider.validateToken(accessToken);
        return Long.valueOf(tokenProvider.getPayload(accessToken));
    }
}