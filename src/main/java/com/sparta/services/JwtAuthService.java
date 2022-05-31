package com.sparta.services;

import com.sparta.configurations.JwtTokenProvider;
import com.sparta.entities.Member;
import com.sparta.entities.MemberDetail;
import com.sparta.repositories.MemberRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class JwtAuthService {
    private final MemberRepo repo;
    private final PasswordEncoder encoder;
    private final JwtTokenProvider provider;
    public String login(String username,String password) throws Exception {

        Member m = repo.findByLoginId(username);
        if(m == null)
            return null;
        else if(!encoder.matches(password,m.getLoginPw())){
            return null;
        }
        MemberDetail detail = new MemberDetail(m);
        return provider.generateToken(detail);
    }
}
