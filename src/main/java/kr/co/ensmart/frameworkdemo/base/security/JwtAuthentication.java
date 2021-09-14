package kr.co.ensmart.frameworkdemo.base.security;

import java.util.Collection;

import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;

public class JwtAuthentication extends AbstractAuthenticationToken {
	private static final long serialVersionUID = 8296845631283729207L;

	private Object principal;
	private String credentials;
	
	public JwtAuthentication(Collection<? extends GrantedAuthority> authorities) {
		super(authorities);
	}
	
	public JwtAuthentication(UserDetail userDetail, String token, Collection<? extends GrantedAuthority> authorities) {
		super(authorities);

		this.principal = userDetail;
		this.credentials = token;
	}


	@Override
	public Object getCredentials() {
		return credentials;
	}

	@Override
	public Object getPrincipal() {
		return principal;
	}

}
