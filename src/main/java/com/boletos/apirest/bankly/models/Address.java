package com.boletos.apirest.bankly.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

public class Address {
	
	@JsonInclude(value = Include.NON_NULL)
    private String addressLine;
	@JsonInclude(value = Include.NON_NULL)
    private String city;
	@JsonInclude(value = Include.NON_NULL)
    private String state;
	@JsonInclude(value = Include.NON_NULL)
    private String zipCode;
	@JsonIgnore
	@JsonInclude(value = Include.NON_NULL)
    private String neighborhood;
	@JsonIgnore
	@JsonInclude(value = Include.NON_NULL)
    private String country;
    
	public Address() {
	}
	
	public Address(String addressLine, String city, String state, String zipCode, String neighborhood, String country) {
		this.addressLine = addressLine;
		this.city = city;
		this.state = state;
		this.zipCode = zipCode;
		this.neighborhood = neighborhood;
		this.country = country;
	}
	
	
	public String getAddressLine() {
		return addressLine;
	}
	public void setAddressLine(String addressLine) {
		this.addressLine = addressLine;
	}
	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		this.city = city;
	}
	public String getState() {
		return state;
	}
	public void setState(String state) {
		this.state = state;
	}
	public String getZipCode() {
		return zipCode;
	}
	public void setZipCode(String zipCode) {
		this.zipCode = zipCode;
	}
	public String getNeighborhood() {
		return neighborhood;
	}
	public void setNeighborhood(String neighborhood) {
		this.neighborhood = neighborhood;
	}
	public String getCountry() {
		return country;
	}
	public void setCountry(String country) {
		this.country = country;
	}
	
	@Override
	public String toString() {
		return "Address [addressLine=" + addressLine + ", city=" + city + ", state=" + state + ", zipCode=" + zipCode
				+ ", neighborhood=" + neighborhood + ", country=" + country + "]";
	}

}
