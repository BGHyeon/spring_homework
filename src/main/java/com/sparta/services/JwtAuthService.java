package com.sparta.services;

import com.sparta.FinalValue;
import com.sparta.jwtsecurity.JwtTokenProvider;
import com.sparta.entities.Member;
import com.sparta.entities.MemberDetail;
import com.sparta.repositories.MemberRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletResponse;

@RequiredArgsConstructor
@Service
public class JwtAuthService {
    private final MemberRepo repo;
    private final PasswordEncoder encoder;
    private final JwtTokenProvider provider;
    private final CookieService cookie;
    public String login(String username, String password, HttpServletResponse response) throws Exception {

        Member m = repo.findByLoginId(username);
        repo.save(m);
        if(m == null)
            return "fail";
        else if(!encoder.matches(password,m.getLoginPw())){
            return "fail";
        }
        MemberDetail detail = new MemberDetail(m);
        String accessToken = provider.generateToken(detail.getMember());
        String refreshToken = provider.generateRefreshToken();
        m.setRefreshToken(refreshToken);
        repo.save(m);
        response.addCookie(cookie.createCookie(FinalValue.JWT_REFRESH_TOKEN,refreshToken));
        response.addCookie(cookie.createCookie(FinalValue.JWT_TOKEN_COOKIE_KEY,accessToken));
        return "success";
    }
}
