package com.dollarsbank.v3.model;

public abstract class Account {
	
	private Integer id;
	
	private double amount;
	
	public Account() {}
	
	public Account(Integer id, double amount) {
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

	
	

}