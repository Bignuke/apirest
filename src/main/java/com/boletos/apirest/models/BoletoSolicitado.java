package com.boletos.apirest.models;

import java.util.Date;

import com.boletos.apirest.bankly.models.Account;
import com.boletos.apirest.bankly.models.Person;
import com.boletos.apirest.entity.Usuario;

public class BoletoSolicitado {

	private Usuario user;			//	Login
	private String alias;			//	Identificador externo do boleto.
	private Account account;		//	Agência da conta.	Número da conta.	
	private String documentNumber;	//	CPF ou CNPJ do emissor.
	private Double amount;			//	Valor do boleto.
	private Date dueDate;			//	Data de vencimento.
	private Boolean emissionFee;	//	Inclua a tarifa de emissão.
	private String type;			//	Depósito ou Cobrança	Não é possível aplicar desconto, juros, multas ou abatimento.
	private Person payer;

	
	
	public Usuario getUser() {
		return user;
	}
	public void setUser(Usuario user) {
		this.user = user;
	}
	public String getAlias() {
		return alias;
	}
	public void setAlias(String alias) {
		this.alias = alias;
	}
	public Account getAccount() {
		return account;
	}
	public void setAccount(Account account) {
		this.account = account;
	}
	public String getDocumentNumber() {
		return documentNumber;
	}
	public void setDocumentNumber(String documentNumber) {
		this.documentNumber = documentNumber;
	}
	public Double getAmount() {
		return amount;
	}
	public void setAmount(Double amount) {
		this.amount = amount;
	}
	public Date getDueDate() {
		return dueDate;
	}
	public void setDueDate(Date dueDate) {
		this.dueDate = dueDate;
	}
	public Boolean getEmissionFee() {
		return emissionFee;
	}
	public void setEmissionFee(Boolean emissionFee) {
		this.emissionFee = emissionFee;
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

}
