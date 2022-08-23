package com.dollarsbank.v3.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import com.dollarsbank.v3.model.Checking;
import com.dollarsbank.v3.model.Customer;
import com.dollarsbank.v3.repository.CustomerRepository;

@Service
public class CheckingService {
	
	@Autowired
	CustomerRepository customerRepo;
	
	public Checking buildChecking(Checking checking) {
		
		UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication()
				.getPrincipal();
		String username = userDetails.getUsername();
		
		Optional<Customer> nullableCustomer = customerRepo.findByUsername(username);
		
		if(nullableCustomer == null) {
			return checking;
		}
		else {
			Customer thisCustomer = nullableCustomer.get();
			checking.setCustomer(thisCustomer);
			
			return checking;
		}
		
	}

}
