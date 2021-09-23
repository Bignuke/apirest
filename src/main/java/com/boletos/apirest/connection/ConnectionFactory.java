package com.boletos.apirest.connection;

import java.sql.Connection;
import java.sql.SQLException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.boletos.apirest.exception.DAOException;
import com.boletos.apirest.utils.PropertiesUtils;
import com.mysql.cj.jdbc.MysqlDataSource;


public class ConnectionFactory {
	
	private static final Logger log = LoggerFactory.getLogger(ConnectionFactory.class);

	// String conString = "jdbc:mysql://187.45.207.39/serracompany";
	//private static String host = "187.45.207.39";
	//private static String database = "serracompany";
	//private static String username = "serracompany";
	//private static String password = "serra4567";

	public static Connection getConnection() throws DAOException {

		try {
			PropertiesUtils prop = new PropertiesUtils();
			String url = prop.getUrl();
			String username = prop.getUsername();
			String password = prop.getPassword();

			MysqlDataSource mysqlDataSource = new MysqlDataSource();
			mysqlDataSource.setUrl(url);
			mysqlDataSource.setUser(username);
			mysqlDataSource.setPassword(password);
			mysqlDataSource.setAutoReconnect(true);

			return mysqlDataSource.getConnection();

		} catch (SQLException e) {
			log.error("Erro ao conectar ao banco. {}", e);
			throw new DAOException(e);
		}

	}

	public static Connection getConnection(int tentativas) throws DAOException {

		int tentativa = 0;
		while (true) {
			try {
				return getConnection();
			} catch (DAOException e) {
				tentativa++;
				if (tentativa == tentativas) {
					throw new DAOException(e);
				}

				log.debug("Erro ao conectar com o Banco de dados tentando novamente em 5 segundos...");
				try {
					Thread.sleep(5000);
				} catch (InterruptedException e1) {
					throw new DAOException(e);
				}

			}
		}

	}
	
	
	
	public static void rollback(Connection con) {
		try {
			if (con!=null)	con.rollback();
		} catch (SQLException e) {
			log.error("Erro {}", e);
		}
	}
	
	
	public static void close(Connection con) {
		try {
			if (con!=null)	con.close();
		} catch (SQLException e) {
			log.error("Erro {}", e);
		}
	}
	
}
