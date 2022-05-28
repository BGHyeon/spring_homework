package com.sparta;

import com.sparta.entitys.Member;
import com.sparta.repositories.MemberMemoryRepo;
import com.sparta.services.MemberService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.DuplicateKeyException;

import java.sql.SQLIntegrityConstraintViolationException;

@SpringBootTest
class SpringHomeworkApplicationTests {

	private MemberMemoryRepo repo;
	@BeforeEach
	public void before(){
		repo = new MemberMemoryRepo();
	}
	@Test
	void 아이디_중복체크1() {
		Member m = new Member();
		m.setLoginId("test");
		m.setLoginPw("tfefef");
		m.setNickName("한글인데");
		repo.save(m);
		Assertions.assertThrows(DuplicateKeyException.class,()->{
			Member s = new Member();
			s.setLoginId("test");
			s.setLoginPw("ffeesse");
			s.setNickName("whatthe");
			repo.save(s);
		});
	}
	@Test
	void 아이디_중복체크2() {
		Member m = new Member();
		m.setLoginId("testId5");
		m.setLoginPw("testPw5");
		m.setNickName("nickname5");
		repo.save(m);
		Assertions.assertThrows(DuplicateKeyException.class,()->{
			for(int i = 0 ; i < 10; i++) {
				Member s = new Member();
				s.setLoginId("testId"+i);
				s.setLoginPw("testPw"+i);
				s.setNickName("nickname"+i);
				repo.save(s);
			}
		});
	}

	@Test
	void 아이디_정책위반1_짧은아이디(){
		Assertions.assertThrows(IllegalArgumentException.class,()->{
			Member s = new Member();
			s.setLoginId("te");
			s.setLoginPw("testPw");
			s.setNickName("nickname");
			repo.save(s);
		});
	}
	@Test
	void 아이디_정책위반2_형식위반(){
		Assertions.assertThrows(IllegalArgumentException.class,()->{
			Member s = new Member();
			s.setLoginId("!!te!@#");
			s.setLoginPw("testPw");
			s.setNickName("nickname");
			repo.save(s);
		});
	}
	@Test
	void 비밀번호_정책위반1(){
		Assertions.assertThrows(IllegalArgumentException.class,()->{
			Member s = new Member();
			s.setLoginId("testId");
			s.setLoginPw("testPw");
			s.setNickName("test");
			repo.save(s);
		});
	}
	@Test
	void 비밀번호_정책위반2(){
		Assertions.assertThrows(IllegalArgumentException.class,()->{
			Member s = new Member();
			s.setLoginId("testId");
			s.setLoginPw("한글도되나?");
			s.setNickName("한글");
			repo.save(s);
		});
	}
}
