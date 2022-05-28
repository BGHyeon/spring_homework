package com.sparta.services;

import com.sparta.entitys.Member;
import com.sparta.repositories.MemberRepo;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@Getter
public class MemberService {
    @Autowired
    private MemberRepo repo;

    @Autowired
    private BCryptPasswordEncoder encoder;

    public Member save(Member member){
        if(!member.build())
            throw new IllegalArgumentException("ID Or Password Argument is Wrong");
        else
            member.setLoginPw(encoder.encode(member.getLoginPw()));
        return repo.save(member);
    }

    public boolean isUseableId(String id){
        return repo.countByLoginId(id) == 0;
    }


}
