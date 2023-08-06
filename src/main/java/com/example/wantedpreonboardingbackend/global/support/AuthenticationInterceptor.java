package com.example.wantedpreonboardingbackend.global.support;

import com.example.wantedpreonboardingbackend.auth.support.token.AuthTokenCreator;
import com.example.wantedpreonboardingbackend.global.support.annotation.NoAuth;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

@RequiredArgsConstructor
public class AuthenticationInterceptor implements HandlerInterceptor {

    private final AuthTokenCreator authTokenCreator;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        boolean check = checkAnnotation(handler, NoAuth.class);
        if (!check) {
            String token = request.getHeader("Authorization");
            Long userId = this.authTokenCreator.extractPayload(token);
            request.setAttribute("userId", userId);
        }
        return true;
    }

    private boolean checkAnnotation(Object handler, Class<NoAuth> cls) {
        HandlerMethod handlerMethod = (HandlerMethod) handler;
        return handlerMethod.getMethodAnnotation(cls) != null;
    }
}
