package com.dollarsbank.v3.service;

import java.time.LocalDateTime;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import com.dollarsbank.v3.model.Checking;
import com.dollarsbank.v3.model.Customer;
import com.dollarsbank.v3.model.Savings;
import com.dollarsbank.v3.model.Transaction;
import com.dollarsbank.v3.model.Transaction.ToAcct;
import com.dollarsbank.v3.model.Transaction.Type;
import com.dollarsbank.v3.repository.CheckingRepository;
import com.dollarsbank.v3.repository.CustomerRepository;
import com.dollarsbank.v3.repository.SavingsRepository;

@Service
public class TransactionService {
	
	@Autowired
	CheckingRepository checkingRepo;
	
	@Autowired
	SavingsRepository savingsRepo;
	
	@Autowired
	CustomerRepository customerRepo;
	
	public Transaction buildTransaction(Transaction tran) {
		
		UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication()
				.getPrincipal();
		String username = userDetails.getUsername();
		
		Optional<Customer> nullableCustomer = customerRepo.findByUsername(username);
		
		if(nullableCustomer == null) {
			return tran;
		}
		else {
			Customer thisCustomer = nullableCustomer.get();
			Checking thisChecking = thisCustomer.getChecking();
			Savings thisSavings = thisCustomer.getSavings();
			tran.setDate(LocalDateTime.now());
			tran.setCustomer(thisCustomer);
			tran.setChecking(thisChecking);
			tran.setSavings(thisSavings);
			
		return tran;
		}
	}
	
	public boolean processTransaction(Transaction tran) {
		
		if(tran.getType() == Type.DEPOSIT && tran.getToAcct() == ToAcct.CHECKING) {
			Checking currentAccount = tran.getChecking();
			
			currentAccount.setAmount(currentAccount.getAmount() + tran.getAmount());
			
			checkingRepo.save(currentAccount);
		}
		else if(tran.getType() == Type.DEPOSIT && tran.getToAcct() == ToAcct.SAVINGS) {
			Savings currentAccount = tran.getSavings();
			
			currentAccount.setAmount(currentAccount.getAmount() + tran.getAmount());
			
			savingsRepo.save(currentAccount);
		}
		else if(tran.getType() == Type.WITHDRAWAL && tran.getToAcct() == ToAcct.CHECKING) {
			Checking currentAccount = tran.getChecking();
			
			currentAccount.setAmount(currentAccount.getAmount() - tran.getAmount());
			
			checkingRepo.save(currentAccount);
		}
		else if(tran.getType() == Type.WITHDRAWAL && tran.getToAcct() == ToAcct.SAVINGS) {
			Savings currentAccount = tran.getSavings();
			
			currentAccount.setAmount(currentAccount.getAmount() - tran.getAmount());
			
			savingsRepo.save(currentAccount);
		}
		else if(tran.getType() == Type.TRANSFER && tran.getToAcct() == ToAcct.CHECKING) {
			Checking currentChecking = tran.getChecking();
			
			Savings currentSavings = tran.getSavings();
			
			currentChecking.setAmount(currentChecking.getAmount() + tran.getAmount());
			
			currentSavings.setAmount(currentSavings.getAmount() - tran.getAmount());
			
			checkingRepo.save(currentChecking);
			
			savingsRepo.save(currentSavings);
		}
		else {
			Checking currentChecking = tran.getChecking();
			
			Savings currentSavings = tran.getSavings();
			
			currentChecking.setAmount(currentChecking.getAmount() - tran.getAmount());
			
			currentSavings.setAmount(currentSavings.getAmount() + tran.getAmount());
			
			checkingRepo.save(currentChecking);
			
			savingsRepo.save(currentSavings);
		}
		
		return false;
	}
	
}
