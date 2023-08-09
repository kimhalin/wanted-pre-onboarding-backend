package com.example.wantedpreonboardingbackend.member.dto.request;

import com.example.wantedpreonboardingbackend.member.domain.Member;
import com.example.wantedpreonboardingbackend.member.domain.Password;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@AllArgsConstructor
public class MemberSignupRequest {

    @NotBlank
    private String email;

    @NotBlank
    private String password;

    public Member toEntity() {
        return Member.builder()
                .email(this.getEmail())
                .password(Password.of(this.getPassword()))
                .build();
    }
}
