package com.boletos.apirest.bankly.models;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

public class Payment {

	@JsonIgnore
	private long idBoleto;
	@JsonInclude(value = Include.NON_NULL)
	private String id;
	@JsonInclude(value = Include.NON_NULL)
	private Double amount;
	@JsonInclude(value = Include.NON_NULL)
	private String paymentChannel;
	@JsonInclude(value = Include.NON_NULL)
	private Date paidOutDate;
	
	
	public long getIdBoleto() {
		return idBoleto;
	}
	public void setIdBoleto(long idBoleto) {
		this.idBoleto = idBoleto;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public Double getAmount() {
		return amount;
	}
	public void setAmount(Double amount) {
		this.amount = amount;
	}
	public String getPaymentChannel() {
		return paymentChannel;
	}
	public void setPaymentChannel(String paymentChannel) {
		this.paymentChannel = paymentChannel;
	}
	public Date getPaidOutDate() {
		return paidOutDate;
	}
	public void setPaidOutDate(Date paidOutDate) {
		this.paidOutDate = paidOutDate;
	}
	
	
	@Override
	public String toString() {
		return "Payment [idBoleto=" + idBoleto + ", id=" + id + ", amount=" + amount + ", paymentChannel="
				+ paymentChannel + ", paidOutDate=" + paidOutDate + "]";
	}
	
}
