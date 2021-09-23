package com.boletos.apirest.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Date;
import java.util.Map;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.boletos.apirest.exception.DAOException;
import com.boletos.apirest.utils.StringUtils;

/**
 * Classe que fornece às implementações de DAO o acesso a conexões, e métodos relacionados à limpeza dos recursos.
 * 
 */
public abstract class AbstractDAO {

	public static int MAX_TRY = 3;
	protected static Logger log = LoggerFactory.getLogger(AbstractDAO.class);
	protected Connection con;

	protected AbstractDAO(Connection con) {
		this.con = con;
	}
	
	
	
	protected static void close(PreparedStatement pstm) {
		close(pstm, null);
	}

	protected static void close(Statement stmt, ResultSet rs) {
		try {
			if (rs != null)
				rs.close();
			if (stmt != null)
				stmt.close();
			
		} catch (Exception e) {
			log.error("Erro {}", e);
		}
	}
	

	public Object executeInsert(Connection conn, String nomeTabela, Map<String, Object> campos) throws DAOException {

		StringBuffer sbQuery = new StringBuffer();
		StringBuffer sbCampo = new StringBuffer();
		StringBuffer sbValor = new StringBuffer();

		Set<String> keySet = campos.keySet();
		for (String campo : keySet) {
			sbCampo.append(",").append(campo);
			sbValor.append(",").append("?");
		}

		sbQuery.append("INSERT INTO ").append(nomeTabela);
		sbQuery.append(" ( ").append(sbCampo.substring(1)).append(" ) ");
		sbQuery.append(" VALUES ( ").append(sbValor.substring(1)).append(" );");

		int c = 0;
		PreparedStatement pstm = null;
		ResultSet rs = null;
		while (true) {
			try {
				pstm = conn.prepareStatement(sbQuery.toString(), PreparedStatement.RETURN_GENERATED_KEYS);

				int i = 1;
				for (String campo : keySet) {
					pstm.setObject(i++, campos.get(campo));
				}
				pstm.executeUpdate();
				
				rs = pstm.getGeneratedKeys();
				if (rs.next())
					return rs.getObject(1);

				return null;

			} catch (SQLException e) {
				c++;
				if (c==MAX_TRY)	{
					log.error("Erro {}", e);
					throw new DAOException("Erro ao incluir dados na tabela " + nomeTabela , e);
				}
			} finally {
				close(pstm, rs);
			}
		}
	}

	
	public void executeUpdate(Connection conn, String nomeTabela, Map<String, Object> campos, Map<String, Object> condicoes) throws DAOException {

		StringBuffer sbQuery = new StringBuffer();
		StringBuffer sbCampo = new StringBuffer();
		StringBuffer sbCondicao = new StringBuffer();

		Set<String> keyCampos = campos.keySet();
		for (String campo : keyCampos) {
			sbCampo.append(", ").append(campo).append("=?");
		}

		Set<String> keyCondicoes = condicoes.keySet();
		for (String condicao : keyCondicoes) {
			sbCondicao.append(" AND ").append(condicao).append("=?");
		}
		
		sbQuery.append("UPDATE ").append(nomeTabela).append(" SET ");
		sbQuery.append(sbCampo.substring(1));
		sbQuery.append(" WHERE ").append(sbCondicao.substring(5));

		int c = 0;
		PreparedStatement pstm = null;
		while (true) {
			try {
				pstm = conn.prepareStatement(sbQuery.toString());

				int i = 1;
				for (String campo : keyCampos) {
					pstm.setObject(i++, campos.get(campo));
				}
				for (String condicao : keyCondicoes) {
					pstm.setObject(i++, condicoes.get(condicao));
				}

				pstm.executeUpdate();
				break;

			} catch (SQLException e) {
				c++;
				if (c==MAX_TRY) {
					log.error("Erro {}", e);
					throw new DAOException("Erro ao atualizar dados na tabela " + nomeTabela , e);
				}
			} finally {
				close(pstm);			
			}
		}
	}
		
	public void executeUpdate(Connection conn, String query) throws DAOException {
		int c = 0;
		PreparedStatement pstm = null;
		while (true) {
			try {
				pstm = conn.prepareStatement(query);
				pstm.executeUpdate();
				break;

			} catch (SQLException e) {
				c++;
				if (c==MAX_TRY)	{
					log.error("Erro {}", e);
					throw new DAOException("Erro ao atualizar dados", e);
				}
			} finally {
				close(pstm);
			}
		}
	}
	
	public void executeDelete(Connection conn, String nomeTabela, Map<String, Object> condicoes) throws DAOException {

		StringBuffer sbQuery = new StringBuffer();
		StringBuffer sbCondicao = new StringBuffer();

		Set<String> keyCondicoes = condicoes.keySet();
		for (String condicao : keyCondicoes) {
			sbCondicao.append(" AND ").append(condicao).append(" = ? ");
		}

		sbQuery.append("DELETE FROM ").append(nomeTabela);
		sbQuery.append(" WHERE ").append(sbCondicao.substring(5));

		int c = 0;
		PreparedStatement pstm = null;
		while (true) {
			try {
				pstm = conn.prepareStatement(sbQuery.toString());

				int i = 1;
				for (String condicao : keyCondicoes) {
					pstm.setObject(i++, condicoes.get(condicao));
				}

				pstm.executeUpdate();
				break;

			} catch (SQLException e) {
				c++;				
				if (c==MAX_TRY)	{
					log.error("Erro {}", e);
					throw new DAOException("Erro ao excluir dados na tabela " + nomeTabela , e);
				}

			} finally {
				close(pstm);
			}
		}
	}




	public static Integer formatObject(Integer obj) {		
		if (obj == null || obj == 0)
			return null;
		else
			return obj;
	}
	public static Double formatObject(Double obj) {		
		if (obj == null || obj == 0)
			return null;
		else
			return obj;
	}
	public static String formatObject(String obj) {
		if (StringUtils.isEmpty(obj))
			return null;
		return StringUtils.trim(obj);
	}
	
	
	protected void put(Map<String, Object> map, String campo, Boolean valor) {
		if (valor != null)	map.put(campo, valor);
	}
	protected void put(Map<String, Object> map, String campo, Date valor) {
		if (valor != null)	map.put(campo, valor);
	}
	protected void put(Map<String, Object> map, String campo, Double valor) {
		if (valor != null)	map.put(campo, valor);
	}
	protected void put(Map<String, Object> map, String campo, Long valor) {
		if (valor != null)	map.put(campo, valor);
	}
	protected void put(Map<String, Object> map, String campo, Integer valor) {
		if (valor != null)	map.put(campo, valor);
	}
	protected void put(Map<String, Object> map, String campo, String valor) {
		if (valor != null && !valor.trim().isEmpty())
			map.put(campo, valor);
	}

}
