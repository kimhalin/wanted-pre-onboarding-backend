package com.example.wantedpreonboardingbackend.member.dto.response;

import com.example.wantedpreonboardingbackend.auth.domain.AuthToken;
import lombok.*;

@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@AllArgsConstructor
public class MemberLoginResponse {
    private AuthToken authToken;
}
