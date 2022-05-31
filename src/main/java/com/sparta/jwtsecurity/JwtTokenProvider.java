package com.sparta.jwtsecurity;

import com.sparta.entities.MemberDetail;
import com.sparta.repositories.MemberRepo;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.util.Base64;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Component
public class JwtTokenProvider {
    private final String JWT_SECRET = Base64.getEncoder().encodeToString("Secret Key".getBytes());
    private final long ValidTime = 360000L;
    private MemberRepo repo;
    @Autowired
    public JwtTokenProvider(MemberRepo repo){
        this.repo = repo;
    }
    public String generateToken(MemberDetail userDetails) {
        Map<String, Object> claims = new HashMap<>();
        claims.put("loginId",userDetails.getMember().getLoginId());
        claims.put("loginPw",userDetails.getMember().getLoginPw());
        claims.put("id",userDetails.getMember().getId());
        return doGenerateToken(claims, userDetails.getUsername());
    }
    private String doGenerateToken(Map<String, Object> claims, String subject) {
        return Jwts.builder()
                .setHeaderParam("typ","JWT")
                .setClaims(claims)
                .setSubject(subject)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + ValidTime))
                .signWith(SignatureAlgorithm.HS256, JWT_SECRET)
                .compact();
    }
    // Principal 반환
    public Authentication getAuthentication(String token) {
        MemberDetail userDetails = new MemberDetail(repo.findById(Long.parseLong(this.getUserPk(token))).get());
        return new UsernamePasswordAuthenticationToken(userDetails, "", userDetails.getAuthorities());
    }

    // 토큰에서 회원 정보 추출
    public String getUserPk(String token) {
        return Jwts.parser().setSigningKey(JWT_SECRET).parseClaimsJws(token).getBody().get("id").toString();
    }

    // 토큰의 유효성 + 만료일자 확인
    public boolean validateToken(String jwtToken) {
        try {
            Jws<Claims> claims = Jwts.parser().setSigningKey(JWT_SECRET).parseClaimsJws(jwtToken);
            return !claims.getBody().getExpiration().before(new Date());
        } catch (Exception e) {
            return false;
        }
    }
}
