package com.sparta.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.sparta.entitys.Comments;

import java.util.List;

@Repository
public interface CommentsRepo extends JpaRepository<Comments,Long> {

    public List<Comments> findAllByOrderByIdDesc();
    @Query("Select this_ from Comments this_ where this_.id=:id and this_.password=:pw")
    public Comments findByIdAndPassword(@Param("id") long id,@Param("pw")String pw);
}
