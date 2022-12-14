package com.dollarsbank.v3.model;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonAutoDetect.Visibility;
import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@JsonAutoDetect(fieldVisibility = Visibility.ANY)
public class Savings extends Account {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	
	@Column(nullable = false)
	private double amount;
	
	@OneToOne
	@JoinColumn(name = "user_id")
	@JsonIgnore
	private Customer customer;
	
	@OneToOne
	@JoinColumn(name = "checking_id")
	@JsonIgnore
	private Checking checking;
	
	@OneToMany(mappedBy = "savings")
	@JsonIgnore
	private List<Transaction> transactions;
	
	public Savings() {}
	
	public Savings(Integer id, double amount) {
		super(id, amount);
		this.id = id;
		this.amount = amount;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public double getAmount() {
		return amount;
	}

	public void setAmount(double amount) {
		this.amount = amount;
	}

	public Customer getCustomer() {
		return customer;
	}

	public void setCustomer(Customer customer) {
		this.customer = customer;
	}

	public Checking getChecking() {
		return checking;
	}

	public void setChecking(Checking checking) {
		this.checking = checking;
	}

	public List<Transaction> getTransactions() {
		return transactions;
	}

	public void setTransactions(List<Transaction> transactions) {
		this.transactions = transactions;
	}

	@Override
	public String toString() {
		return "Savings [id=" + id + ", amount=" + amount + "]";
	}
	
}
