package com.example.wantedpreonboardingbackend.member.application;

import com.example.wantedpreonboardingbackend.auth.application.AuthService;
import com.example.wantedpreonboardingbackend.auth.domain.AuthToken;
import com.example.wantedpreonboardingbackend.global.exception.BusinessException;
import com.example.wantedpreonboardingbackend.global.exception.NotFoundException;
import com.example.wantedpreonboardingbackend.member.domain.Member;
import com.example.wantedpreonboardingbackend.member.domain.MemberRepository;
import com.example.wantedpreonboardingbackend.member.domain.Password;
import com.example.wantedpreonboardingbackend.member.dto.request.MemberLoginRequest;
import com.example.wantedpreonboardingbackend.member.dto.request.MemberSignupRequest;
import com.example.wantedpreonboardingbackend.member.dto.response.MemberLoginResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class MemberServiceTest {

    @InjectMocks
    private MemberService memberService;
    @Mock
    private MemberRepository memberRepository;
    @Mock
    private AuthService authService;

    @BeforeEach
    void setUp() {

    }

    @Test
    @DisplayName("회원가입을 할 수 있다")
    void signup() {

        // given
        MemberSignupRequest request = MemberSignupRequest.builder()
                .email("user@example.com")
                .password("password")
                .build();
        when(memberRepository.save(any(Member.class))).thenReturn(null);
        when(memberRepository.existsByEmail(request.getEmail())).thenReturn(false);

        // when
        memberService.signup(request);

        // then
        verify(memberRepository, times(1)).existsByEmail(request.getEmail());
        verify(memberRepository, times(1)).save(any(Member.class));
    }

    @Test
    @DisplayName("로그인을 할 수 있다")
    void login() {

        // given
        MemberLoginRequest request = MemberLoginRequest.builder()
                .email("user@example.com")
                .password("password")
                .build();
        Member member = Member.builder()
                .id(1L)
                .email("user@example.com")
                .password(Password.of("password"))
                .build();

        AuthToken authToken = new AuthToken("eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiIxIiw", 1L);

        when(memberRepository.findByEmail(request.getEmail())).thenReturn(Optional.ofNullable(member));
        when(authService.createAuthToken(member.getId())).thenReturn(authToken);

        // when
        MemberLoginResponse response = memberService.login(request);

        // then
        verify(memberRepository, times(1)).findByEmail(request.getEmail());
        verify(authService, times(1)).createAuthToken(member.getId());
        assertThat(response.getAuthToken()).isEqualTo(authToken);
    }

    @Test
    @DisplayName("Member ID로 Member를 조회할 수 있다")
    void getById() {

        // given
        Member member = Member.builder()
                .id(1L)
                .email("user@example.com")
                .password(Password.of("password"))
                .build();
        when(memberRepository.findById(1L)).thenReturn(Optional.ofNullable(member));

        // when
        Member memberEntity = memberService.getById(1L);

        // then
        verify(memberRepository, times(1)).findById(1L);
        assertThat(memberEntity).isEqualTo(member);
    }

    @Test
    @DisplayName("회원가입 시 해당 이메일로 가입된 member 존재할 경우 예외가 발생한다")
    void signUp_Exception_DuplicatedEmail() {

        // given
        MemberSignupRequest request = MemberSignupRequest.builder()
                .email("user@example.com")
                .password("password")
                .build();

        when(memberRepository.existsByEmail(request.getEmail())).thenReturn(true);

        // when & then
        assertThatThrownBy(() -> memberService.signup(request))
                .isInstanceOf(BusinessException.class);
    }

    @Test
    @DisplayName("로그인 시 해당 이메일로 가입된 회원이 존재하지 않을 경우 예외가 발생한다")
    void login_Exception_NotFound_Member() {

        //given
        MemberLoginRequest request = MemberLoginRequest.builder()
                .email("user@example.com")
                .password("password")
                .build();

        when(memberRepository.findByEmail(request.getEmail())).thenReturn(Optional.empty());

        // when & then
        assertThatThrownBy(() -> memberService.login(request))
                .isInstanceOf(NotFoundException.class);
    }

    @Test
    @DisplayName("로그인 시 비밀번호 불일치일 경우 예외가 발생한다")
    void login_Exception_NotSame_Password() {

        //given
        MemberLoginRequest request = MemberLoginRequest.builder()
                .email("user@example.com")
                .password("password")
                .build();
        Member member = Member.builder()
                .id(1L)
                .email("user@example.com")
                .password(Password.of("password01"))
                .build();

        when(memberRepository.findByEmail(request.getEmail())).thenReturn(Optional.ofNullable(member));

        // when & then
        assertThatThrownBy(() -> memberService.login(request))
                .isInstanceOf(BusinessException.class);
    }
}