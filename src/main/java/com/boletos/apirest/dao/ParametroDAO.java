package com.boletos.apirest.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import com.boletos.apirest.bankly.models.Authentication;
import com.boletos.apirest.exception.DAOException;
import com.boletos.apirest.utils.DateUtils;
import com.boletos.apirest.utils.NumberUtils;
import com.boletos.apirest.utils.DateUtils.PadraoData;
import com.boletos.apirest.utils.StringUtils;

public class ParametroDAO extends AbstractDAO {

	public ParametroDAO(Connection con) {
		super(con);
	}
	
	private static final String TABELA = "parametros";

	private static final String DESC_AUTH = "bankly.link_auth";
	private static final String DESC_API = "bankly.link_api";
	private static final String DESC_GRANTTYPE = "bankly.grant_type";
	private static final String DESC_CLIENTID = "bankly.client_id";
	private static final String DESC_CLIENTSECRET = "bankly.client_secret";
	private static final String DESC_ACCESSTOKEN = "token.access_token";
	private static final String DESC_CREATETOKEN = "token.create_token";
	private static final String DESC_EXPIREIN = "token.expire_token";
	
	public void inserir(Authentication auth) throws DAOException {
		Map<String, String> map = new HashMap<String, String>();
		map.put(DESC_ACCESSTOKEN, auth.getAccessToken());
		map.put(DESC_EXPIREIN, String.valueOf(auth.getExpiresIn()));
		map.put(DESC_CREATETOKEN, DateUtils.formatar(auth.getCreateToken(), PadraoData.DDMMYYYY_BR_HHMMSS_DP));
		
		for (String descricao : map.keySet()) {
			Map<String, Object> campos = new HashMap<String, Object>();
			campos.put("valor", map.get(descricao));
			
			Map<String, Object> condicoes = new HashMap<String, Object>();
			condicoes.put("descricao", descricao);
			
			executeUpdate(con, TABELA, campos, condicoes);
		}
	}

	
	private String buscar(String descricao) throws DAOException {
		StringBuilder query = new StringBuilder();
		query.append("SELECT valor FROM ").append(TABELA).append(" WHERE 1=1");
		if (!StringUtils.isEmpty(descricao))
			query.append(" AND descricao='").append(descricao).append("'");
		
		PreparedStatement pstm = null;
		ResultSet rs = null;
		try {
			pstm = con.prepareStatement(query.toString());
			rs = pstm.executeQuery();
			
			if (rs.next()) {
				return rs.getString("valor");
			}
			return null;
			
		} catch (Exception e) {
			log.error("Erro [{}] {}", query, e);
			throw new DAOException(e);
		} finally {
			close(pstm, rs);
		}
	} 

	public String buscarAccessToken() throws DAOException {
		return buscar(DESC_ACCESSTOKEN);
	}

	public Integer buscarExpireIn() throws DAOException {
		return NumberUtils.parse(buscar(DESC_EXPIREIN));
	}

	public Date buscarCreateToken() throws DAOException {
		return DateUtils.converter(buscar(DESC_CREATETOKEN), PadraoData.DDMMYYYY_BR_HHMMSS_DP);
	}
	
	
	public String buscarUrlAuth() throws DAOException {
		return buscar(DESC_AUTH);
	}
	public String buscarUrlApi() throws DAOException {
		return buscar(DESC_API);
	}
	public String buscarGrantType() throws DAOException {
		return buscar(DESC_GRANTTYPE);
	}
	public String buscarClientId() throws DAOException {
		return buscar(DESC_CLIENTID);
	}
	public String buscarClientSecret() throws DAOException {
		return buscar(DESC_CLIENTSECRET);
	}


}
