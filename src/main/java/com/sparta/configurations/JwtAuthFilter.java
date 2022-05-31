package com.sparta.configurations;

import com.sparta.FinalValue;
import com.sparta.services.CookieService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Slf4j
@Component
public class JwtAuthFilter extends OncePerRequestFilter {
    private JwtTokenProvider provider;

    private CookieService cookie;
    @Autowired
    public JwtAuthFilter(JwtTokenProvider provider,CookieService cookie){
        this.provider=provider;
        this.cookie=cookie;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws ServletException, IOException {
        Cookie token = cookie.getCookie(request, FinalValue.JWT_TOKEN_COOKIE_KEY);
        // 유효한 토큰인지 확인
        if (token != null) {
            String jwtToken = token.getValue();
            if (provider.validateToken(jwtToken)) {
                // 토큰이 유효하면 토큰으로부터 유저 정보를 받아옴
                Authentication authentication = provider.getAuthentication(jwtToken);
                // SecurityContext 에 Authentication 객체를 저장
                SecurityContextHolder.getContext().setAuthentication(authentication);
            }
        }

        chain.doFilter(request, response);
    }
}
