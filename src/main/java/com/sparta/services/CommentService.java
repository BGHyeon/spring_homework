package com.sparta.services;

import com.sparta.entitys.Comments;
import com.sparta.repositories.CommentsRepo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CommentService {
    @Autowired
    private CommentsRepo repo;

    public Optional<Comments> getCommentsById(long id) {
        return repo.findById(id);
    }
    public Comments saveComments(Comments comments) {
        return repo.save(comments);
    }
    public List<Comments> getComments(){
        return repo.findAllByOrderByIdDesc();
    }
    public void deleteComments(Comments comments){
        repo.delete(comments);
    }

    public boolean deleteComments(long id,String pw){
    	Comments c = repo.findByIdAndPassword(id, pw);
    	if(c == null)
    		return false;
    	else {
    		repo.delete(c);
    		return true;
    	}
    }
}
