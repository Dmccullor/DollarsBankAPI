package com.dollarsbank.v3.model;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.OneToMany;

@Entity
public class Checking extends Account{
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	
	@Column(nullable = false)
	private double amount;
	
	@Column(nullable = false)
	private double init_deposit;
	
	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "user_id")
	@JsonIgnore
	private Customer customer;
	
	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "savings_id")
	@JsonIgnore
	private Savings savings;
	
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "checking")
	@JsonIgnore
	private List<Transaction> transactions;
	
	public Checking() {
		this(-1, 0, 0);
	}
	
	public Checking(Integer id, double amount, double init_deposit) {
		super(id, amount);
		this.id = id;
		this.amount = amount;
		this.init_deposit = init_deposit;
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

	public double getInit_deposit() {
		return init_deposit;
	}

	public void setInit_deposit(double init_deposit) {
		this.init_deposit = init_deposit;
	}

	public Customer getCustomer() {
		return customer;
	}

	public void setCustomer(Customer customer) {
		this.customer = customer;
	}

	public Savings getSavings() {
		return savings;
	}

	public void setSavings(Savings savings) {
		this.savings = savings;
	}

	public List<Transaction> getTransactions() {
		return transactions;
	}

	public void setTransactions(List<Transaction> transactions) {
		this.transactions = transactions;
	}

	@Override
	public String toString() {
		return "Checking [id=" + id + ", amount=" + amount + ", init_deposit=" + init_deposit + "]";
	}

	
	
}