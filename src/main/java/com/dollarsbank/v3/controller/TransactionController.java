package com.dollarsbank.v3.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.dollarsbank.v3.model.Transaction;
import com.dollarsbank.v3.repository.TransactionRepository;
import com.dollarsbank.v3.service.TransactionService;

@RestController
@RequestMapping("/api")
public class TransactionController {
	
	@Autowired
	TransactionRepository repo;
	
	@Autowired
	TransactionService service;
	
	@GetMapping("/transaction")
	public List<Transaction> getTransactions() {
		return repo.findAll();
	}
	
	@GetMapping("/transaction/{id}")
	public ResponseEntity<?> getTransactionById(@PathVariable int id) {
		Optional<Transaction> found = repo.findById(id);
		
		if (found.isEmpty()) {
			return ResponseEntity.status(404).body("Transaction with ID: " + id + " was not found.");
		}
		else {
			return ResponseEntity.status(200).body(found.get());
		}
	}
	
	@PostMapping("/transaction")
	public ResponseEntity<?> createTransaction(@RequestBody Transaction tran) {
		
		tran.setId(null);
		
		Transaction configured = service.buildTransaction(tran);
		
		Transaction created = repo.save(configured);
		
		service.processTransaction(created);
		
		return ResponseEntity.status(201).body(created);
	}


}
