package com.example.wantedpreonboardingbackend.auth.application;

import com.example.wantedpreonboardingbackend.auth.domain.AuthToken;
import com.example.wantedpreonboardingbackend.auth.dto.request.AuthReissueTokenRequest;
import com.example.wantedpreonboardingbackend.auth.support.token.AuthTokenCreator;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final AuthTokenCreator authTokenCreator;


    public AuthToken createAuthToken(Long memberId) {
        return this.authTokenCreator.createAuthToken(memberId);
    }

    public AuthToken reissueAccessToken(AuthReissueTokenRequest dto) {
        return this.authTokenCreator.renewAuthToken(dto.getRefreshTokenId());
    }

}
