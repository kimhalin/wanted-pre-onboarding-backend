package com.example.wantedpreonboardingbackend.member.dto.request;

import com.example.wantedpreonboardingbackend.global.support.Constants;
import com.example.wantedpreonboardingbackend.member.domain.Member;
import com.example.wantedpreonboardingbackend.member.domain.Password;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Getter;

@Getter
public class MemberSignupRequest {

    @NotBlank
    @Pattern(regexp = Constants.EMAIL_REGEX)
    private String email;

    @NotBlank
    @Size(min = Constants.MIN_PASSWORD_LENGTH)
    private String password;

    public Member toEntity() {
        return Member.builder()
                .email(this.getEmail())
                .password(Password.of(this.getPassword()))
                .build();
    }
}
