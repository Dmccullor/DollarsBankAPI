package com.dollarsbank.v3.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dollarsbank.v3.model.Customer;
import com.dollarsbank.v3.repository.CustomerRepository;

@Service
public class CustomerService {
	
	@Autowired
	CustomerRepository repo;
	
	public List<Customer> getAll() {
		List<Customer> customerList = repo.findAll();
		
		return customerList;
	}

}
