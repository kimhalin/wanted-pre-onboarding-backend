package com.example.wantedpreonboardingbackend.member.dto.request;

import com.example.wantedpreonboardingbackend.global.support.Constants;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.*;

@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@AllArgsConstructor
public class MemberLoginRequest {
    @NotBlank
    @Pattern(regexp = Constants.EMAIL_REGEX)
    private String email;

    @NotBlank
    @Size(min = Constants.MIN_PASSWORD_LENGTH)
    private String password;
}
