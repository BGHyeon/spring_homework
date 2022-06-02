package com.sparta.controllers;

import com.google.gson.Gson;
import com.sparta.entities.Notice;
import com.sparta.entities.MemberDetail;
import com.sparta.services.NoticeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
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

    @PostMapping(value="/notice/{noticeId}")
    public RedirectView saveNotice(@PathVariable("noticeId") String noticeId,Notice Notice){
        service.patchNotice(Notice);
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
    @PostMapping(value = "/like/{id}")
    public String toggleLike(@PathVariable("id") String id, @AuthenticationPrincipal MemberDetail detail){
        return new Gson().toJson(service.toggleLike(Long.parseLong(id), detail.getMember()));
    }

}
