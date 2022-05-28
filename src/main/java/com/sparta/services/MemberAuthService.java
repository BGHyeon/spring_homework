package com.sparta.services;

import com.sparta.entities.Member;
import com.sparta.entities.MemberDetail;
import com.sparta.repositories.MemberRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class MemberAuthService implements UserDetailsService {
    @Autowired
    private MemberRepo repo;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Member member = repo.findByLoginId(username);
        if(member == null)
            throw new UsernameNotFoundException("계정을 찾을 수 없습니다.");
        return new MemberDetail(member);
    }
}
