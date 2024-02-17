package com.github.immotarity.global.security.handler;

import com.github.immotarity.global.manager.CookieManager;
import com.github.immotarity.global.security.jwt.JwtTokenProvider;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Slf4j
@Component
@RequiredArgsConstructor
public class CustomAuthenticationEntryPointHandler implements AuthenticationEntryPoint {
    private final CookieManager cookieManager;

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException, ServletException {
        String token = cookieManager.getCookieValue(request, JwtTokenProvider.ACCESS_KEY);

        log.error("AUTHENTICATION_ENTRY_POINT");
        log.info(token);
        response.sendError(HttpServletResponse.SC_UNAUTHORIZED);
    }
}
