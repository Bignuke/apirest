package com.boletos.apirest.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.boletos.apirest.bankly.models.Account;
import com.boletos.apirest.bankly.models.Amount;
import com.boletos.apirest.bankly.models.Payment;
import com.boletos.apirest.bankly.models.Person;
import com.boletos.apirest.entity.Boleto;
import com.boletos.apirest.exception.DAOException;
import com.boletos.apirest.utils.DateUtils;
import com.boletos.apirest.utils.StringUtils;

public class BoletoDAO extends AbstractDAO {

	public BoletoDAO(Connection con) {
		super(con);
	}
	
	public void persistir(Boleto bean) throws DAOException {
		if (bean.getId() == 0) {
			inserir(bean);
		}
		else {
			atualizar(bean);
			excluirPagamento(bean.getId());
		}
		
		if (bean.getPayments()!=null) {
			for (Payment p : bean.getPayments()) {
				p.setIdBoleto(bean.getId());
				inserirPagamento(p);
			}
		}
	}
	
	
	private void inserir(Boleto bean) throws DAOException {
		Map<String, Object> campos = new HashMap<String, Object>();
		put(campos, "idUsuario", bean.getUser().getId());
		put(campos, "authenticationCode", bean.getAuthenticationCode());
		put(campos, "account_branch", bean.getAccount().getBranch());
		put(campos, "account_number", bean.getAccount().getNumber());
		put(campos, "documentNumber", bean.getDocumentNumber());
		put(campos, "amount_currency", bean.getAmount().getCurrency());
		put(campos, "amount_value", bean.getAmount().getValue());
		put(campos, "dueDate", bean.getDueDate());
		put(campos, "emissionFee", bean.getEmissionFee());
		put(campos, "type", bean.getType());
		if (bean.getPayer()!=null) {
			put(campos, "payer_document", bean.getPayer().getDocument());
			put(campos, "payer_name", bean.getPayer().getName());
			put(campos, "payer_tradeName", bean.getPayer().getTradeName());
			if (bean.getPayer().getAddress()!=null) {
				put(campos, "payer_address_addressLine", bean.getPayer().getAddress().getAddressLine());
				put(campos, "payer_address_city", bean.getPayer().getAddress().getCity());
				put(campos, "payer_address_state", bean.getPayer().getAddress().getState());
				put(campos, "payer_address_zipCode", bean.getPayer().getAddress().getZipCode());
				put(campos, "payer_address_neighborhood", bean.getPayer().getAddress().getNeighborhood());
				put(campos, "payer_address_country", bean.getPayer().getAddress().getCountry());
			}
		}
		put(campos, "updatedAt", bean.getUpdatedAt());
		put(campos, "ourNumber", bean.getOurNumber());
		put(campos, "digitable", bean.getDigitable());
		put(campos, "status", bean.getStatus());
		put(campos, "document", bean.getDocument());
		put(campos, "emissionDate", bean.getEmissionDate());
		if (bean.getRecipientOrigin()!=null) {
			put(campos, "recipientOrigin_document", bean.getRecipientOrigin().getDocument());
			put(campos, "recipientOrigin_name", bean.getRecipientOrigin().getName());
			put(campos, "recipientOrigin_tradeName", bean.getRecipientOrigin().getTradeName());
			if (bean.getRecipientOrigin().getAddress()!=null) {
				put(campos, "recipientOrigin_address_addressLine", bean.getRecipientOrigin().getAddress().getAddressLine());
				put(campos, "recipientOrigin_address_city", bean.getRecipientOrigin().getAddress().getCity());
				put(campos, "recipientOrigin_address_state", bean.getRecipientOrigin().getAddress().getState());
				put(campos, "recipientOrigin_address_zipCode", bean.getRecipientOrigin().getAddress().getZipCode());
				put(campos, "recipientOrigin_address_neighborhood", bean.getRecipientOrigin().getAddress().getNeighborhood());
				put(campos, "recipientOrigin_address_country", bean.getRecipientOrigin().getAddress().getCountry());
			}
		}
		if (bean.getRecipientFinal()!=null) {
			put(campos, "recipientFinal_document", bean.getRecipientFinal().getDocument());
			put(campos, "recipientFinal_name", bean.getRecipientFinal().getName());
			put(campos, "recipientFinal_tradeName", bean.getRecipientFinal().getTradeName());
			if (bean.getRecipientFinal().getAddress()!=null) {
				put(campos, "recipientFinal_address_addressLine", bean.getRecipientFinal().getAddress().getAddressLine());
				put(campos, "recipientFinal_address_city", bean.getRecipientFinal().getAddress().getCity());
				put(campos, "recipientFinal_address_state", bean.getRecipientFinal().getAddress().getState());
				put(campos, "recipientFinal_address_zipCode", bean.getRecipientFinal().getAddress().getZipCode());
				put(campos, "recipientFinal_address_neighborhood", bean.getRecipientFinal().getAddress().getNeighborhood());
				put(campos, "recipientFinal_address_country", bean.getRecipientFinal().getAddress().getCountry());
			}
		}
		
		Object id = executeInsert(con, Boleto.TABELA, campos);
		bean.setId(Long.parseLong(id.toString()));
	}
	
	private void atualizar(Boleto bean) throws DAOException {
		Map<String, Object> campos = new HashMap<String, Object>();
//		put(campos, "idUsuario", bean.getUser().getId());
//		put(campos, "authenticationCode", bean.getAuthenticationCode());
//		put(campos, "account_branch", bean.getAccount().getBranch());
//		put(campos, "account_number", bean.getAccount().getNumber());
//		put(campos, "documentNumber", bean.getDocumentNumber());
//		put(campos, "amount_currency", bean.getAmount().getCurrency());
//		put(campos, "amount_value", bean.getAmount().getValue());
//		put(campos, "dueDate", bean.getDueDate());
		put(campos, "emissionFee", bean.getEmissionFee());
		put(campos, "type", bean.getType());
		if (bean.getPayer()!=null) {
			put(campos, "payer_document", bean.getPayer().getDocument());
			put(campos, "payer_name", bean.getPayer().getName());
			put(campos, "payer_tradeName", bean.getPayer().getTradeName());
			if (bean.getPayer().getAddress()!=null) {
				put(campos, "payer_address_addressLine", bean.getPayer().getAddress().getAddressLine());
				put(campos, "payer_address_city", bean.getPayer().getAddress().getCity());
				put(campos, "payer_address_state", bean.getPayer().getAddress().getState());
				put(campos, "payer_address_zipCode", bean.getPayer().getAddress().getZipCode());
				put(campos, "payer_address_neighborhood", bean.getPayer().getAddress().getNeighborhood());
				put(campos, "payer_address_country", bean.getPayer().getAddress().getCountry());
			}
		}
		put(campos, "updatedAt", bean.getUpdatedAt());
		put(campos, "ourNumber", bean.getOurNumber());
		put(campos, "digitable", bean.getDigitable());
		put(campos, "status", bean.getStatus());
		put(campos, "document", bean.getDocument());
		put(campos, "emissionDate", bean.getEmissionDate());
		if (bean.getRecipientOrigin()!=null) {
			put(campos, "recipientOrigin_document", bean.getRecipientOrigin().getDocument());
			put(campos, "recipientOrigin_name", bean.getRecipientOrigin().getName());
			put(campos, "recipientOrigin_tradeName", bean.getRecipientOrigin().getTradeName());
			if (bean.getRecipientOrigin().getAddress()!=null) {
				put(campos, "recipientOrigin_address_addressLine", bean.getRecipientOrigin().getAddress().getAddressLine());
				put(campos, "recipientOrigin_address_city", bean.getRecipientOrigin().getAddress().getCity());
				put(campos, "recipientOrigin_address_state", bean.getRecipientOrigin().getAddress().getState());
				put(campos, "recipientOrigin_address_zipCode", bean.getRecipientOrigin().getAddress().getZipCode());
				put(campos, "recipientOrigin_address_neighborhood", bean.getRecipientOrigin().getAddress().getNeighborhood());
				put(campos, "recipientOrigin_address_country", bean.getRecipientOrigin().getAddress().getCountry());
			}
		}
		if (bean.getRecipientFinal()!=null) {
			put(campos, "recipientFinal_document", bean.getRecipientFinal().getDocument());
			put(campos, "recipientFinal_name", bean.getRecipientFinal().getName());
			put(campos, "recipientFinal_tradeName", bean.getRecipientFinal().getTradeName());
			if (bean.getRecipientFinal().getAddress()!=null) {
				put(campos, "recipientFinal_address_addressLine", bean.getRecipientFinal().getAddress().getAddressLine());
				put(campos, "recipientFinal_address_city", bean.getRecipientFinal().getAddress().getCity());
				put(campos, "recipientFinal_address_state", bean.getRecipientFinal().getAddress().getState());
				put(campos, "recipientFinal_address_zipCode", bean.getRecipientFinal().getAddress().getZipCode());
				put(campos, "recipientFinal_address_neighborhood", bean.getRecipientFinal().getAddress().getNeighborhood());
				put(campos, "recipientFinal_address_country", bean.getRecipientFinal().getAddress().getCountry());
			}
		}
		
		campos.put("dataAtualizacao", DateUtils.dataAtual());
		
		Map<String, Object> condicoes = new HashMap<String, Object>();
		condicoes.put("id", bean.getId());
		
		executeUpdate(con, Boleto.TABELA, campos, condicoes);
	}

	
	public Boleto buscar(String branch, String number, String authenticationCode) throws DAOException {
		StringBuilder query = new StringBuilder();
		query.append("SELECT id");
		query.append(",idUsuario");
		query.append(",authenticationCode");
		query.append(",account_branch");
		query.append(",account_number");
		query.append(",documentNumber");
		query.append(",amount_currency");
		query.append(",amount_value");
		query.append(",dueDate");
		query.append(",emissionFee");
		query.append(",type");
		query.append(",payer_document");
		query.append(",payer_name");
		query.append(",payer_tradeName");
		query.append(",payer_address_addressLine");
		query.append(",payer_address_city");
		query.append(",payer_address_state");
		query.append(",payer_address_zipCode");
		query.append(",payer_address_neighborhood");
		query.append(",payer_address_country");
		query.append(",updatedAt");
		query.append(",ourNumber");
		query.append(",digitable");
		query.append(",status");
		query.append(",document");
		query.append(",emissionDate");
		query.append(",recipientOrigin_document");
		query.append(",recipientOrigin_name");
		query.append(",recipientOrigin_tradeName");
		query.append(",recipientOrigin_address_addressLine");
		query.append(",recipientOrigin_address_city");
		query.append(",recipientOrigin_address_state");
		query.append(",recipientOrigin_address_zipCode");
		query.append(",recipientOrigin_address_neighborhood");
		query.append(",recipientOrigin_address_country");
		query.append(",recipientFinal_document");
		query.append(",recipientFinal_name");
		query.append(",recipientFinal_tradeName");
		query.append(",recipientFinal_address_addressLine");
		query.append(",recipientFinal_address_city");
		query.append(",recipientFinal_address_state");
		query.append(",recipientFinal_address_zipCode");
		query.append(",recipientFinal_address_neighborhood");
		query.append(",recipientFinal_address_country");
		query.append(" FROM ").append(Boleto.TABELA);
		query.append(" WHERE 1=1");
		
		if (!StringUtils.isEmpty(branch))
			query.append(" AND account_branch='").append(branch).append("'");
		if (!StringUtils.isEmpty(number))
			query.append(" AND account_number='").append(number).append("'");
		if (!StringUtils.isEmpty(authenticationCode))
			query.append(" AND authenticationCode='").append(authenticationCode).append("'");
		
		
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			ps = con.prepareStatement(query.toString());
			rs = ps.executeQuery();
			
			UsuarioDAO usuarioDAO = new UsuarioDAO(con);
			
			Boleto bean = new Boleto();
			if (rs.next()) {
				bean.setId(rs.getLong("id"));
				bean.setUser(usuarioDAO.buscar(rs.getInt("idUsuario")));
				bean.setAuthenticationCode(rs.getString("authenticationCode"));
				bean.setAccount(new Account(rs.getString("account_branch"), rs.getString("account_number")));
				bean.setDocumentNumber(rs.getString("documentNumber"));
				bean.setAmount(new Amount(rs.getString("amount_currency"), rs.getDouble("amount_value")));
				bean.setDueDate(rs.getTimestamp("dueDate"));
				bean.setEmissionFee(rs.getBoolean("emissionFee"));
				bean.setType(rs.getString("type"));
				bean.setPayer(new Person(
						rs.getString("payer_document"),
						rs.getString("payer_name"),
						rs.getString("payer_tradeName"),
						rs.getString("payer_address_addressLine"),
						rs.getString("payer_address_city"),
						rs.getString("payer_address_state"),
						rs.getString("payer_address_zipCode"),
						rs.getString("payer_address_neighborhood"),
						rs.getString("payer_address_country")
				));
				bean.setUpdatedAt(rs.getTimestamp("updatedAt"));
				bean.setOurNumber(rs.getString("ourNumber"));
				bean.setDigitable(rs.getString("digitable"));
				bean.setStatus(rs.getString("status"));
				bean.setDocument(rs.getString("document"));
				bean.setEmissionDate(rs.getTimestamp("emissionDate"));
				bean.setRecipientOrigin(new Person(
						rs.getString("recipientOrigin_document"),
						rs.getString("recipientOrigin_name"),
						rs.getString("recipientOrigin_tradeName"),
						rs.getString("recipientOrigin_address_addressLine"),
						rs.getString("recipientOrigin_address_city"),
						rs.getString("recipientOrigin_address_state"),
						rs.getString("recipientOrigin_address_zipCode"),
						rs.getString("recipientOrigin_address_neighborhood"),
						rs.getString("recipientOrigin_address_country")
				));
				bean.setRecipientFinal(new Person(
						rs.getString("recipientFinal_document"),
						rs.getString("recipientFinal_name"),
						rs.getString("recipientFinal_tradeName"),
						rs.getString("recipientFinal_address_addressLine"),
						rs.getString("recipientFinal_address_city"),
						rs.getString("recipientFinal_address_state"),
						rs.getString("recipientFinal_address_zipCode"),
						rs.getString("recipientFinal_address_neighborhood"),
						rs.getString("recipientFinal_address_country")
				));
				
				bean.setPayments(listar(bean.getId()));
			}
			return bean;
			
		} catch (SQLException e) {
			throw new DAOException(e);
		} finally {
			close(ps, rs);
		}
	}
	
	/********************************************************************************************/
	
	private void inserirPagamento(Payment bean) throws DAOException {
		Map<String, Object> campos = new HashMap<String, Object>();
		put(campos, "idBoleto", bean.getIdBoleto());
		put(campos, "id", bean.getId());
		put(campos, "paymentChannel", bean.getPaymentChannel());
		put(campos, "paidOutDate", bean.getPaidOutDate());
		put(campos, "amount", bean.getAmount());
		
		executeInsert(con, "pagamento", campos);
	}
	
	private void excluirPagamento(long idBoleto) throws DAOException {
		Map<String, Object> condicoes = new HashMap<String, Object>();
		condicoes.put("idBoleto", idBoleto);
		
		executeDelete(con, "pagamento", condicoes);
	}
	
	private List<Payment> listar(long idBoleto) throws DAOException {
		StringBuilder query = new StringBuilder();
		query.append("SELECT idBoleto, id, paymentChannel, paidOutDate, amount");
		query.append(" FROM pagamento WHERE idBoleto=").append(idBoleto);
		
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			ps = con.prepareStatement(query.toString());
			rs = ps.executeQuery();
			
			List<Payment> lista = new ArrayList<Payment>();
			
			while (rs.next()) {
				Payment bean = new Payment();
				bean.setIdBoleto(rs.getLong("idBoleto"));
				bean.setId(rs.getString("id"));
				bean.setPaymentChannel(rs.getString("paymentChannel"));
				bean.setPaidOutDate(rs.getTimestamp("paidOutDate"));
				bean.setAmount(rs.getDouble("amount"));
				
				lista.add(bean);
			}
			return lista;
			
		} catch (SQLException e) {
			throw new DAOException(e);
		} finally {
			close(ps, rs);
		}
	}
	
}
