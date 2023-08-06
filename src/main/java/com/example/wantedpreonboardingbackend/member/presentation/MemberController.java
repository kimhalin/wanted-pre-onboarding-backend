package com.example.wantedpreonboardingbackend.member.presentation;

import com.example.wantedpreonboardingbackend.auth.domain.AuthInfo;
import com.example.wantedpreonboardingbackend.global.exception.BusinessException;
import com.example.wantedpreonboardingbackend.global.exception.ErrorMessage;
import com.example.wantedpreonboardingbackend.global.support.annotation.CurrentUser;
import com.example.wantedpreonboardingbackend.member.application.MemberService;
import com.example.wantedpreonboardingbackend.member.dto.request.MemberLoginRequest;
import com.example.wantedpreonboardingbackend.member.dto.request.MemberSignupRequest;
import com.example.wantedpreonboardingbackend.member.dto.response.MemberLoginResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/v1/members")
@Tag(name = "회원")
@RequiredArgsConstructor
public class MemberController {

    private final MemberService memberService;

    @PostMapping("/signup")
    @Operation(summary = "회원가입")
    public ResponseEntity<Void> signup(@Valid @RequestBody MemberSignupRequest dto, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            throw new BusinessException(ErrorMessage.ERROR_INVALID_EMAIL_OR_PASSWORD, HttpStatus.BAD_REQUEST);
        }

        this.memberService.signup(dto);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @PostMapping("/login")
    @Operation(summary = "로그인")
    public ResponseEntity<MemberLoginResponse> signup(@Valid @RequestBody MemberLoginRequest dto, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            throw new BusinessException(ErrorMessage.ERROR_INVALID_EMAIL_OR_PASSWORD, HttpStatus.BAD_REQUEST);
        }

        MemberLoginResponse result = this.memberService.login(dto);
        return ResponseEntity.ok(result);
    }

    @GetMapping("/temp")
    @Operation(summary = "임시", security = @SecurityRequirement(name = "bearerAuth"))
    public ResponseEntity<Void> temp(@Parameter(hidden = true) @CurrentUser AuthInfo authInfo) {
        return ResponseEntity.ok().build();
    }
}
