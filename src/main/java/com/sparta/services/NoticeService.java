package com.sparta.services;

import com.google.gson.JsonObject;
import com.sparta.entities.Comment;
import com.sparta.entities.Member;
import com.sparta.entities.MemberDetail;
import com.sparta.entities.Notice;
import com.sparta.repositories.NoticeRepo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class NoticeService {
    @Autowired
    private NoticeRepo repo;

    public Optional<Notice> getNoticeById(long id) {
        return repo.findById(id);
    }
    public Notice saveNotice(Notice notice) {

        return repo.save(notice);
    }
    public List<Notice> getNotice(){
        return repo.findAllByOrderByIdDesc();
    }
    public void deleteNotice(Notice notice){
        repo.delete(notice);
    }
    public void deleteNotice(long id){
        repo.deleteById(id);
    }
    public Notice addComment(long id, Comment comment){
        comment.setMember(((MemberDetail)SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getMember());
        Notice notice = repo.findById(id).get();
        notice.addComment(comment);
        return repo.save(notice);
    }

    public JsonObject toggleLike(long id, Member member){
        JsonObject o = new JsonObject();
        Notice n = repo.findById(id).get();
        o.addProperty("islike",n.toggleLike(member));
        repo.save(n);
        o.addProperty("count",n.getLikeCount());

        return o;
    }
}
