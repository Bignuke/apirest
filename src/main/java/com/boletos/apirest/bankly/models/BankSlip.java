package com.boletos.apirest.bankly.models;

import java.util.Date;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

public class BankSlip {

	@JsonInclude(value = Include.NON_NULL)
	private String authenticationCode;
	@JsonInclude(value = Include.NON_NULL)
	private Date updatedAt;
	@JsonInclude(value = Include.NON_NULL)
	private String ourNumber;
	@JsonInclude(value = Include.NON_NULL)
	private String digitable;
	@JsonInclude(value = Include.NON_NULL)
	private String status;
	@JsonInclude(value = Include.NON_NULL)
	private Account account;
	@JsonInclude(value = Include.NON_NULL)
	private String document;
	@JsonInclude(value = Include.NON_NULL)
	private Amount amount;
	@JsonInclude(value = Include.NON_NULL)
	private Date dueDate;
	@JsonInclude(value = Include.NON_NULL)
	private Date emissionDate;
	@JsonInclude(value = Include.NON_NULL)
	private String type;
	@JsonInclude(value = Include.NON_NULL)
	private Person payer;
	@JsonInclude(value = Include.NON_NULL)
	private Person recipientFinal;
	@JsonInclude(value = Include.NON_NULL)
	private Person recipientOrigin;
	@JsonInclude(value = Include.NON_NULL)
	private List<Payment> payments;
	
	
	public BankSlip() {
	}
	
	public BankSlip(BankSlip bean) {
		this.authenticationCode = bean.getAuthenticationCode();
		this.updatedAt = bean.getUpdatedAt();
		this.ourNumber = bean.getOurNumber();
		this.digitable = bean.getDigitable();
		this.status = bean.getStatus();
		this.account = bean.getAccount();
		this.document = bean.getDocument();
		this.amount = bean.getAmount();
		this.dueDate = bean.getDueDate();
		this.emissionDate = bean.getEmissionDate();
		this.type = bean.getType();
		this.payer = bean.getPayer();
		this.recipientFinal = bean.getRecipientFinal();
		this.recipientOrigin = bean.getRecipientOrigin();
		this.payments = bean.getPayments();
	}
	
	public BankSlip(Account account, Double amount, Date dueDate, String type, Person payer, String authenticationCode) {
		this.account = account;
		this.amount = new Amount(amount);
		this.dueDate = dueDate;
		this.type = type;
		this.payer = payer;
		this.authenticationCode = authenticationCode;
	}

	public String getAuthenticationCode() {
		return authenticationCode;
	}
	public void setAuthenticationCode(String authenticationCode) {
		this.authenticationCode = authenticationCode;
	}
	public Date getUpdatedAt() {
		return updatedAt;
	}
	public void setUpdatedAt(Date updatedAt) {
		this.updatedAt = updatedAt;
	}
	public String getOurNumber() {
		return ourNumber;
	}
	public void setOurNumber(String ourNumber) {
		this.ourNumber = ourNumber;
	}
	public String getDigitable() {
		return digitable;
	}
	public void setDigitable(String digitable) {
		this.digitable = digitable;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public Account getAccount() {
		return account;
	}
	public void setAccount(Account account) {
		this.account = account;
	}
	public String getDocument() {
		return document;
	}
	public void setDocument(String document) {
		this.document = document;
	}
	public Amount getAmount() {
		return amount;
	}
	public void setAmount(Amount amount) {
		this.amount = amount;
	}
	public Date getDueDate() {
		return dueDate;
	}
	public void setDueDate(Date dueDate) {
		this.dueDate = dueDate;
	}
	public Date getEmissionDate() {
		return emissionDate;
	}
	public void setEmissionDate(Date emissionDate) {
		this.emissionDate = emissionDate;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public Person getPayer() {
		return payer;
	}
	public void setPayer(Person payer) {
		this.payer = payer;
	}
	public Person getRecipientFinal() {
		return recipientFinal;
	}
	public void setRecipientFinal(Person recipientFinal) {
		this.recipientFinal = recipientFinal;
	}
	public Person getRecipientOrigin() {
		return recipientOrigin;
	}
	public void setRecipientOrigin(Person recipientOrigin) {
		this.recipientOrigin = recipientOrigin;
	}
	public List<Payment> getPayments() {
		return payments;
	}
	public void setPayments(List<Payment> payments) {
		this.payments = payments;
	}
	
	
	@Override
	public String toString() {
		return "Boleto [authenticationCode=" + authenticationCode + ", updatedAt=" + updatedAt + ", ourNumber="
				+ ourNumber + ", digitable=" + digitable + ", status=" + status + ", account=" + account + ", document="
				+ document + ", amount=" + amount + ", dueDate=" + dueDate + ", emissionDate=" + emissionDate
				+ ", type=" + type + ", payer=" + payer + ", recipientFinal=" + recipientFinal + ", recipientOrigin="
				+ recipientOrigin + ", payments=" + payments + "]";
	}
	

	public BankSlip(String error) {
		this.error = error;
	}
	
	@JsonInclude(Include.NON_NULL)
	private String error;
	public String getError() {
		return error;
	}
	public void setError(String error) {
		this.error = error;
	}
	
}
