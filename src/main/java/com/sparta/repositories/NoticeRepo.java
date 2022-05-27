package com.sparta.repositories;

import com.sparta.entitys.Notice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;


import java.util.List;

@Repository
public interface NoticeRepo extends JpaRepository<Notice,Long> {

    public List<Notice> findAllByOrderByIdDesc();

    @Query("Select this_ from Notice this_ join this_.comments t where t.id=:id")
    public Notice findByContentsId(@Param("id")long id);
}
