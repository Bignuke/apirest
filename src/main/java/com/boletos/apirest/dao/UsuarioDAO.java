package com.boletos.apirest.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.boletos.apirest.entity.Usuario;
import com.boletos.apirest.exception.DAOException;

public class UsuarioDAO extends AbstractDAO {

	public UsuarioDAO(Connection con) {
		super(con);
	}
	
	public Usuario login(Usuario usuario) throws DAOException {
		Usuario user = buscar(null, usuario.getUsername(), usuario.getPassword());
		return user;
	}
	
	public Usuario buscar(int id) throws DAOException {
		return buscar(id, null, null);
	}
	
	private Usuario buscar(Integer id, String user, String password) throws DAOException {
		StringBuilder query = new StringBuilder();
		query.append("SELECT id, idCliente, login, senha, administrador");
		query.append(" FROM ").append(Usuario.TABELA).append(" WHERE 1=1");
		if (id !=null) {
			query.append(" AND id=").append(id);
		}
		else {
			query.append(" AND login='").append(user).append("' AND senha=MD5('").append(password).append("')");
		}
		
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			ps = con.prepareStatement(query.toString());
			rs = ps.executeQuery();
			
			Usuario bean = new Usuario();
			if (rs.next()) {
				bean.setId(rs.getInt("id"));
				bean.setIdCliente(rs.getInt("idCliente"));
				bean.setUsername(rs.getString("login"));
				bean.setPassword(rs.getString("senha"));
				bean.setAdministrador(rs.getBoolean("administrador"));
			}
			return bean;
		} catch (SQLException e) {
			log.error("Erro [{}] {}", query, e);
			throw new DAOException(e);
		}
	}

	
	
}
