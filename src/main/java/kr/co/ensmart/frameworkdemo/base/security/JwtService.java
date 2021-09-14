package kr.co.ensmart.frameworkdemo.base.security;

import java.util.Base64;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import javax.annotation.PostConstruct;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class JwtService {
	@Value("${spring.jwt.secret}")
	private String jwtSecret;
	private final String algorithm = "HmacSHA256";
	private SecretKey secretKey;
	private final String issuer = "x2bee.com";
	private final String subject = "x2bee api token";

    private final long TOKEN_VALID_MILISECOND = 1000L * 60 * 60 * 10; // 10시간

    @PostConstruct
    protected void init() {
        secretKey = new SecretKeySpec(Base64.getEncoder().encode(jwtSecret.getBytes()), algorithm);
    }

    public String generateToken(UserDetail userDetail,List<String> roles) {
    	final long now = System.currentTimeMillis();

        Claims claims = Jwts.claims();
        claims.put("userName", userDetail.getUserName());
        claims.put("email", userDetail.getEmail());
        claims.put("roles", roles);
    	
		return Jwts.builder()
				.setClaims(claims)
			    .setSubject(subject)
			    .setIssuer(issuer)
			    .setIssuedAt(new Date(now))
			    .setNotBefore(new Date(now - TOKEN_VALID_MILISECOND))
			    .setExpiration(new Date(now + TOKEN_VALID_MILISECOND))
			    .signWith(secretKey)
			    .compact();
    }

    public boolean verifyToken(String token) {
    	try {
    		final Jws<Claims> jws = parseToken(token);
    		
    		final String tokenSubject = jws.getBody().getSubject();
    		final String tokenIssuer = jws.getBody().getIssuer();
    		
    		if ( StringUtils.hasText(tokenSubject) && StringUtils.hasText(tokenIssuer) ) {
    			return tokenSubject.equals(this.subject) && tokenIssuer.equals(this.issuer);
    		}
		} catch (Exception e) {
			log.error("[COMMON][TOKEN_SERVICE] invalidate token: " + token, e);
		}
    	
        return false;
    }
    
    public Jws<Claims> parseToken(String token) {
    	return Jwts.parserBuilder()
    			.setSigningKey(secretKey)
    			.build()
    			.parseClaimsJws(token);
    }
    
    public Optional<String> resolveToken(HttpServletRequest request) {
		final String prefix = "Bearer ";
		String authorization = request.getHeader("Authorization");
		if ( StringUtils.hasText(authorization) && StringUtils.startsWithIgnoreCase(authorization, prefix) ) {
			return Optional.ofNullable(authorization.substring(prefix.length()));
		}
		return Optional.empty();
    }
   
}
