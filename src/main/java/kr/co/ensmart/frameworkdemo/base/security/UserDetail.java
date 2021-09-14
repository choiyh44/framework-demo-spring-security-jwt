package kr.co.ensmart.frameworkdemo.base.security;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserDetail {
	private String userName;
	private String email;
}
