package com.dollarsbank.v3.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import com.dollarsbank.v3.model.Checking;
import com.dollarsbank.v3.model.Customer;
import com.dollarsbank.v3.model.Savings;
import com.dollarsbank.v3.repository.CustomerRepository;

@Service
public class SavingsService {
	
	@Autowired
	CustomerRepository customerRepo;
	
	public Savings buildSavings(Savings savings) {
		
		UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication()
				.getPrincipal();
		String username = userDetails.getUsername();
		
		Optional<Customer> nullableCustomer = customerRepo.findByUsername(username);
		
		if(nullableCustomer == null) {
			return savings;
		}
		else {
			Customer thisCustomer = nullableCustomer.get();
			Checking thisChecking = thisCustomer.getChecking();
			
			savings.setCustomer(thisCustomer);
			savings.setChecking(thisChecking);
			
			return savings;
		}
		
	}

}
