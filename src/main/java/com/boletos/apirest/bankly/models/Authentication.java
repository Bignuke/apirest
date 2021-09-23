package com.boletos.apirest.bankly.models;

import java.util.Date;

import com.boletos.apirest.utils.DateUtils;

public class Authentication {

	private String accessToken;
	private Integer expiresIn;
	private String tokenType;
	private Date createToken;
	
	
	public String getAccessToken() {
		return accessToken;
	}
	public void setAccessToken(String accessToken) {
		this.accessToken = accessToken;
	}
	public Integer getExpiresIn() {
		return expiresIn;
	}
	public void setExpiresIn(Integer expiresIn) {
		this.expiresIn = expiresIn;
	}
	public String getTokenType() {
		return tokenType;
	}
	public void setTokenType(String tokenType) {
		this.tokenType = tokenType;
	}
	public Date getCreateToken() {
		return createToken;
	}
	public void setCreateToken(Date createToken) {
		this.createToken = createToken;
	}
	
	public boolean isTokenValid() {
		if (createToken == null)
			return false;
		Date atual = DateUtils.dataAtual();
		Date limit = DateUtils.addSegundos(createToken, expiresIn);
		return atual.before(limit);
	}
	
	
	@Override
	public String toString() {
		return "Authentication [accessToken=" + accessToken + ", expiresIn=" + expiresIn + ", tokenType=" + tokenType
				+ "]";
	}
	
}
