package com.boletos.apirest.entity;

import com.boletos.apirest.bankly.models.BankSlip;
import com.boletos.apirest.models.BoletoEmitido;
import com.boletos.apirest.models.BoletoSolicitado;

public class Boleto extends BankSlip {
	
	public static final String TABELA = "boleto";
	
	private long id;
	private Usuario user;
	private String alias;
	private String documentNumber;
	private Boolean emissionFee;
	
	public Boleto() {
	}
	
	public Boleto(BoletoSolicitado solicitado, BoletoEmitido emitido) {
		super(solicitado.getAccount(), solicitado.getAmount(), solicitado.getDueDate(), solicitado.getType(), solicitado.getPayer(), 			emitido.getAuthenticationCode());
		
		this.user = solicitado.getUser();
		this.alias = solicitado.getAlias();
		this.documentNumber = solicitado.getDocumentNumber();
		this.emissionFee = solicitado.getEmissionFee();
	}

	public Boleto(long id, BankSlip bankslip) {
		super(bankslip);
		this.id = id;
	}

	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
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
	public String getDocumentNumber() {
		return documentNumber;
	}
	public void setDocumentNumber(String documentNumber) {
		this.documentNumber = documentNumber;
	}
	public Boolean getEmissionFee() {
		return emissionFee;
	}
	public void setEmissionFee(Boolean emissionFee) {
		this.emissionFee = emissionFee;
	}
	
}
