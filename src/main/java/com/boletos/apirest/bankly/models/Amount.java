package com.boletos.apirest.bankly.models;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

public class Amount {

	@JsonInclude(value = Include.NON_NULL)
	private String currency;
	@JsonInclude(value = Include.NON_NULL)
    private Double value;
    
    public Amount() {
	}
    public Amount(String currency, Double value) {
    	this.currency = currency;
    	this.value = value;
	}
    public Amount(Double value) {
    	this.currency = "BRL";
    	this.value = value;
	}
    
	public String getCurrency() {
		return currency;
	}
	public void setCurrency(String currency) {
		this.currency = currency;
	}
	public Double getValue() {
		return value;
	}
	public void setValue(Double value) {
		this.value = value;
	}
	
	@Override
	public String toString() {
		return "Amount [currency=" + currency + ", value=" + value + "]";
	}
    
}
