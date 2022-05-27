package com.sparta.controllers;

import com.sparta.entitys.Notice;
import com.sparta.services.MemberDetail;
import com.sparta.services.NoticeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@Controller
public class PageController {
    @Autowired
    private NoticeService service;
    @GetMapping(value = "/login")
    public String toLogin(){
        return "index";
    }
    @GetMapping(value = "/member/join")
    public String toJoin(){
        return "join";
    }
    @GetMapping(value = "/main")
    public String toMain(Model model){
        model.addAttribute("notices",service.getNotice());
        return "main";
    }
    @GetMapping(value="/newpage")
    public String toEdit(Model model){
        model.addAttribute("isEdit",false);
        model.addAttribute("data",new Notice());
        return "editpage";
    }

    @GetMapping(value="/notice/{id}")
    public String commentInfo(@PathVariable("id") String id,@AuthenticationPrincipal MemberDetail detail, Model model){
        Notice notice = service.getNoticeById(Long.parseLong(id)).get();
        model.addAttribute("data",notice);
        model.addAttribute("owner",notice.getMember().getId() == detail.getMember().getId());
        return "notice";
    }
    @GetMapping(value="/editpage/{id}")
    public String editPage(@PathVariable("id") String id,Model model){
        model.addAttribute("data",service.getNoticeById(Long.parseLong(id)).get());
        model.addAttribute("isEdit",true);
        return "editpage";
    }
}
