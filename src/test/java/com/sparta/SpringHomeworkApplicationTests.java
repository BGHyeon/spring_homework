package com.sparta;

import com.sparta.entitys.Member;
import com.sparta.services.MemberService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.DataIntegrityViolationException;

import java.sql.SQLIntegrityConstraintViolationException;

@SpringBootTest
class SpringHomeworkApplicationTests {
	@Autowired
	MemberService service;
	@Test
	void 아이디_중복체크() {
		Member m = new Member();
		m.setLoginId("test");
		service.getRepo().save(m);
		try {
			Member s = new Member();
			s.setLoginId("test");
			service.getRepo().save(s);
		}catch (DataIntegrityViolationException e){
			System.out.println("catch Success");
		}
	}

}
