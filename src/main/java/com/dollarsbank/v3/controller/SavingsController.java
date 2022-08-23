package com.dollarsbank.v3.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.dollarsbank.v3.model.Checking;
import com.dollarsbank.v3.model.Customer;
import com.dollarsbank.v3.model.Savings;
import com.dollarsbank.v3.repository.CheckingRepository;
import com.dollarsbank.v3.repository.CustomerRepository;
import com.dollarsbank.v3.repository.SavingsRepository;
import com.dollarsbank.v3.service.SavingsService;

@RestController
@RequestMapping("/api")
public class SavingsController {
	
	@Autowired
	SavingsRepository repo;
	
	@Autowired
	SavingsService service;
	
	@Autowired
	CustomerRepository customerRepo;
	
	@Autowired
	CheckingRepository checkingRepo;
	
	@GetMapping("/savings")
	public List<Savings> getSavings() {
		return repo.findAll();
	}
	
	@GetMapping("/savings/{id}")
	public ResponseEntity<?> getSavingsById(@PathVariable int id) {
		Optional<Savings> found = repo.findById(id);
		
		if (found.isEmpty()) {
			return ResponseEntity.status(404).body("Savings account with ID: " + id + " was not found.");
		}
		else {
			return ResponseEntity.status(200).body(found.get());
		}
	}
	
	@PostMapping("/savings")
	public ResponseEntity<?> createSavings(@RequestBody Savings savings) {
		
		savings.setId(null);
		Savings configured = service.buildSavings(savings);
		Savings created = repo.save(configured);
		
		UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication()
				.getPrincipal();
		String username = userDetails.getUsername();
		Optional<Customer> nullableCustomer = customerRepo.findByUsername(username);
		
		Customer thisCustomer = nullableCustomer.get();
		Checking thisChecking = thisCustomer.getChecking();
		
		thisCustomer.setSavings(created);
		thisCustomer.setHas_savings(true);
		customerRepo.save(thisCustomer);
		
		thisChecking.setSavings(created);
		checkingRepo.save(thisChecking);
		
		return ResponseEntity.status(201).body(created);
	}
	
	@PutMapping("/savings")
	public ResponseEntity<?> udpateSavings(@RequestBody Savings savings) {
		boolean exists = repo.existsById(savings.getId());
		
		if(!exists) {
			return ResponseEntity.status(404).body("Account was not updated. Account not found.");
		}
		else {
			Savings updated = repo.save(savings);
			return ResponseEntity.status(200).body(updated);
		}
	}

}
