package com.example.wantedpreonboardingbackend.auth.support;

import com.example.wantedpreonboardingbackend.auth.support.token.TokenProvider;
import com.example.wantedpreonboardingbackend.global.exception.AuthFailedException;
import com.example.wantedpreonboardingbackend.global.exception.ErrorMessage;
import com.example.wantedpreonboardingbackend.global.support.annotation.CurrentUser;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.core.MethodParameter;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

@RequiredArgsConstructor
public class AuthenticationPrincipalArgumentResolver implements HandlerMethodArgumentResolver {

    private final TokenProvider tokenProvider;

    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        return parameter.hasParameterAnnotation(CurrentUser.class);
    }

    @Override
    public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer,
                                  NativeWebRequest webRequest, WebDataBinderFactory binderFactory) {
        HttpServletRequest request = webRequest.getNativeRequest(HttpServletRequest.class);
        if (request == null) {
            throw new AuthFailedException(ErrorMessage.ERROR_AUTH_FAILED, HttpStatus.UNAUTHORIZED);
        }
        String token = request.getHeader("Authorization");
        tokenProvider.validateToken(token.substring("Bearer ".length()));

        return tokenProvider.getParsedClaims(token.substring("Bearer ".length()));
    }

}
