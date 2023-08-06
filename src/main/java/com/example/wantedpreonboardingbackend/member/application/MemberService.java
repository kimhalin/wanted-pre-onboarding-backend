package com.example.wantedpreonboardingbackend.member.application;

import com.example.wantedpreonboardingbackend.auth.application.AuthService;
import com.example.wantedpreonboardingbackend.auth.domain.AuthToken;
import com.example.wantedpreonboardingbackend.global.exception.BusinessException;
import com.example.wantedpreonboardingbackend.global.exception.ErrorMessage;
import com.example.wantedpreonboardingbackend.member.domain.Member;
import com.example.wantedpreonboardingbackend.member.domain.MemberRepository;
import com.example.wantedpreonboardingbackend.member.dto.request.MemberLoginRequest;
import com.example.wantedpreonboardingbackend.member.dto.request.MemberSignupRequest;
import com.example.wantedpreonboardingbackend.member.dto.response.MemberLoginResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MemberService {
    private final MemberRepository memberRepository;

    private final AuthService authService;

    public void signup(final MemberSignupRequest dto) {
        checkDuplicatedEmail(dto.getEmail());
        Member member = dto.toEntity();
        this.memberRepository.save(member);
    }

    public MemberLoginResponse login(final MemberLoginRequest dto) {
        Member member = memberRepository.getByEmail(dto.getEmail());
        boolean isSamePassword = member.getPassword().isSamePassword(dto.getPassword());

        if (!isSamePassword) {
            throw new BusinessException(ErrorMessage.ERROR_WRONG_PASSWORD, HttpStatus.CONFLICT);
        }

        AuthToken authToken = this.authService.createAuthToken(member.getId());
        return new MemberLoginResponse(authToken);
    }

    private void checkDuplicatedEmail(String email) {
        boolean isDuplicated  = this.memberRepository.existsByEmail(email);
        if (isDuplicated) {
            throw new BusinessException(ErrorMessage.ERROR_DUPLICATED_EMAIL, HttpStatus.CONFLICT);
        }
    }

    public Member getById(Long memberId){
        return this.memberRepository.getById(memberId);
    }
}
