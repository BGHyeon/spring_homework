package com.sparta.services;

import com.sparta.entitys.Comment;
import com.sparta.entitys.Member;
import com.sparta.entitys.Notice;
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
}
