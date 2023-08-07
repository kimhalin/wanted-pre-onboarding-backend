package com.example.wantedpreonboardingbackend.auth.presentation;

import com.example.wantedpreonboardingbackend.auth.application.AuthService;
import com.example.wantedpreonboardingbackend.auth.domain.AuthToken;
import com.example.wantedpreonboardingbackend.auth.dto.request.AuthReissueTokenRequest;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/v1/auth")
@Tag(name = "Auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("/tokens")
    @Operation(summary = "Access Token 재발행", security = @SecurityRequirement(name = "bearerAuth"))
    public ResponseEntity<AuthToken> reissueAccessToken(@Valid @RequestBody AuthReissueTokenRequest dto) {
        AuthToken authToken = this.authService.reissueAccessToken(dto);
        return ResponseEntity.ok(authToken);
    }
}
