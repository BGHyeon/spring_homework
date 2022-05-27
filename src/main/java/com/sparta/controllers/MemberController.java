package com.sparta.controllers;

import com.sparta.entitys.Member;
import com.sparta.services.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.view.RedirectView;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;


@RestController
public class MemberController {

    @Autowired
    private MemberService service;

   @PostMapping(value = "/member")
    public Object joinMember(Member member) throws UnsupportedEncodingException {
       String url = "/login";
        try{
            service.save(member);
        }catch (IllegalArgumentException e){
            url = "/member/join?msg="+URLEncoder.encode("로그인 또는 비밀번호 형식이 잘못되었습니다.","UTF-8");
        }catch (DataIntegrityViolationException e){
            url = "/member/join?msg="+URLEncoder.encode("중복된 ID 입니다.","UTF-8");
        }
        return new RedirectView(url);
    }

    @PostMapping(value = "/member/{id}")
    public String idCheck(@PathVariable("id")String id){
        if(service.isUseableId(id))
            return "check";
        else
            return "uncheck";
    }
}
