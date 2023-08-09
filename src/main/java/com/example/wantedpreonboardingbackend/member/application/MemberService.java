package com.example.wantedpreonboardingbackend.member.application;

import com.example.wantedpreonboardingbackend.auth.application.AuthService;
import com.example.wantedpreonboardingbackend.auth.domain.AuthToken;
import com.example.wantedpreonboardingbackend.global.exception.BusinessException;
import com.example.wantedpreonboardingbackend.global.exception.ErrorMessage;
import com.example.wantedpreonboardingbackend.global.exception.NotFoundException;
import com.example.wantedpreonboardingbackend.global.support.Constants;
import com.example.wantedpreonboardingbackend.member.domain.Member;
import com.example.wantedpreonboardingbackend.member.domain.MemberRepository;
import com.example.wantedpreonboardingbackend.member.dto.request.MemberLoginRequest;
import com.example.wantedpreonboardingbackend.member.dto.request.MemberSignupRequest;
import com.example.wantedpreonboardingbackend.member.dto.response.MemberLoginResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.regex.Pattern;

@Service
@RequiredArgsConstructor
public class MemberService {
    private final MemberRepository memberRepository;

    private final AuthService authService;

    @Transactional
    public void signup(final MemberSignupRequest dto) {
        this.checkEmailAndPasswordForm(dto.getEmail(), dto.getPassword());
        checkDuplicatedEmail(dto.getEmail());
        Member member = dto.toEntity();
        this.memberRepository.save(member);
    }

    public MemberLoginResponse login(final MemberLoginRequest dto) {
        this.checkEmailAndPasswordForm(dto.getEmail(), dto.getPassword());

        Member member = this.getByEmail(dto.getEmail());
        boolean isSamePassword = member.getPassword().isSamePassword(dto.getPassword());

        if (!isSamePassword) {
            throw new BusinessException(ErrorMessage.ERROR_WRONG_PASSWORD, HttpStatus.CONFLICT);
        }

        AuthToken authToken = this.authService.createAuthToken(member.getId());
        return new MemberLoginResponse(authToken);
    }

    private void checkDuplicatedEmail(String email) {
        boolean isDuplicated = this.memberRepository.existsByEmail(email);
        if (isDuplicated) {
            throw new BusinessException(ErrorMessage.ERROR_DUPLICATED_EMAIL, HttpStatus.CONFLICT);
        }
    }

    private void checkEmailAndPasswordForm(String email, String password) {
        Pattern emailPattern = Pattern.compile(Constants.EMAIL_REGEX);
        if (password.length() < Constants.MIN_PASSWORD_LENGTH
        || !emailPattern.matcher(email).matches()) {
            throw new BusinessException(ErrorMessage.ERROR_INVALID_EMAIL_OR_PASSWORD, HttpStatus.BAD_REQUEST);
        }
    }

    public Member getById(Long memberId) {
        return this.memberRepository.findById(memberId)
                .orElseThrow(() -> new NotFoundException(ErrorMessage.ERROR_MEMBER_NOT_FOUND, HttpStatus.NOT_FOUND));
    }

    public Member getByEmail(String email) {
        return this.memberRepository.findByEmail(email)
                .orElseThrow(() -> new NotFoundException(ErrorMessage.ERROR_MEMBER_NOT_FOUND, HttpStatus.NOT_FOUND));

    }
}
