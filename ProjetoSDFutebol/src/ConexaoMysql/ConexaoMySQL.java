package ConexaoMysql;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConexaoMySQL {

	public static String status = "Não conectou...";

	public ConexaoMySQL() {

	}

	public static java.sql.Connection getConexaoMySQL() {
		Connection connection = null; //
		try {
			// Carregando o JDBC Driver padrão
			String driverName = "com.mysql.jdbc.Driver";
			Class.forName(driverName);
			// Configurando a conexão com o banco
			String serverName = "localhost";
			String mydatabase = "TrabalhoG2";
			String url = "jdbc:mysql://" + serverName + "/" + mydatabase;
			String userName = "root";
			String password = "";
			connection = DriverManager.getConnection(url, userName, password);
			if (connection != null) {
				status = "STATUS--->Conectado com Sucesso!";
			} else {
				status = "STATUS--->Não foi possível realizar conexão!";
			}
			return connection;
		} catch (ClassNotFoundException e) {// Driver não encobntrado
			System.out.println("O driver " + connection + " Não foi encontrado");
			return null;
		} catch (SQLException e) {// Não conseguindo se conectar com o banco
			System.out.println("Não foi possível se conectar ao banco de dados. " + e);
			return null;
		}
	}

	// Método que retorna o status da sua conexão//
	public static String statusConection() {
		return status;
	}

	// Método que retorna o status da sua conexao
	public static boolean FecharConexao() {
		try {
			ConexaoMySQL.getConexaoMySQL().close();
			return true;
		} catch (SQLException e) {
			return false;
		}
	}

	// Método para reiniciar a conexao
	public static java.sql.Connection ReiniciarConexao() {
		FecharConexao();
		return ConexaoMySQL.getConexaoMySQL();
	}

}
