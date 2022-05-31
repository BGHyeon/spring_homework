package com.sparta.repositories;

import com.sparta.entities.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface MemberRepo extends JpaRepository<Member,Long> {
    @Query("Select this_ from Member this_ where this_.loginId=:loginId")
    public Member findByLoginId(@Param("loginId") String loginId);
    @Query("Select this_ from Member this_ where this_.loginId=:loginId and this_.loginPw=:loginPw")
    public Member findByLoginId(@Param("loginId") String loginId,@Param("loginPw")String loginPw);
    public int countByLoginId(String loginId);

}
