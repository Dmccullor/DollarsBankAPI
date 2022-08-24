package com.dollarsbank.v3.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.dollarsbank.v3.model.Customer;
import com.dollarsbank.v3.repository.CustomerRepository;
import com.dollarsbank.v3.service.CustomerService;

@RestController
@RequestMapping("/api")
public class CustomerController {
	
	@Autowired
	CustomerRepository repo;
	
	@Autowired
	CustomerService service;
	
	@Autowired
	PasswordEncoder encoder;
	
	 @GetMapping("/customer")
	 public ResponseEntity<List<Customer>> getallUsers(){
	     List<Customer> userModels = (List<Customer>) service.getAll();
	     System.out.println("Test Line" + userModels);
	     return new ResponseEntity<List<Customer>>(userModels, HttpStatus.OK);
	 }
	
	
	@GetMapping("/customer/{id}")
	public ResponseEntity<?> getCustomerById(@PathVariable int id) {
		Optional<Customer> found = repo.findById(id);
		
		if (found.isEmpty()) {
			return ResponseEntity.status(404).body("Customer with ID: " + id + " was not found.");
		}
		else {
			return ResponseEntity.status(200).body(found.get());
		}
	}
	
	@PostMapping("/customer")
	public ResponseEntity<?> createCustomer(@RequestBody Customer customer) {
		Optional<Customer> found = repo.findByUsername(customer.getUsername());
		
		if (found.isEmpty()) {
			customer.setId(null);
			customer.setRole(Customer.Role.ROLE_USER);
			customer.setPassword(encoder.encode(customer.getPassword()));
			
			Customer created = repo.save(customer);
			
			return ResponseEntity.status(201).body(created);
		}
		else {
			return ResponseEntity.status(409).body(customer.getUsername() + " already exists.");
		}
	}
	
	@PutMapping("/customer")
	public ResponseEntity<?> updateCustomer(@RequestBody Customer customer) {
		boolean exists = repo.existsById(customer.getId());
		
		if(!exists) {
			return ResponseEntity.status(404).body("Customer was not udpated. Customer not found.");
		}
		else {
			Customer updated = repo.save(customer);
			return ResponseEntity.status(200).body(updated);
		}
	}

}
