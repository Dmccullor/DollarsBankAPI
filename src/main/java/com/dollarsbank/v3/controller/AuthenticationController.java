package com.dollarsbank.v3.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.dollarsbank.v3.model.AuthenticationRequest;
import com.dollarsbank.v3.model.AuthenticationResponse;
import com.dollarsbank.v3.util.JwtUtil;

@RestController
@RequestMapping("/api")
public class AuthenticationController {
	
	@Autowired
	JwtUtil jwtUtil;
	
	@Autowired
	AuthenticationManager authenticationManager;
	
	@Autowired
	UserDetailsService userDetailsService;
	
	@PostMapping("/authenticate")
	public ResponseEntity<?> createAuthenticationToken(@RequestBody AuthenticationRequest request) throws Exception {
		
		try {
			
			authenticationManager.authenticate(
					new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword()));
			
		} catch(BadCredentialsException e) {
			//throw new Exception("Incorrect username or password");
			return ResponseEntity.status(404).body("Incorrect username or password.");
		}
		
		final UserDetails userDetails = userDetailsService.loadUserByUsername(request.getUsername());
		
		final String jwt = jwtUtil.generateTokens(userDetails);
		
		return ResponseEntity.status(201).body(new AuthenticationResponse(jwt));
	}

}
