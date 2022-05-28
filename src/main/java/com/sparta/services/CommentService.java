package com.sparta.services;

import com.sparta.entities.Comment;
import com.sparta.entities.MemberDetail;
import com.sparta.entities.Notice;
import com.sparta.repositories.CommentRepo;
import com.sparta.repositories.NoticeRepo;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
@Getter
public class CommentService {
    @Autowired
    private CommentRepo repo;

    @Autowired
    private NoticeRepo noticeRepo;

    public void saveComment(long noticeid, String comment){
        Notice n = noticeRepo.findById(noticeid).get();
        Comment c = new Comment(comment);
        c.setMember(((MemberDetail)SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getMember());
        n.addComment(c);
        noticeRepo.save(n);
    }
    public void deleteComment(long id){
        Notice n = noticeRepo.findByContentsId(id);
        Comment c = repo.findById(id).get();
        n.removeComment(c);
        noticeRepo.save(n);
        repo.delete(c);
    }
    public void editComment(long id, String comment){
        Comment c = repo.findById(id).get();
        c.setComment(comment);
        repo.save(c);
    }
}
