package com.sparta.controllers;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.sparta.FinalValue;
import com.sparta.entities.Member;
import com.sparta.services.CookieService;
import com.sparta.services.JwtAuthService;
import com.sparta.services.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.view.RedirectView;

import javax.servlet.http.HttpServletResponse;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;


@RestController
@RequiredArgsConstructor
public class MemberController {

    private final MemberService service;

    private final JwtAuthService jwtservice;


    @PostMapping(value = "/member/login")
    public String jwtLogin(String username, String password, HttpServletResponse response){
        try {
            return jwtservice.login(username,password,response);
        } catch (Exception e) {
            e.printStackTrace();
            return "fail";
        }
    }

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
        Gson ret = new Gson();
        JsonObject o = new JsonObject();
        if(service.isUseableId(id)) {
            o.addProperty("ret", FinalValue.SUCCESS);
            o.addProperty("msg","사용 가능한 ID 입니다.");
        }else{
            o.addProperty("ret", FinalValue.ILLEGAL_POLICY_ERROR);
            o.addProperty("msg","이미 사용중인 ID 입니다.");
        }
        return ret.toJson(o);
    }

}
