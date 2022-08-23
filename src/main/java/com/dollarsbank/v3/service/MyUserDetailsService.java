package com.dollarsbank.v3.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.dollarsbank.v3.model.Customer;
import com.dollarsbank.v3.model.MyUserDetails;
import com.dollarsbank.v3.repository.CustomerRepository;

@Service
public class MyUserDetailsService implements UserDetailsService{
	
	@Autowired
	CustomerRepository repo;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

		Optional<Customer> customerFound = repo.findByUsername(username);
		
		if(customerFound.isEmpty()) {
			throw new UsernameNotFoundException(username);
		}
		
		return new MyUserDetails(customerFound.get());
	}

}
