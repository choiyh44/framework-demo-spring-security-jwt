package kr.co.ensmart.frameworkdemo.app.sample;

import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import kr.co.ensmart.frameworkdemo.app.sample.dto.Token;
import kr.co.ensmart.frameworkdemo.base.security.JwtService;
import kr.co.ensmart.frameworkdemo.base.security.UserDetail;

@RestController
@RequestMapping("/api/tokens")
public class TokenApiController {
	@Autowired
	private JwtService jwtService;
	
	@PostMapping("")
	public Token createToken(@RequestBody UserDetail userDetail) {
		return new Token(jwtService.generateToken(userDetail, Arrays.asList("ROLE_CUSTOMER")));
	}
	
}
