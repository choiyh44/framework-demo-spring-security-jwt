package kr.co.ensmart.frameworkdemo.base.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import kr.co.ensmart.frameworkdemo.base.security.JwtAccessDeniedHandler;
import kr.co.ensmart.frameworkdemo.base.security.JwtFilter;
import kr.co.ensmart.frameworkdemo.base.security.JwtService;
import lombok.RequiredArgsConstructor;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig extends WebSecurityConfigurerAdapter {
	private final JwtAccessDeniedHandler jwtAccessDeniedHandler;
	private final JwtService jwtService;
	
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }
    
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.csrf().disable()
			.authorizeRequests()
				.antMatchers("/", "/home", "/api/tokens").permitAll()
//				.antMatchers("/api/samples").permitAll()
//				.anyRequest().hasAnyRole("ROLE_CUSTOMER", "ROLE_ADMIN", "ROLE_SERVICE")
				.anyRequest().authenticated()
			.and()
			.exceptionHandling()
				.accessDeniedHandler(jwtAccessDeniedHandler)
            .and()
            .sessionManagement()
            	.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
            .and()
            .addFilterBefore(new JwtFilter(jwtService), UsernamePasswordAuthenticationFilter.class);            	;
            	
	}

	@Override
	public void configure(WebSecurity web) throws Exception {
		web.ignoring().antMatchers("/css/**", "/js/**", "/img/**");
	}

}
