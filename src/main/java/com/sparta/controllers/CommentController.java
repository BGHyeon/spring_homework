package com.sparta.controllers;

import com.sparta.ScriptUtil;
import com.sparta.entitys.Comment;
import com.sparta.repositories.CommentRepo;
import com.sparta.repositories.NoticeRepo;
import com.sparta.services.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.view.RedirectView;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@RestController
public class CommentController {
    @Autowired
    private CommentService service;
    @PostMapping("/comment/{noticeid}")
    public RedirectView saveComment(@PathVariable("noticeid")String id, String comment, HttpServletResponse response) throws IOException {
        if(comment.length() > 0) {
            service.saveComment(Long.parseLong(id), comment);
        }else{
            ScriptUtil.alert(response,"댓글 내용을 입력해 주세요","/notice/"+id);
            return null;
        }
        return new RedirectView("/notice/"+id);
    }
    @DeleteMapping("/comment/{id}")
    public String deleteComment(@PathVariable("id") String id){
        try {
            service.deleteComment(Long.parseLong(id));
            return "save";
        }catch (Exception e){
            return e.getMessage();
        }
    }
    @PatchMapping("/comment/{id}")
    public String editComment(@PathVariable("id")String id,String comment){
        try {
        service.editComment(Long.parseLong(id),comment);
            return "save";
        }catch (Exception e){
            return e.getMessage();
        }
    }
}
