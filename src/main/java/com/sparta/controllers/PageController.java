package com.sparta.controllers;

import com.sparta.entitys.Comments;
import com.sparta.services.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@Controller
public class PageController {
    @Autowired
    private CommentService service;
    @GetMapping(value = "/")
    public String toMain(Model model){
        model.addAttribute("comments",service.getComments());
        return "main";
    }
    @GetMapping(value="/newpage")
    public String toEdit(Model model){
        model.addAttribute("isEdit",false);
        model.addAttribute("data",new Comments());
        return "editpage";
    }
    @GetMapping(value="/comment/{id}")
    public String commentInfo(@PathVariable("id") String id,Model model){
        model.addAttribute("data",service.getCommentsById(Long.parseLong(id)).get());
        return "comment";
    }
    @GetMapping(value="/editpage/{id}")
    public String editPage(@PathVariable("id") String id,Model model){
        model.addAttribute("data",service.getCommentsById(Long.parseLong(id)).get());
        model.addAttribute("isEdit",true);
        return "editpage";
    }
}
