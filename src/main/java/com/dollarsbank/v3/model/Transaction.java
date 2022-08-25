package com.dollarsbank.v3.model;

import java.time.LocalDateTime;
// import java.time.format.DateTimeFormatter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
public class Transaction {
	
	// private DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
	
	public static enum Type {
		DEPOSIT, WITHDRAWAL, TRANSFER
	}
	
	public static enum ToAcct {
		CHECKING, SAVINGS
	}

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	
	@Column
	private LocalDateTime date;
	
	@Column
	private Type type;
	
	@Column
	private ToAcct toAcct;
	
	@Column
	private double amount;
	
	@ManyToOne
	@JoinColumn(name = "user_id")
	@JsonIgnore
	private Customer customer;
	
	@ManyToOne
	@JoinColumn(name = "checking_id")
	@JsonIgnore
	private Checking checking;
	
	@ManyToOne
	@JoinColumn(name = "savings_id")
	@JsonIgnore
	private Savings savings;
	
	public Transaction() {
		//this(-1, LocalDateTime.now(), Type.DEPOSIT, ToAcct.CHECKING, 0);
	}
	
	public Transaction(Integer id, LocalDateTime date, Type type, ToAcct toAcct, double amount) {
		
		super();
		this.id = id;
		this.date = date;
		this.type = type;
		this.toAcct = toAcct;
		this.amount = amount;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public LocalDateTime getDate() {
		return date;
	}

	public void setDate(LocalDateTime date) {
		this.date = date;
	}

	public Type getType() {
		return type;
	}

	public void setType(Type type) {
		this.type = type;
	}

	public ToAcct getToAcct() {
		return toAcct;
	}

	public void setToAcct(ToAcct toAcct) {
		this.toAcct = toAcct;
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

	public Savings getSavings() {
		return savings;
	}

	public void setSavings(Savings savings) {
		this.savings = savings;
	}

	@Override
	public String toString() {
		return "Transaction [id=" + id + ", date=" + date + ", type=" + type + ", toAcct=" + toAcct + ", amount="
				+ amount + ", customer=" + customer.getName() + "]";
	}
	
	
	
}
