package com.sparta.jwtsecurity;

import com.sparta.FinalValue;
import com.sparta.entities.MemberDetail;
import com.sparta.services.CookieService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
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
        Cookie refreshToken = cookie.getCookie(request,FinalValue.JWT_REFRESH_TOKEN);
        // 유효한 토큰인지 확인
        if (token != null && refreshToken != null) {
            String jwtToken = token.getValue();
            String refresh = refreshToken.getValue();
            if (provider.validateToken(jwtToken)) {
                // 토큰값과 refresh 토큰으로 유저 정보를 받아옴
                MemberDetail detail = provider.getMemberDetail(jwtToken,refresh);
                if(detail.getMember() != null) {
                    Authentication authentication = provider.getAuthentication(detail);
                    // SecurityContext 에 Authentication 객체를 저장
                    SecurityContextHolder.getContext().setAuthentication(authentication);
                    // Refresh 토큰 만료시 다시 발급
                    if (!provider.validateToken(refresh)) {
                        String newRefreshToken = provider.generateRefreshToken();
                        String newAccessToken = provider.generateToken(detail.getMember());
                        provider.setRefreshToken(detail.getMember(),newRefreshToken);
                        response.addCookie(cookie.createCookie(FinalValue.JWT_TOKEN_COOKIE_KEY,newAccessToken));
                        response.addCookie(cookie.createCookie(FinalValue.JWT_REFRESH_TOKEN,newRefreshToken));
                    }
                }
            }
        }

        chain.doFilter(request, response);
    }
}
