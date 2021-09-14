package kr.co.ensmart.frameworkdemo.base.security;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.util.CollectionUtils;
import org.springframework.web.filter.GenericFilterBean;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;

public class JwtFilter extends GenericFilterBean {
	private JwtService jwtService;
	
	public JwtFilter(JwtService jwtService) {
		this.jwtService = jwtService;
	}
	
    @Override
    public void doFilter(ServletRequest request,ServletResponse response,FilterChain chain) 
    		throws IOException, ServletException {
    	Optional<String> jwtOpt = jwtService.resolveToken((HttpServletRequest)request);
    	if (jwtOpt.isPresent() && jwtService.verifyToken(jwtOpt.get())) {
    		Jws<Claims> jws = jwtService.parseToken(jwtOpt.get());
    		UserDetail userDetail = new UserDetail();
    		userDetail.setUserName((String)jws.getBody().get("userName"));
    		userDetail.setEmail((String)jws.getBody().get("email"));
    		List<String> roles = (List<String>)jws.getBody().get("roles");
	    	List<GrantedAuthority> authorities = new ArrayList<>();
	    	if (!CollectionUtils.isEmpty(roles)) {
	    		roles.forEach(role -> authorities.add(new SimpleGrantedAuthority(role)));
	    	}
	    	UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(userDetail, "", authorities);
			SecurityContextHolder.getContext().setAuthentication(authentication);
    	}
    	
    	chain.doFilter(request, response);
    }
    
}
