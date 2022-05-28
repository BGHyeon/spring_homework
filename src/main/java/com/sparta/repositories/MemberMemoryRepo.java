package com.sparta.repositories;

import com.sparta.entitys.Member;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.Map;
@Repository
public class MemberMemoryRepo {
    Map<Long, Member> memory = new HashMap<>();
    private long ID_SEQUENCE = 1L;
    public Member save(Member m){
        if(!m.build()){
            throw new IllegalArgumentException("ID형식이 잘못되었거나 비밀번호에 닉네임이 포함되어있습니다.");
        }else if(!isUseableId(m.getLoginId())){
            throw new DuplicateKeyException("이미 존재하는 ID입니다.");
        }else{
            System.out.println("save Memeber:" +ID_SEQUENCE);
            m.setId(ID_SEQUENCE);
            memory.put(ID_SEQUENCE++,m);
        }
        return m;
    }
    private boolean isUseableId(String id){
        return memory.values().stream().filter(e->e.getLoginId().equals(id)).count() == 0;
    }

}
