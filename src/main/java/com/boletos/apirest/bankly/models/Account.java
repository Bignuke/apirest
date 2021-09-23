package com.boletos.apirest.bankly.models;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

public class Account {

	@JsonInclude(value = Include.NON_NULL)
	private String branch;
	@JsonInclude(value = Include.NON_NULL)
	private String number;
	
	public Account() {
	}
	public Account(String branch, String number) {
		this.branch = branch;
		this.number = number;
	}
	
	public String getBranch() {
		return branch;
	}
	public void setBranch(String branch) {
		this.branch = branch;
	}
	public String getNumber() {
		return number;
	}
	public void setNumber(String number) {
		this.number = number;
	}
	
	
	@Override
	public String toString() {
		return "Account [branch=" + branch + ", number=" + number + "]";
	}
	
}
