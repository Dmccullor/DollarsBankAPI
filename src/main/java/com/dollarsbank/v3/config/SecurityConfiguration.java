package com.dollarsbank.v3.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.dollarsbank.v3.filter.JwtRequestFilter;
import com.dollarsbank.v3.service.MyUserDetailsService;

@SuppressWarnings("deprecation")
@EnableWebSecurity
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {
	
//	@Autowired
//	private MyUserDetailsService userDetailsService;
	
	@Autowired
	private JwtRequestFilter jwtRequestFilter;
	
//	@Override
//	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
//		
//		auth.inMemoryAuthentication()
//			.withUser("admin")
//			.password(passwordEncoder().encode("password123"))
//			.authorities("ADMIN");
//		
//		auth.userDetailsService(userDetailsService);
//	}
	
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		
		http.cors().and().csrf().disable()
		.authorizeRequests()
		.antMatchers("/").permitAll()
		.antMatchers(HttpMethod.GET, "/api/customer/**").permitAll()
		.antMatchers(HttpMethod.POST, "/api/customer").permitAll()
		.antMatchers(HttpMethod.POST, "/api/authenticate").permitAll()
		.antMatchers("/api/checking/**").permitAll()
		.antMatchers("/api/savings/**").permitAll()
		.antMatchers("/api/transaction/**").permitAll()
		//.and().httpBasic();
		
		.anyRequest().authenticated()
		.and().sessionManagement()
		.sessionCreationPolicy(SessionCreationPolicy.STATELESS);
		
		http.addFilterBefore(jwtRequestFilter, UsernamePasswordAuthenticationFilter.class);
	}
	
	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
	
	@Override
	@Bean
	public AuthenticationManager authenticationManagerBean() throws Exception {
		
		return super.authenticationManagerBean();
	}

}
