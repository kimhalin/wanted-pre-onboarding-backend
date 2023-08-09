package com.example.wantedpreonboardingbackend.member.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@AllArgsConstructor
public class MemberLoginRequest {
    @NotBlank
    private String email;

    @NotBlank
    private String password;
}
