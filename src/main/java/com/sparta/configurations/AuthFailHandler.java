package com.sparta.configurations;

import com.sparta.ScriptUtil;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.CredentialsExpiredException;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
@Component
public class AuthFailHandler extends SimpleUrlAuthenticationFailureHandler {
    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception) throws IOException, ServletException {
        exception.printStackTrace();
        String errorMsg;
        if(exception instanceof BadCredentialsException || exception instanceof UsernameNotFoundException){
            errorMsg = "ID 또는 비밀번호가 맞지 않습니다.";
        }else if(exception instanceof InternalAuthenticationServiceException){
            errorMsg = "이게 뭐지";
        }else if(exception instanceof CredentialsExpiredException){
            errorMsg = "세션이 만료되었습니다. 다시 로그인 해 주세요";
        }else{
            errorMsg = "알 수 없는 오류가 발생했습니다. 관리자에게 문의해주세요.";
        }
        ScriptUtil.alert(response,errorMsg,"/login");
    }
}
