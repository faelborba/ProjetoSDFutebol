package ConexaoMysql;

import java.io.File;
import java.io.FileNotFoundException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Scanner;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import servidorHttp.ConverteEmString;

public class ConexaoMySQL {

	public static String status = "Não conectou...";

	public ConexaoMySQL() {

	}

	public static java.sql.Connection getConexaoMySQL() {
		Gson gson = new GsonBuilder().setPrettyPrinting().create();
		Scanner pegaTexto = null;
		String textoConfig = null;
		Connection connection = null;
		ConfigMysql configMysql = new ConfigMysql();
		File arquivo = new File("config/configMysql.json");// fazendo um objeto arquivo
		if (!arquivo.exists()) {// testando se o arquivo já existe, caso não exista cria um novo
			System.out.println("Server BD: Arquivo de configuração inexistente, Dirver Mysql não iniciado!");
			return connection;
		} else {
			System.out.println("Server BD: Abrindo arquivo de configuração.");
			try {
				pegaTexto = new Scanner(arquivo); // pega texto do arquivo
			} catch (FileNotFoundException e) {
				System.out.println("Server BD: Erro no arquivo de configuração mysql");
			}
			textoConfig = new ConverteEmString().converteJson(pegaTexto);
			System.out.println("Server DB: Arquivo " + arquivo.getName() + " Aberto\n");
			System.out.println("Server DB: Json:\n" + textoConfig);
			configMysql = gson.fromJson(textoConfig, ConfigMysql.class);
			System.out.println("Server DB: teste: " + configMysql.serverName);

			try {
				// Carregando o JDBC Driver padrão
				String driverName = "com.mysql.jdbc.Driver";
				Class.forName(driverName);
				// Configurando a conexão com o banco
				String serverName = configMysql.getServerName();
				String mydatabase = configMysql.getMydatabase();
				String userName = configMysql.getUserName();
				String password = configMysql.getPassword();
				String url = "jdbc:mysql://" + serverName + "/" + mydatabase;
				connection = DriverManager.getConnection(url, userName, password);
				if (connection != null) {
					status = "Server BD: STATUS--->Conectado com Sucesso!";
				} else {
					status = "Server BD: STATUS--->Não foi possível realizar conexão!";
				}
				return connection;
			} catch (ClassNotFoundException e) {// Driver não encobntrado
				System.out.println("Server BD: O driver " + connection + " Não foi encontrado. " + e);
				return null;
			} catch (SQLException e) {// Não conseguindo se conectar com o banco
				System.out.println("Server BD: Não foi possível se conectar ao banco de dados. " + e);
				return null;
			}
		}
	}

	// Método que retorna o status da sua conexão//
	public static String statusConection() {
		return status;
	}

	// Método que retorna o status da sua conexao
	public static boolean FecharConexao() throws FileNotFoundException {
		try {
			ConexaoMySQL.getConexaoMySQL().close();
			return true;
		} catch (SQLException e) {
			return false;
		}
	}

	// Método para reiniciar a conexao
	public static java.sql.Connection ReiniciarConexao() throws FileNotFoundException {
		FecharConexao();
		return ConexaoMySQL.getConexaoMySQL();
	}

}
