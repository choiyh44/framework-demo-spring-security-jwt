package kr.co.ensmart.frameworkdemo.base.security;

import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import lombok.extern.slf4j.Slf4j;

@SpringBootTest(properties = { "spring.jwt.secret=testjwtsecreet1234testjwtsecreet1234" })
@Slf4j
class JwtServiceTest {
	@Autowired
	private JwtService jwtService;
	
	@Test
	void test() {
		UserDetail userDetail = new UserDetail("testuser", "testemail");
		List<String> roles = Arrays.asList("ROLE_CUSTOMER");
		String jwt = jwtService.generateToken(userDetail, roles);
		
		log.info("jwt: {}", jwt);
	}

}
