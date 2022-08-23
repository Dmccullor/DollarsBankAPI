package com.dollarsbank.v3.model;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonAutoDetect.Visibility;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Entity
@JsonIgnoreProperties({"hibernateLazyInitializer"})
public class Customer {
	
	public static enum Role {
		ROLE_USER, ROLE_ADMIN
	}

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	
	@Column(nullable = false)
	private String name;
	
	@Column(nullable = false)
	private String username;
	
	@Column(nullable = false)
	private String address;
	
	@Column(nullable = false)
	private long phone;
	
	@Column(nullable = false)
	private String password;
	
	@Enumerated(EnumType.STRING)
	@Column(nullable = false)
	private Role role;
	
	@Column(columnDefinition = "boolean default true")
	private boolean enabled;
	
	@OneToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "checking_id")
	private Checking checking;
	
	@Column(nullable = false)
	private boolean has_savings;
	
	@OneToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "savings_id")
	private Savings savings;
	
	@OneToMany(fetch = FetchType.EAGER, mappedBy = "customer")
	private List<Transaction> transactions;
	
	public Customer() {
		// this(-1, "", "", "", 0L, "", Role.ROLE_USER, true, false);
	}
	
	

	public Customer(Integer id, String name, String username, String address, long phone, String password, Role role,
			boolean enabled, Checking checking, boolean has_savings, Savings savings) {
		super();
		this.id = id;
		this.name = name;
		this.username = username;
		this.address = address;
		this.phone = phone;
		this.password = password;
		this.role = role;
		this.enabled = enabled;
		this.checking = checking;
		this.has_savings = has_savings;
		this.savings = savings;
		//this.transactions = transactions;
	}



	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public long getPhone() {
		return phone;
	}

	public void setPhone(long phone) {
		this.phone = phone;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public Role getRole() {
		return role;
	}

	public void setRole(Role role) {
		this.role = role;
	}

	public boolean isEnabled() {
		return enabled;
	}

	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}

	public Checking getChecking() {
		return checking;
	}

	public void setChecking(Checking checking) {
		this.checking = checking;
	}

	public boolean isHas_savings() {
		return has_savings;
	}

	public void setHas_savings(boolean has_savings) {
		this.has_savings = has_savings;
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
		return "Customer [id=" + id + ", name=" + name + ", username=" + username + ", address=" + address + ", phone="
				+ phone + ", password=" + password + ", role=" + role + ", enabled=" + enabled + ", checking="
				+ checking + ", has_savings=" + has_savings + ", savings=" + savings + ", transactions=" + transactions
				+ "]";
	}
	
	
	
//	public Customer(Integer id, String name, String username, String address, long phone, String password, Role role, boolean enabled, boolean has_savings) {
//		super();
//		this.id = id;
//		this.name = name;
//		this.username = username;
//		this.address = address;
//		this.phone = phone;
//		this.password = password;
//		this.role = role;
//		this.enabled = enabled;
//		this.has_savings = has_savings;
//	}
//
//	public Integer getId() {
//		return id;
//	}
//
//	public void setId(Integer id) {
//		this.id = id;
//	}
//
//	public String getName() {
//		return name;
//	}
//
//	public void setName(String name) {
//		this.name = name;
//	}
//	
//	public String getUsername() {
//		return username;
//	}
//	
//	public void setUsername(String username) {
//		this.username = username;
//	}
//
//	public String getAddress() {
//		return address;
//	}
//
//	public void setAddress(String address) {
//		this.address = address;
//	}
//
//	public long getPhone() {
//		return phone;
//	}
//
//	public void setPhone(long phone) {
//		this.phone = phone;
//	}
//
//	public String getPassword() {
//		return password;
//	}
//
//	public void setPassword(String password) {
//		this.password = password;
//	}
//	
//	public Role getRole() {
//		return role;
//	}
//	
//	public void setRole(Role role) {
//		this.role = role;
//	}
//	
//	public boolean isEnabled() {
//		return enabled;
//	}
//	
//	public void setEnabled(boolean enabled) {
//		this.enabled = enabled;
//	}
//
//	public Checking getChecking() {
//		return checking;
//	}
//
//	public void setChecking(Checking checking) {
//		this.checking = checking;
//	}
//
//	public boolean isHas_savings() {
//		return has_savings;
//	}
//
//	public void setHas_savings(boolean has_savings) {
//		this.has_savings = has_savings;
//	}
//
//	public Savings getSavings() {
//		return savings;
//	}
//
//	public void setSavings(Savings savings) {
//		this.savings = savings;
//	}
//
//	public List<Transaction> getTransactions() {
//		return transactions;
//	}
//
//	public void setTransactions(List<Transaction> transactions) {
//		this.transactions = transactions;
//	}
//
//	@Override
//	public String toString() {
//		return "Customer [id=" + id + ", name=" + name + ", username=" + username + ", address=" + address + ", phone=" + phone + ", password="
//				+ password + ", role=" + role + ", is enabled=" + enabled + ", has_savings=" + has_savings + "]";
//	}
//
//	
//
//	
}