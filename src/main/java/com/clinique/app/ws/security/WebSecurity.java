package com.clinique.app.ws.security;

import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;

import com.clinique.app.ws.services.UserService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;


@EnableWebSecurity 
public class WebSecurity extends WebSecurityConfigurerAdapter{
	
	private final UserService userDetailsService;
	private final BCryptPasswordEncoder BCryptPasswordEncoder;
	
	public WebSecurity(UserService userDetailsService,
			org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder bCryptPasswordEncoder) {
		super();
		this.userDetailsService = userDetailsService;
		BCryptPasswordEncoder = bCryptPasswordEncoder;
	}
	
	
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		
		http
		.cors().and()
		.csrf().disable()
		.authorizeRequests()
		.antMatchers(HttpMethod.POST,SecurityConstants.SIGN_UP_URL)
		.permitAll()
		.antMatchers(HttpMethod.POST,"/roles")
		.permitAll()
		.antMatchers("/v2/api-docs", "/swagger-resources/**", "/swagger-ui.html**", "/webjars/**", "/v3/api-docs/")
		.permitAll()
		.anyRequest().authenticated().and()
		.addFilter(new AuthenticationFilter(authenticationManager()))
		.addFilter(new AuthorizationFilter(authenticationManager()))
		.sessionManagement()
		.sessionCreationPolicy(SessionCreationPolicy.STATELESS);

	
	}
	
	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.userDetailsService(userDetailsService).passwordEncoder(BCryptPasswordEncoder);
	}

}
