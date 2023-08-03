package com.example.wantedpreonboardingbackend.member.application;

import com.example.wantedpreonboardingbackend.global.exception.BusinessException;
import com.example.wantedpreonboardingbackend.global.exception.ErrorMessage;
import com.example.wantedpreonboardingbackend.member.domain.Member;
import com.example.wantedpreonboardingbackend.member.domain.MemberRepository;
import com.example.wantedpreonboardingbackend.member.dto.request.MemberSignupRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MemberService {
    private final MemberRepository memberRepository;

    public void signup(final MemberSignupRequest dto) {
        checkDuplicatedEmail(dto.getEmail());
        Member member = dto.toEntity();
        this.memberRepository.save(member);
    }

    private void checkDuplicatedEmail(String email) {
        boolean isDuplicated  = this.memberRepository.existsByEmail(email);
        if (isDuplicated) {
            throw new BusinessException(ErrorMessage.ERROR_DUPLICATED_EMAIL);
        }
    }
}
