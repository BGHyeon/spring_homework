package com.sparta.controllers;

import com.sparta.entitys.Comments;
import com.sparta.services.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.view.RedirectView;

import java.util.List;

@RestController
public class CommentsController {
    @Autowired
    private CommentService service;


    @GetMapping(value = "/api/allcomment")
    public List<Comments> getAllComments() {
        return service.getComments();
    }

    @GetMapping(value="/api/comment/{id}")
    public Comments getCommentsInfo(@PathVariable("id") String id) {
        return service.getCommentsById(Long.parseLong(id)).get();
    }

    @PostMapping(value="/api/savecomment")
    public RedirectView updateComment(Comments comments) {
        service.saveComments(comments);
        return new RedirectView("/");
    }

    @PatchMapping(value="/api/savecomment")
    public RedirectView saveComments(Comments comments){
        service.saveComments(comments);
        return new RedirectView("/");
    }

    @DeleteMapping(value="/api/deletecomment")
    public String deletecomment(@RequestParam("id") String id,@RequestParam("pw") String pw) {
        if(service.deleteComments(Long.parseLong(id),pw))
        	return "save";
        else
        	return "fail";
    }
}
