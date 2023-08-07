package com.example.wantedpreonboardingbackend.global.support;

import com.example.wantedpreonboardingbackend.auth.support.token.AuthTokenCreator;
import com.example.wantedpreonboardingbackend.global.exception.AuthFailedException;
import com.example.wantedpreonboardingbackend.global.exception.ErrorMessage;
import com.example.wantedpreonboardingbackend.global.support.annotation.NoAuth;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

@Component
@RequiredArgsConstructor
public class AuthenticationInterceptor implements HandlerInterceptor {

    private final AuthTokenCreator authTokenCreator;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        boolean check = checkAnnotation(handler, NoAuth.class);

        if (!check) {
            String token = request.getHeader("Authorization");
            if (token == null) {
                throw new AuthFailedException(ErrorMessage.ERROR_AUTH_FAILED, HttpStatus.UNAUTHORIZED);
            }
        }
        return true;
    }

    private boolean checkAnnotation(Object handler, Class<NoAuth> cls) {
        HandlerMethod handlerMethod = (HandlerMethod) handler;
        return handlerMethod.getMethodAnnotation(cls) != null;
    }
}
