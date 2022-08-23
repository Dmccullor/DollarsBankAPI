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
import com.dollarsbank.v3.repository.CheckingRepository;
import com.dollarsbank.v3.repository.CustomerRepository;
import com.dollarsbank.v3.service.CheckingService;

@RestController
@RequestMapping("/api")
public class CheckingController {
	
	@Autowired
	CheckingRepository repo;
	
	@Autowired
	CustomerRepository customerRepo;
	
	@Autowired
	CheckingService service;
	
	@GetMapping("/checking")
	public List<Checking> getChecking() {
		return repo.findAll();
	}
	
	@GetMapping("/checking/{id}")
	public ResponseEntity<?> getCheckingById(@PathVariable int id) {
		Optional<Checking> found = repo.findById(id);
		
		if (found.isEmpty()) {
			return ResponseEntity.status(404).body("Checking account with ID: " + id + " was not found.");
		}
		else {
			return ResponseEntity.status(200).body(found.get());
		}
	}
	
	@PostMapping("/checking")
	public ResponseEntity<?> createChecking(@RequestBody Checking checking) {
		
		checking.setId(null);
		Checking configured = service.buildChecking(checking);
		Checking created = repo.save(configured);
		
		UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication()
				.getPrincipal();
		String username = userDetails.getUsername();
		Optional<Customer> nullableCustomer = customerRepo.findByUsername(username);
		Customer thisCustomer = nullableCustomer.get();
		
		thisCustomer.setChecking(created);
		customerRepo.save(thisCustomer);
		
		return ResponseEntity.status(201).body(created);
	}
	
	@PutMapping("/checking")
	public ResponseEntity<?> updateChecking(@RequestBody Checking checking) {
		boolean exists = repo.existsById(checking.getId());
		
		if(!exists) {
			return ResponseEntity.status(404).body("Account was not updated. Account not found.");
		}
		else {
			Checking updated = repo.save(checking);
			return ResponseEntity.status(200).body(updated);
		}
	}

}
