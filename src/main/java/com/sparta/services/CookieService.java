package com.sparta.services;


import com.sparta.FinalValue;
import org.springframework.stereotype.Service;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Service
public class CookieService {

    public Cookie createCookie(String cookieName, String value){
        Cookie token = new Cookie(cookieName,value);
        token.setHttpOnly(true);
        token.setMaxAge((int) 2 * 360 * 1000);
        token.setPath("/");
        return token;
    }
    public Cookie deleteCookie(String cookieName){
        Cookie token = new Cookie(cookieName,"");
        token.setHttpOnly(true);
        token.setMaxAge(0);
        token.setPath("/");
        return token;
    }

    public Cookie getCookie(HttpServletRequest req, String cookieName){
        final Cookie[] cookies = req.getCookies();
        if(cookies==null) return null;
        for(Cookie cookie : cookies){
            if(cookie.getName().equals(cookieName))
                return cookie;
        }
        return null;
    }
    public void resetToken(HttpServletResponse response){
        Cookie c = deleteCookie(FinalValue.JWT_TOKEN_COOKIE_KEY);
        response.addCookie(c);
        Cookie d = deleteCookie(FinalValue.JWT_REFRESH_TOKEN);
        response.addCookie(d);
    }

}
