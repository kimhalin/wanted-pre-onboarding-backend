package com.example.wantedpreonboardingbackend.member.dto.response;

import com.example.wantedpreonboardingbackend.auth.domain.AuthToken;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class MemberLoginResponse {
    private AuthToken authToken;
}
