package com.sparta.controllers;

import com.sparta.entitys.Notice;
import com.sparta.services.MemberDetail;
import com.sparta.services.MemberService;
import com.sparta.services.NoticeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.view.RedirectView;

import java.util.List;

@RestController
public class NoticeController {
    @Autowired
    private NoticeService service;


    @GetMapping(value = "/notice")
    public List<Notice> getAllNotice() {
        return service.getNotice();
    }

    @PostMapping(value="/notice")
    public RedirectView updateComment(@AuthenticationPrincipal MemberDetail detail, Notice Notice) {
        Notice.setMember(detail.getMember());
        service.saveNotice(Notice);
        return new RedirectView("/main");
    }

    @PatchMapping(value="/notice")
    public RedirectView saveNotice(Notice Notice){
        service.saveNotice(Notice);
        return new RedirectView("/main");
    }

    @DeleteMapping(value = "/notice/{id}")
    public String deleteNotice(@PathVariable("id") String id){
        try {
            service.deleteNotice(Long.parseLong(id));
        }catch (Exception e){
            e.printStackTrace();
            return e.getMessage();
        }
        return "save";
    }


}