package com.boletos.apirest.bankly.models;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

public class Person {

	@JsonInclude(value = Include.NON_NULL)
    private String document;
	@JsonInclude(value = Include.NON_NULL)
    private String name;
	@JsonInclude(value = Include.NON_NULL)
    private String tradeName;
	@JsonInclude(value = Include.NON_NULL)
    private Address address;
    
    
	public Person(String document, String name, String tradeName, 
			String addressLine, String city, String state, String zipCode, String neighborhood, String country) {
		this.document = document;
		this.name = name;
		this.tradeName = tradeName;
		this.address = new Address(addressLine, city, state, zipCode, neighborhood, country);
	}
	
	public Person() {
	}
	
	public String getDocument() {
		return document;
	}
	public void setDocument(String document) {
		this.document = document;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getTradeName() {
		return tradeName;
	}
	public void setTradeName(String tradeName) {
		this.tradeName = tradeName;
	}
	public Address getAddress() {
		return address;
	}
	public void setAddress(Address address) {
		this.address = address;
	}
	
	
	@Override
	public String toString() {
		return "Person [document=" + document + ", name=" + name + ", tradeName=" + tradeName + ", address=" + address
				+ "]";
	}
    
}
