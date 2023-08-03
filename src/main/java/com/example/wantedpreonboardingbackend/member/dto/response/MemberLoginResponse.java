package com.example.wantedpreonboardingbackend.member.dto.response;

import com.example.wantedpreonboardingbackend.auth.domain.AuthToken;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class MemberLoginResponse {
    private AuthToken authToken;
}
