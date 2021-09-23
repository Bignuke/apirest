package com.boletos.apirest.models;

import com.boletos.apirest.bankly.models.Account;
import com.boletos.apirest.entity.Usuario;

public class BoletoEmitido {

	private Usuario user;
	private Account account;
	private String authenticationCode;
	


	public Usuario getUser() {
		return user;
	}
	public void setUser(Usuario user) {
		this.user = user;
	}
	public Account getAccount() {
		return account;
	}
	public void setAccount(Account account) {
		this.account = account;
	}
	public String getAuthenticationCode() {
		return authenticationCode;
	}
	public void setAuthenticationCode(String authenticationCode) {
		this.authenticationCode = authenticationCode;
	}
	
}
